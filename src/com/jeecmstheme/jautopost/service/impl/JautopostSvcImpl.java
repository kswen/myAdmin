package com.jeecmstheme.jautopost.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URL;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jeecms.cms.entity.main.Content;
import com.jeecms.cms.entity.main.ContentCount;
import com.jeecms.cms.manager.main.ContentCountMng;
import com.jeecms.cms.service.ImageSvc;
import com.jeecms.common.upload.UploadUtils;
import com.jeecms.common.web.springmvc.RealPathResolver;
import com.jeecms.core.entity.CmsSite;
import com.jeecms.core.manager.CmsConfigMng;
import com.jeecms.core.manager.CmsSiteMng;
import com.jeecmstheme.jautopost.ConcurrentUtil;
import com.jeecmstheme.jautopost.Constants;
import com.jeecmstheme.jautopost.Jautopost;
import com.jeecmstheme.jautopost.ParseHelper;
import com.jeecmstheme.jautopost.QETag;
import com.jeecmstheme.jautopost.entity.CmsJautopost;
import com.jeecmstheme.jautopost.entity.CmsJautopost.JautopostResultType;
import com.jeecmstheme.jautopost.entity.CmsJautopostCfg;
import com.jeecmstheme.jautopost.entity.CmsJautopostFiles;
import com.jeecmstheme.jautopost.entity.CmsJautopostHistory;
import com.jeecmstheme.jautopost.entity.CmsJautopostTemp;
import com.jeecmstheme.jautopost.helper.StringUtil;
import com.jeecmstheme.jautopost.manager.CmsJautopostFilesMng;
import com.jeecmstheme.jautopost.manager.CmsJautopostHistoryMng;
import com.jeecmstheme.jautopost.manager.CmsJautopostMng;
import com.jeecmstheme.jautopost.manager.CmsJautopostTempMng;
import com.jeecmstheme.jautopost.nodes.Document;
import com.jeecmstheme.jautopost.nodes.Element;
import com.jeecmstheme.jautopost.safety.Cleaner;
import com.jeecmstheme.jautopost.safety.Whitelist;
import com.jeecmstheme.jautopost.select.Elements;
import com.jeecmstheme.jautopost.select.Selector;
import com.jeecmstheme.jautopost.service.JautopostSvc;
import com.jeecmstheme.jautopost.service.UploadSvc;

@Service
public class JautopostSvcImpl implements JautopostSvc {
	private Logger log = LoggerFactory.getLogger(JautopostSvcImpl.class);
	
	private String siteBaseURL;
	private JautopostThread thread;
	
	public boolean start(Integer id) {
		CmsJautopost jauto = cmsJautopostMng.findById(id);
		initialBaseParams(jauto);
		if (jauto == null || jauto.getStatus() == CmsJautopost.START) {
			return false;
		}
		thread = new JautopostThread(jauto);
		thread.start();
		return true;
	}
	
	private void initialBaseParams(CmsJautopost jauto ){
		String protocol = jauto.getSite().getProtocol();
		String domain = jauto.getSite().getBaseDomain();
		Integer port = jauto.getSite().getPort();
		String path = jauto.getSite().getContextPath();
		if(!StringUtil.isBlank(path)){
			this.setSiteBaseURL(protocol+domain+":"+port+path);
		}
		this.setSiteBaseURL(protocol+domain+":"+port);
	}
	
	private void end(CmsJautopost jauto){
		Integer siteId = jauto.getSite().getId();
		cmsJautopostMng.end(jauto.getId());
		CmsJautopost jautopost = cmsJautopostMng.popAcquFromQueue(siteId);
		if (jautopost != null) {
			Integer id = jautopost.getId();
			start(id);
		}
	}
	
	public void end(){
		if(null!=thread){
			thread.setNeedStop(true);
		}
	}
	public void pause(){
		if(null!=thread){
			thread.setNeedStop(true);
		}
	}

	private CmsJautopostFilesMng cmsJautopostFilesMng;
	private CmsJautopostMng cmsJautopostMng;
	private CmsJautopostHistoryMng cmsJautopostHistoryMng;
	private CmsJautopostTempMng cmsJautopostTempMng;
	@Autowired
	private CmsSiteMng siteMng;
	@Autowired
	private CmsConfigMng cmsConfigMng;
	@Autowired
	private UploadSvc upload2QiniuSvc;
	@Autowired
	private ImageSvc imgSvc;
	@Autowired
	private ContentCountMng contentCountMng;
	@Autowired
	private RealPathResolver realPathResolver;
	
	@Autowired
	public void setCmsJautopostFilesMng(CmsJautopostFilesMng cmsJautopostFilesMng) {
		this.cmsJautopostFilesMng = cmsJautopostFilesMng;
	}

	@Autowired
	public void setCmsJautopostMng(CmsJautopostMng cmsJautopostMng) {
		this.cmsJautopostMng = cmsJautopostMng;
	}

	@Autowired
	public void setCmsJautopostHistoryMng(
			CmsJautopostHistoryMng cmsJautopostHistoryMng) {
		this.cmsJautopostHistoryMng = cmsJautopostHistoryMng;
	}

	@Autowired
	public void setCmsJautopostTempMng(
			CmsJautopostTempMng cmsJautopostTempMng) {
		this.cmsJautopostTempMng = cmsJautopostTempMng;
	}

	protected SessionFactory sessionFactory;

	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	protected SessionFactory getSessionFactory() {
		return this.sessionFactory;
	}
	
	public class JautopostThread extends Thread{
		
		private boolean needStop = false;
		
		private CmsJautopost jauto;

		public JautopostThread(CmsJautopost jauto) {
			super(jauto.getClass().getName() + "#" + jauto.getId());
			this.jauto = jauto;
		}

		/**
		 * @return the needStop
		 */
		public boolean isNeedStop() {
			return needStop;
		}

		/**
		 * @param needStop the needStop to set
		 */
		public void setNeedStop(boolean needStop) {
			this.needStop = needStop;
		}

		@Override
		public void run() {
			//----------绑定session到当前线程------------
            SessionFactory sessionFactory = getSessionFactory();
            boolean participate = ConcurrentUtil.bindHibernateSessionToThread(sessionFactory);
            
			if (jauto == null) {
				return;
			}
			jauto = cmsJautopostMng.start(jauto.getId());
			String[] plans = jauto.getAllPlans();
			HttpClient client = new DefaultHttpClient();
			CharsetHandler handler = new CharsetHandler(jauto.getPageEncoding());
			List<String> contentList;
			String url;
			int currNum = jauto.getCurrNum();
			int currItem = jauto.getCurrItem();
			Integer jautoId = jauto.getId();
			for (int i = plans.length - currNum; i >= 0; i--) {
				url = plans[i];
				
				if(isNeedStop()){
					JautopostSvcImpl.this.end(jauto);
					log.info("Jautopost#{} stop ", jauto.getId());
					return;
				}
				//get article url list
				contentList = getContentList(url, jauto);
				String link;
				for (int j = contentList.size() - currItem; j >= 0; j--) {
					if (cmsJautopostMng.isNeedBreak(jauto.getId(),
							plans.length - i, contentList.size() - j,
							contentList.size())) {
						client.getConnectionManager().shutdown();
						log.info("Jautopost#{} breaked", jauto.getId());
						return;
					}
					if (jauto.getPauseTime() > 0) {
						try {
							Thread.sleep(jauto.getPauseTime());
						} catch (InterruptedException e) {
							log.warn(null, e);
						}
					}
					
					if(isNeedStop()){
						JautopostSvcImpl.this.end(jauto);
						log.info("Jautopost#{} stop ", jauto.getId());
						return;
					}
					
					//single content url
					link = contentList.get(j);
					
					float curr = contentList.size() - j;
					float total = contentList.size();
					CmsJautopostTemp temp = JautopostSvcImpl.this.newTemp(
							url, link, contentList.size() - j, curr, total,
							jauto.getSite());
					CmsJautopostHistory history = JautopostSvcImpl.this
							.newHistory(url, link, jauto);
					CmsSite site=jauto.getSite();
					site=siteMng.findById(site.getId());
					String contextPath=cmsConfigMng.get().getContextPath();
					
					//get content
					saveContent(client, handler,contextPath,site.getUploadPath(),jautoId, link,temp,
							history);
				}
				currItem = 1;
			}
			client.getConnectionManager().shutdown();
			JautopostSvcImpl.this.end(jauto);
			log.info("Jautopost#{} complete", jauto.getId());
			
			//----------关闭session------------                   
            ConcurrentUtil.closeHibernateSessionFromThread(participate, sessionFactory);
		}

		private List<String> getContentList(String url, CmsJautopost jauto) {
			String listSelector=jauto.getListSelector();
			List<String> list = new ArrayList<String>();
			try {
				Document doc = null;
				String autoCharset=jauto.getAutoCharset();
				if(!StringUtil.isBlank(autoCharset)&&Constants.YES.equals(autoCharset)){
					doc = Jautopost.connect(url).userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/39.0.2171.95 Safari/537.36").timeout(1000*120).get();
				}else{
					doc = Jautopost.parse(new URL(url).openStream(), jauto.getPageEncoding(), url);
				}
				if(Constants.URL.equals(listSelector)){
					String listByURL=jauto.getListByURL();
			        Elements urlSet = doc.select("a[href~=("+listByURL+")");
			        for(Element surl:urlSet){
			        	if(null!=surl){
			        		String linkURL = surl.attr("abs:href");
			        		if(linkURL.indexOf("http")>-1){
			        			list.add(linkURL);
			        		}
			        	}
			        }
				}else if(Constants.CSS.equals(listSelector)){
					String listByCSS=jauto.getListByCSS();
			        Elements urlSet = doc.select(listByCSS);
			        for(Element surl:urlSet){
			        	if(null!=surl){
			        		String linkURL = surl.attr("abs:href");
			        		if(linkURL.indexOf("http")>-1){
			        			list.add(linkURL);
			        		}
			        	}
			        }
				}else{
					log.error("Jautopost#{} stopped, article set incorrect", jauto.getId());
				}
			} catch (Selector.SelectorParseException e) {
				log.error("Jautopost#{} stopped: "+e.getMessage(), jauto.getId());
			} catch (Exception e) {
				e.printStackTrace();
				log.error("Jautopost#{} stopped, article get list exception: "+e.getMessage(), jauto.getId());
			}
			return list;
		}

		private String extractImg(String imgUrl){
			String outFileName = "";
			String fileUrl = jauto.getSite().getContextPath()!=null? jauto.getSite().getContextPath():"";
			String fileHashTag = "";
			CmsJautopostFiles cmsJautopostFiles = null;
			try {
				HttpClient client = new DefaultHttpClient();
				HttpGet httpget = new HttpGet(new URI(imgUrl));
				HttpResponse response = client.execute(httpget);
				if(HttpStatus.SC_OK != response.getStatusLine().getStatusCode()){
					return "";
				}
				HttpEntity entity = response.getEntity();
				InputStream is = entity.getContent();
				outFileName=UploadUtils.generateFilename(jauto.getSite().getUploadPath(), ParseHelper.getImageExt(entity));
				String realFilePath = realPathResolver.get(outFileName);
				File outFile=new File(realFilePath);
				UploadUtils.checkDirAndCreate(outFile.getParentFile());
				OutputStream os = new FileOutputStream(outFile);
				IOUtils.copy(is, os);
				os.close();
				fileHashTag = QETag.calcETag(realFilePath);
				CmsJautopostCfg cfg =  jauto.getJautopostCfg();
				if(cfg!=null){
					//upload to qiniu
					String filePath = upload2QiniuSvc.upload(realFilePath, cfg);
					//check if need keep in local server
					if(!Constants.YES.equals(cfg.getLocalSave())){
						if(outFile.exists()){
							outFile.delete();
							return filePath;
						}
					}
				}
				
				cmsJautopostFiles = cmsJautopostFilesMng.findByMD5Str(fileHashTag);
				if(cmsJautopostFiles != null){
					String existFilePath = cmsJautopostFiles.getPath();
					File oldFile = new File(realPathResolver.get(existFilePath));
					if(oldFile.exists()){
						outFileName = existFilePath;
						if(outFile.exists()){
							outFile.delete();
						}
					}else{
						cmsJautopostFiles.setId(cmsJautopostFiles.getId());
						cmsJautopostFiles.setMd5Str(fileHashTag);
						cmsJautopostFiles.setPath(outFileName);
						cmsJautopostFilesMng.update(cmsJautopostFiles);
					}
					return fileUrl+outFileName;
				}else{
					CmsJautopostFiles cmsJautopostFilesNew = new CmsJautopostFiles();
					cmsJautopostFilesNew.setMd5Str(fileHashTag);
					cmsJautopostFilesNew.setPath(outFileName);
					cmsJautopostFilesMng.save(cmsJautopostFilesNew);
				}
			}catch(org.apache.http.ConnectionClosedException ioe){
				return "";
			}catch(java.net.ConnectException ioe){
				return "";
			}catch(java.io.FileNotFoundException ioe){
				return "";
			} catch (Exception e) {
				e.printStackTrace();
				return "";
			}
			
			return fileUrl+outFileName;
		}
		
		private Content saveContent(HttpClient client, CharsetHandler handler,String contextPath,
				String uploadPath,Integer jautoId, String url, CmsJautopostTemp temp, CmsJautopostHistory history) {
			CmsJautopost jauto = cmsJautopostMng.findById(jautoId);
			String imgSrc=jauto.getImgSrc();
			//String contentStart=jauto.getContentStart();
			//String contentEnd=jauto.getContentEnd();
			String viewStart=jauto.getViewStart();
			String viewEnd=jauto.getViewEnd();
			String viewIdStart=jauto.getViewIdStart();
			String viewIdEnd=jauto.getViewIdEnd();
			String viewLink=jauto.getViewLink();
			String authorStart=jauto.getAuthorStart();
			String authorEnd=jauto.getAuthorEnd();
			String originStart=jauto.getOriginStart();
			String originEnd=jauto.getOriginEnd();
			String releaseTimeStart=jauto.getReleaseTimeStart();
			String releaseTimeEnd=jauto.getReleaseTimeEnd();
			String descriptionStart=jauto.getDescriptionStart();
			String descriptionEnd=jauto.getDescriptionEnd();
			
			String titleSelector=jauto.getTitleSelector();
			String titleStart=jauto.getTitleByTagBegin();
			String titleEnd=jauto.getTitleByTagEnd();
			String titleByCSS=jauto.getTitleByCSS();
			
			String contentSelector=jauto.getContentSelector();
			String contentStart=jauto.getContentByTagBegin();
			String contentEnd=jauto.getContentByTagEnd();
			String contentByCSS=jauto.getContentByCSS();
			
			String removeLink=jauto.getRemoveContentLink();
			String removeContentByTagBegin=jauto.getRemoveContentByTagBegin();
			String removeContentByTagEnd=jauto.getRemoveContentByTagEnd();
			String removeContentByCSS=jauto.getRemoveContentByCSS();

			String isPagination = jauto.getIsPagination();
			String pageCssSelector = jauto.getPageCssSelector();
			String publishWithPage = jauto.getPublishWithPage();
			
			
			String baseUri = "";
			String titleImg = "";
			history.setJautopost(jauto);
			try {
				URL getBaseuri = new URL(url);
				baseUri = getBaseuri.getProtocol()+"://"+getBaseuri.getHost();
				
				HttpGet httpget = new HttpGet(new URI(url));
				String html = client.execute(httpget, handler);
				String autoCharset=jauto.getAutoCharset();
				if(!StringUtil.isBlank(autoCharset)&&Constants.YES.equals(autoCharset)){
					html = Jautopost.connect(url).userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/39.0.2171.95 Safari/537.36").timeout(1000*60).get().html();
				}
				httpget.releaseConnection();
				List<String> txtUrlList = new ArrayList<String>();
				txtUrlList.add(url);
				if ("YES".equals(isPagination) && StringUtils.isNotEmpty(pageCssSelector)) {
					Document doc = Jautopost.parse(html);
					
					if(StringUtil.isBlank(doc.baseUri())){
						doc.setBaseUri(url);
					}
					
					Elements urlSet = doc.select(pageCssSelector);
					for (Element surl : urlSet) {
						if (null != surl) {
							String linkURL = surl.attr("abs:href");
							if(StringUtils.isEmpty(linkURL)){
								linkURL = surl.attr("href");
								if(linkURL!=null&&linkURL.startsWith("/")){
									Matcher m = Pattern.compile("^http://[^/]+").matcher(url);
									String baseUrl = "";
								    while(m.find()){
								    	baseUrl = m.group();
								    }
									linkURL = baseUrl +linkURL;
								}
							}
							if (linkURL.indexOf("http") > -1) {
								if (!txtUrlList.contains(linkURL)) {
									txtUrlList.add(linkURL);
								}
							}
						}
					}
				}
				
				int start, end;
				String title = "";
				String txt = "";
				//extract title
				if(Constants.TITLE_TAG.equals(titleSelector)){
					start = html.indexOf(titleStart);
					if (start == -1) {
						return handerResult(temp, history, null,
								JautopostResultType.TITLESTARTNOTFOUND);
					}
					start += titleStart.length();
					end = html.indexOf(titleEnd, start);
					if (end == -1) {
						return handerResult(temp, history, null,
								JautopostResultType.TITLEENDNOTFOUND);
					}
					
					title = html.substring(start, end);
					if (cmsJautopostHistoryMng
							.checkExistByProperties(true, title)) {
						return handerResult(temp, history, title,
								JautopostResultType.TITLEEXIST, true);
					}
				}else if(Constants.TITLE_CSS.equals(titleSelector)){
					Document doc = Jautopost.parse(html);
					Element utitle  = null;
					if(titleByCSS.indexOf(":first")!=-1){
						utitle  = doc.select(titleByCSS.substring(0,titleByCSS.indexOf(":first"))).first();
					}else if(titleByCSS.indexOf(":last")!=-1){
						utitle  = doc.select(titleByCSS.substring(0,titleByCSS.indexOf(":last"))).last();
					}else if(titleByCSS.indexOf(":")!=-1){
						//support 0-9 index
						int index = Integer.parseInt(titleByCSS.substring(titleByCSS.indexOf(":")+1, titleByCSS.indexOf(":")+2));
						utitle  = doc.select(titleByCSS.substring(0,titleByCSS.indexOf(":"))).get(index);
					}else{
						utitle  = doc.select(titleByCSS).first();
					}
					
					if(null!=utitle){
						title = utitle.text();
					}else{
						return handerResult(temp, history, null,
								JautopostResultType.TITLENOTFOUND);
					}
					
					if (cmsJautopostHistoryMng
							.checkExistByProperties(true, title)) {
						return handerResult(temp, history, title,
								JautopostResultType.TITLEEXIST, true);
					}
				}else{
					return handerResult(temp, history, null,
							JautopostResultType.TITLEENDNOTFOUND);
				}
				
				int sizeOfcontentSet = txtUrlList.size();
				if(sizeOfcontentSet>0&&"YES".equals(isPagination) ){
					for(int urlx = 0; urlx<sizeOfcontentSet; urlx++ ){
						String tempUrl = txtUrlList.get(urlx);

						HttpGet httpgetrpr = new HttpGet(new URI(tempUrl));
						String htmlpr = client.execute(httpgetrpr, handler);
						if(!StringUtil.isBlank(autoCharset)&&Constants.YES.equals(autoCharset)){
							htmlpr = Jautopost.connect(tempUrl).userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/39.0.2171.95 Safari/537.36").timeout(1000*60).get().html();
						}
						
						//extract content
						if(Constants.CONTENT_TAG.equals(contentSelector)){
							start = htmlpr.indexOf(contentStart);
							if (start == -1) {
								return handerResult(temp, history, title,
										JautopostResultType.CONTENTSTARTNOTFOUND);
							}
							start += contentStart.length();
							end = htmlpr.indexOf(contentEnd, start);
							if (end == -1) {
								return handerResult(temp, history, title,
										JautopostResultType.CONTENTENDNOTFOUND);
							}
							if("YES".equals(publishWithPage)&&urlx<sizeOfcontentSet-1){
								txt += htmlpr.substring(start, end)+"[NextPage][/NextPage]";
							}else{
								txt += htmlpr.substring(start, end);
							}
						}else if(Constants.CONTENT_CSS.equals(contentSelector)){
							Document doc = Jautopost.parse(htmlpr);
							Element utxt  = null;
							if(contentByCSS.indexOf(":first")!=-1){
								utxt  = doc.select(contentByCSS.substring(0,contentByCSS.indexOf(":first"))).first();
							}else if(contentByCSS.indexOf(":last")!=-1){
								utxt  = doc.select(contentByCSS.substring(0,contentByCSS.indexOf(":last"))).last();
							}else if(contentByCSS.indexOf(":")!=-1){
								//support 0-9 index
								int index = Integer.parseInt(contentByCSS.substring(contentByCSS.indexOf(":")+1, contentByCSS.indexOf(":")+2));
								utxt  = doc.select(contentByCSS.substring(0,contentByCSS.indexOf(":"))).get(index);
							}else{
								utxt  = doc.select(contentByCSS).first();
							}
							if(null!=utxt){
								if("YES".equals(publishWithPage)&&urlx<sizeOfcontentSet-1){
									txt +=utxt.html()+"[NextPage][/NextPage]";
								}else{
									txt +=utxt.html();
								}
							}else{
								return handerResult(temp, history, title,
										JautopostResultType.CONTENTNOTFOUND);
							}
						}else{
							return handerResult(temp, history, title,
									JautopostResultType.CONTENTNOTFOUND);
						}
						httpgetrpr.releaseConnection();
					}
				}else{
					if(Constants.CONTENT_TAG.equals(contentSelector)){
						start = html.indexOf(contentStart);
						if (start == -1) {
							return handerResult(temp, history, title,
									JautopostResultType.CONTENTSTARTNOTFOUND);
						}
						start += contentStart.length();
						end = html.indexOf(contentEnd, start);
						if (end == -1) {
							return handerResult(temp, history, title,
									JautopostResultType.CONTENTENDNOTFOUND);
						}
						txt = html.substring(start, end);
					}else if(Constants.CONTENT_CSS.equals(contentSelector)){
						Document doc = Jautopost.parse(html);
						Element utxt  = null;
						if(contentByCSS.indexOf(":first")!=-1){
							utxt  = doc.select(contentByCSS.substring(0,contentByCSS.indexOf(":first"))).first();
						}else if(contentByCSS.indexOf(":last")!=-1){
							utxt  = doc.select(contentByCSS.substring(0,contentByCSS.indexOf(":last"))).last();
						}else if(contentByCSS.indexOf(":")!=-1){
							//support 0-9 index
							int index = Integer.parseInt(contentByCSS.substring(contentByCSS.indexOf(":")+1, contentByCSS.indexOf(":")+2));
							utxt  = doc.select(contentByCSS.substring(0,contentByCSS.indexOf(":"))).get(index);
						}else{
							utxt  = doc.select(contentByCSS).first();
						}
						if(null!=utxt){
							txt = utxt.html();
						}else{
							return handerResult(temp, history, title,
									JautopostResultType.CONTENTNOTFOUND);
						}
					}else{
						return handerResult(temp, history, title,
								JautopostResultType.CONTENTNOTFOUND);
					}
				}
				
				//filter content by tag
				String[] tagBegin = removeContentByTagBegin.split("︾");
				String[] tagEnd = removeContentByTagEnd.split("︾");
				String[] bycss = removeContentByCSS.split("︾");
				String[][] beginEnd =  new String[][]{tagBegin ,tagEnd};
				for(int i = 0; i < beginEnd[0].length; i++) {
					String b = beginEnd[0][i];
				    String e = beginEnd[1][i];
				    if(!StringUtil.isBlank(b)&&!StringUtil.isBlank(e)){
				    	txt = ParseHelper.filterContent(txt, beginEnd[0][i], beginEnd[1][i]);
				    }
				}
				
				//filter content by css
				for(int i = 0; i < bycss.length; i++) {
					if(!StringUtil.isBlank(bycss[i])){
						Document doc = Jautopost.parse(txt);
						Elements removeList  = doc.select(bycss[i]);
						for(Element e:removeList){
							e.remove();
						}
						txt = doc.html();
					}
				}
				
				//handle image, download save and replace url in local
				if(jauto.getImgAcqu()&&!StringUtil.isBlank(imgSrc)){
					Document doc = Jautopost.parse(txt);
					if(StringUtil.isBlank(doc.baseUri())){
						doc.setBaseUri(baseUri);
					}
					Elements imgs = doc.select("["+imgSrc+"]").select("img");
					for (Element img : imgs) {
						   String isrc = img.attr("abs:"+imgSrc+"");
						   if(!StringUtil.isBlank(isrc)){
							   String imgName = extractImg(isrc);
								if("".equals(titleImg)&&!"".equals(imgName)){
									titleImg = imgName;
								}
								img.attr("src", imgName);
						   }
				   }
					txt = doc.html();
				}else if(!imgSrc.equals("src")){
					Document doc = Jautopost.parse(txt);
					if(StringUtil.isBlank(doc.baseUri())){
						doc.setBaseUri(baseUri);
					}
					Elements imgs = doc.select("["+imgSrc+"]").select("img");
					for (Element img : imgs) {
			            	String imgName = img.attr("abs:"+imgSrc+"");
							if("".equals(titleImg)&&!"".equals(imgName)){
								titleImg = imgName;
							}
							img.attr("src", imgName);
				   }
					txt = doc.html();
			    }
				
				String author = null;
				if(StringUtils.isNotBlank(authorStart)){
					start = html.indexOf(authorStart);
					if (start == -1) {
						return handerResult(temp, history, null,
								JautopostResultType.AUTHORSTARTNOTFOUND);
					}
					start += authorStart.length();
					end = html.indexOf(authorEnd, start);
					if (end == -1) {
						return handerResult(temp, history, null,
								JautopostResultType.AUTHORENDNOTFOUND);
					}
					author = html.substring(start, end);
				}
				
				String origin = null;
				if(StringUtils.isNotBlank(originStart)){
					start = html.indexOf(originStart);
					if (start == -1) {
						return handerResult(temp, history, null,
								JautopostResultType.ORIGINSTARTNOTFOUND);
					}
					start += originStart.length();
					end = html.indexOf(originEnd, start);
					if (end == -1) {
						return handerResult(temp, history, null,
								JautopostResultType.ORIGINENDNOTFOUND);
					}
					origin = html.substring(start, end);
				}
				
				String description = null;
				if(StringUtils.isNotBlank(descriptionStart)){
					start = html.indexOf(descriptionStart);
					if (start == -1) {
						return handerResult(temp, history, null,
								JautopostResultType.DESCRISTARTNOTFOUND);
					}
					start += descriptionStart.length();
					end = html.indexOf(descriptionEnd, start);
					if (end == -1) {
						return handerResult(temp, history, null,
								JautopostResultType.DESCRIENDNOTFOUND);
					}
					description = html.substring(start, end);
				}
				
				Date releaseTime = null;
				if(StringUtils.isNotBlank(releaseTimeStart)){
					start = html.indexOf(releaseTimeStart);
					if (start == -1) {
						return handerResult(temp, history, null,
								JautopostResultType.RELEASESTARTNOTFOUND);
					}
					start += releaseTimeStart.length();
					end = html.indexOf(releaseTimeEnd, start);
					if (end == -1) {
						return handerResult(temp, history, null,
								JautopostResultType.RELEASEENDNOTFOUND);
					}
					String releaseDate= html.substring(start, end);
					SimpleDateFormat df=new SimpleDateFormat(jauto.getReleaseTimeFormat());
					releaseTime=df.parse(releaseDate);
				}

				String view = null;
				if(StringUtils.isNotBlank(viewLink)){
					start = html.indexOf(viewIdStart);
					if (start == -1) {
						return handerResult(temp, history, null,
								JautopostResultType.VIEWIDSTARTNOTFOUND);
					}
					start += viewIdStart.length();
					end = html.indexOf(viewIdEnd, start);
					if (end == -1) {
						return handerResult(temp, history, null,
								JautopostResultType.VIEWIDENDNOTFOUND);
					}
					viewLink+=html.substring(start, end);
					HttpGet viewHttpGet = new HttpGet(new URI(viewLink));
					html = client.execute(viewHttpGet, handler);
				}
				if(StringUtils.isNotBlank(viewStart)){
					start = html.indexOf(viewStart);
					if (start == -1) {
						return handerResult(temp, history, null,
								JautopostResultType.VIEWSTARTNOTFOUND);
					}
					start += viewStart.length();
					end = html.indexOf(viewEnd, start);
					if (end == -1) {
						return handerResult(temp, history, null,
								JautopostResultType.VIEWENDNOTFOUND);
					}
					view = html.substring(start, end);
				}
				
				//remove link or add ref="nofollow"
				if(Constants.NEED_REMOVE_LINK.equals(removeLink)){
					txt = ParseHelper.filterLink(txt, true);
				}
				
				//remove html body tag
				Document docContent = Jautopost.parseBodyFragment(txt);
				if(StringUtil.isBlank(docContent.baseUri())){
					docContent.setBaseUri(getSiteBaseURL());
				}
				Whitelist wlist = Whitelist.relaxed();
				wlist.addEnforcedAttribute("a", "rel", "nofollow");
				//if site setting allow relative path.....
				wlist.preserveRelativeLinks(true);
				Cleaner wCleaner = new Cleaner(wlist);
				docContent = wCleaner.clean(docContent);
				txt = docContent.body().html().toString();
				
				//remove blank section
				txt = ParseHelper.removeBlankSection(txt);
				
				Content content = cmsJautopostMng.saveContent(title,titleImg, txt,origin,author,description,releaseTime,
						jautoId, JautopostResultType.SUCCESS, temp, history);
				if(StringUtils.isNotBlank(view)){
					ContentCount count=content.getContentCount();
					int c=Integer.parseInt(view);
					//采集访问一次需减一
					if(StringUtils.isNotBlank(viewLink)){
						c=c-1;
					}
					count.setViews(c);
					contentCountMng.update(count);
				}
				cmsJautopostTempMng.save(temp);
				cmsJautopostHistoryMng.save(history);
				return content;
			} catch (java.net.ConnectException e) {
				log.warn(null, e);
				return handerResult(temp, history, null,
						JautopostResultType.HOSTCONNECTIMEOUT);
			} catch (java.net.SocketTimeoutException e) {
				log.warn(null, e);
				return handerResult(temp, history, null,
						JautopostResultType.HOSTCONNECTIMEOUT);
			} catch (IllegalArgumentException e) {
				log.warn(null, e);
				return handerResult(temp, history, null,
						JautopostResultType.FILTERCSSERROR);
			} catch (org.apache.http.client.HttpResponseException e) {
				log.warn(null, e);
				return handerResult(temp, history, null,
						JautopostResultType.HOSTCONNECTIMEOUT);
			} catch (java.net.UnknownHostException e) {
				log.warn(null, e);
				return handerResult(temp, history, null,
						JautopostResultType.HOSTCONNECTIMEOUT);
			} catch (Exception e) {
				e.printStackTrace();
				log.warn(null, e);
				return handerResult(temp, history, null,
						JautopostResultType.UNKNOW);
			}
		}
		
		private Content handerResult(CmsJautopostTemp temp,
				CmsJautopostHistory history, String title,
				JautopostResultType errorType) {
			return handerResult(temp, history, title, errorType, false);
		}

		private Content handerResult(CmsJautopostTemp temp,
				CmsJautopostHistory history, String title,
				JautopostResultType errorType, Boolean repeat) {
			temp.setDescription(errorType.name());
			temp.setTitle(title);
			cmsJautopostTempMng.save(temp);
			if (!repeat) {
				history.setTitle(title);
				history.setDescription(errorType.name());
				cmsJautopostHistoryMng.save(history);
			}
			return null;
		}
	}

	private CmsJautopostTemp newTemp(String channelUrl, String contentUrl,
			Integer id, Float curr, Float total, CmsSite site) {
		CmsJautopostTemp temp = new CmsJautopostTemp();
		temp.setChannelUrl(channelUrl);
		temp.setContentUrl(contentUrl);
		temp.setSeq(id);
		NumberFormat nf = NumberFormat.getPercentInstance();
		String percent = nf.format(curr / total);
		temp.setPercent(Integer.parseInt(percent.substring(0,
				percent.length() - 1)));
		temp.setSite(site);
		return temp;
	}

	private CmsJautopostHistory newHistory(String channelUrl,
			String contentUrl, CmsJautopost jauto) {
		CmsJautopostHistory history = new CmsJautopostHistory();
		history.setChannelUrl(channelUrl);
		history.setContentUrl(contentUrl);
		history.setJautopost(jauto);
		return history;
	}

	private class CharsetHandler implements ResponseHandler<String> {
		private String charset;

		public CharsetHandler(String charset) {
			this.charset = charset;
		}

		public String handleResponse(HttpResponse response)
				throws ClientProtocolException, IOException {
			StatusLine statusLine = response.getStatusLine();
			if (statusLine.getStatusCode() >= 300) {
				throw new HttpResponseException(statusLine.getStatusCode(),
						statusLine.getReasonPhrase());
			}
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				if (!StringUtils.isBlank(charset)) {
					return EntityUtils.toString(entity, charset);
				} else {
					return EntityUtils.toString(entity);
				}
			} else {
				return null;
			}
		}
	}

	/**
	 * @return the siteBaseURL
	 */
	public String getSiteBaseURL() {
		return siteBaseURL;
	}

	/**
	 * @param siteBaseURL the siteBaseURL to set
	 */
	public void setSiteBaseURL(String siteBaseURL) {
		this.siteBaseURL = siteBaseURL;
	}
}
