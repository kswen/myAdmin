package com.jeecmstheme.jautopost.manager.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeecms.cms.entity.main.Content;
import com.jeecms.cms.entity.main.ContentExt;
import com.jeecms.cms.entity.main.ContentTxt;
import com.jeecms.cms.manager.main.ChannelMng;
import com.jeecms.cms.manager.main.CmsModelMng;
import com.jeecms.cms.manager.main.ContentMng;
import com.jeecms.cms.manager.main.ContentTypeMng;
import com.jeecms.cms.service.ChannelDeleteChecker;
import com.jeecms.common.hibernate3.Updater;
import com.jeecms.core.manager.CmsSiteMng;
import com.jeecms.core.manager.CmsUserMng;
import com.jeecmstheme.jautopost.Jautopost;
import com.jeecmstheme.jautopost.dao.CmsJautopostDao;
import com.jeecmstheme.jautopost.entity.CmsJautopost;
import com.jeecmstheme.jautopost.entity.CmsJautopost.JautopostResultType;
import com.jeecmstheme.jautopost.entity.CmsJautopostHistory;
import com.jeecmstheme.jautopost.entity.CmsJautopostTemp;
import com.jeecmstheme.jautopost.manager.CmsJautopostCfgMng;
import com.jeecmstheme.jautopost.manager.CmsJautopostHistoryMng;
import com.jeecmstheme.jautopost.manager.CmsJautopostMng;

@Service
@Transactional
public class CmsJautopostMngImpl implements CmsJautopostMng,
		ChannelDeleteChecker {
	@Transactional(readOnly = true)
	public List<CmsJautopost> getList(Integer siteId) {
		return dao.getList(siteId);
	}

	@Transactional
	public CmsJautopost findById(Integer id) {
		CmsJautopost entity = dao.findById(id);
		return entity;
	}

	public void stop(Integer id) {
		CmsJautopost acqu = findById(id);
		if (acqu == null) {
			return;
		}
		if (acqu.getStatus() == CmsJautopost.START) {
			acqu.setStatus(CmsJautopost.STOP);
		} else if (acqu.getStatus() == CmsJautopost.PAUSE) {
			acqu.setCurrNum(0);
			acqu.setCurrItem(0);
			acqu.setTotalItem(0);
		}
	}

	public void pause(Integer id) {
		CmsJautopost acqu = findById(id);
		if (acqu == null) {
			return;
		}
		if (acqu.getStatus() == CmsJautopost.START) {
			acqu.setStatus(CmsJautopost.PAUSE);
		}
	}

	public CmsJautopost start(Integer id) {
		CmsJautopost acqu = findById(id);
		if (acqu == null) {
			return acqu;
		}
		acqu.setStatus(CmsJautopost.START);
		acqu.setStartTime(new Date());
		acqu.setEndTime(null);
		if (acqu.getCurrNum() <= 0) {
			acqu.setCurrNum(1);
		}
		if (acqu.getCurrItem() <= 0) {
			acqu.setCurrItem(1);
		}
		acqu.setTotalItem(0);
		return acqu;
	}

	public void end(Integer id) {
		CmsJautopost acqu = findById(id);
		if (acqu == null) {
			return;
		}
		acqu.setStatus(CmsJautopost.STOP);
		acqu.setEndTime(new Date());
		acqu.setCurrNum(0);
		acqu.setCurrItem(0);
		acqu.setTotalItem(0);
		acqu.setTotalItem(0);
	}

	public boolean isNeedBreak(Integer id, int currNum, int currItem,
			int totalItem) {
		CmsJautopost acqu = findById(id);
		if (acqu == null) {
			return true;
		} else if (acqu.isPuase()) {
			acqu.setCurrNum(currNum);
			acqu.setCurrItem(currItem);
			acqu.setTotalItem(totalItem);
			acqu.setEndTime(new Date());
			return true;
		} else if (acqu.isStop()) {
			acqu.setCurrNum(0);
			acqu.setCurrItem(0);
			acqu.setTotalItem(0);
			acqu.setEndTime(new Date());
			return true;
		} else {
			acqu.setCurrNum(currNum);
			acqu.setCurrItem(currItem);
			acqu.setTotalItem(totalItem);
			return false;
		}
	}

	public CmsJautopost save(CmsJautopost bean, Integer channelId,
			Integer typeId, Integer jautopostCfgId, Integer userId, Integer siteId) {
		bean.setChannel(channelMng.findById(channelId));
		bean.setType(contentTypeMng.findById(typeId));
		bean.setUser(cmsUserMng.findById(userId));
		bean.setSite(cmsSiteMng.findById(siteId));
		if(null!=jautopostCfgId){
			bean.setJautopostCfg(jautopostCfgMng.findById(jautopostCfgId));
		}
		bean.init();
		dao.save(bean);
		return bean;
	}

	public CmsJautopost update(CmsJautopost bean, Integer channelId,
			Integer typeId, Integer jautopostCfgId) {
		if(null!=jautopostCfgId){
			bean.setJautopostCfg(jautopostCfgMng.findById(jautopostCfgId));
		}
		CmsJautopost cmsJautopost = bean;
		if(cmsJautopost.getIsPagination()==null){
			cmsJautopost.setIsPagination("");
		}
		if(cmsJautopost.getPublishWithPage()==null){
			cmsJautopost.setPublishWithPage("");
		}
		Updater<CmsJautopost> updater = new Updater<CmsJautopost>(bean);
		bean = dao.updateByUpdater(updater);
		bean.setChannel(channelMng.findById(channelId));
		bean.setType(contentTypeMng.findById(typeId));
		return bean;
	}

	public CmsJautopost deleteById(Integer id) {
		//删除采集记录
		jautopostHistoryMng.deleteByJautopost(id);
		CmsJautopost bean = dao.deleteById(id);
		return bean;
	}

	public CmsJautopost[] deleteByIds(Integer[] ids) {
		CmsJautopost[] beans = new CmsJautopost[ids.length];
		for (int i = 0, len = ids.length; i < len; i++) {
			beans[i] = deleteById(ids[i]);
		}
		return beans;
	}

	public Content saveContent(String title,String titleImg, String txt, String origin,
			String author,String description,Date releaseDate,Integer acquId,
			JautopostResultType resultType, CmsJautopostTemp temp,
			CmsJautopostHistory history) {
		CmsJautopost acqu = findById(acquId);
		Content c = new Content();
		c.setSite(acqu.getSite());
		c.setModel(modelMng.getDefModel());
		ContentExt cext = new ContentExt();
		ContentTxt ctxt = new ContentTxt();
		cext.setAuthor(author);
		cext.setOrigin(origin);
		cext.setReleaseDate(releaseDate);
		cext.setTitle(title);
		
		String plainTxt = Jautopost.parse(txt).text();
		if(plainTxt.length()>220){
			plainTxt = plainTxt.substring(0, 220);
		}
		cext.setDescription(plainTxt);
		
		ctxt.setTxt(txt);
		if(!"".equals(titleImg)){
			cext.setTitleImg(titleImg);
		}
		Content content = contentMng.save(c, cext, ctxt,null, null, null,
				null, null, null, null, null, null, acqu.getChannel().getId(),
				acqu.getType().getId(), false,false, acqu.getUser(), false);
		history.setTitle(title);
		history.setContent(content);
		history.setDescription(resultType.name());
		temp.setTitle(title);
		temp.setDescription(resultType.name());
		return content;
	}

	public String checkForChannelDelete(Integer channelId) {
		if (dao.countByChannelId(channelId) > 0) {
			return "cmsJautopost.error.cannotDeleteChannel";
		} else {
			return null;
		}
	}

	public CmsJautopost getStarted(Integer siteId) {
		return dao.getStarted(siteId);
	}

	public Integer hasStarted(Integer siteId) {
		return getStarted(siteId) == null ? 0 : getMaxQueue(siteId) + 1;
	}

	public Integer getMaxQueue(Integer siteId) {
		return dao.getMaxQueue(siteId);
	}

	public void addToQueue(Integer[] ids, Integer queueNum) {
		for (Integer id : ids) {
			CmsJautopost acqu = findById(id);
			if (acqu.getStatus() == CmsJautopost.START || acqu.getQueue() > 0) {
				continue;
			}
			acqu.setQueue(queueNum++);
		}
	}

	public void cancel(Integer siteId, Integer id) {
		CmsJautopost acqu = findById(id);
		Integer queue = acqu.getQueue();
		for (CmsJautopost c : getLargerQueues(siteId, queue)) {
			c.setQueue(c.getQueue() - 1);
		}
		acqu.setQueue(0);
	}

	public List<CmsJautopost> getLargerQueues(Integer siteId, Integer queueNum) {
		return dao.getLargerQueues(siteId, queueNum);
	}

	public CmsJautopost popAcquFromQueue(Integer siteId) {
		CmsJautopost jautopost = dao.popAcquFromQueue(siteId);
		if (jautopost != null) {
			Integer id = jautopost.getId();
			cancel(siteId, id);
		}
		return jautopost;
	}

	private ChannelMng channelMng;
	private ContentMng contentMng;
	private ContentTypeMng contentTypeMng;
	private CmsSiteMng cmsSiteMng;
	private CmsUserMng cmsUserMng;
	private CmsJautopostDao dao;
	@Autowired
	private CmsModelMng modelMng;
	@Autowired
	private CmsJautopostCfgMng jautopostCfgMng;
	@Autowired
	private CmsJautopostHistoryMng jautopostHistoryMng;

	@Autowired
	public void setChannelMng(ChannelMng channelMng) {
		this.channelMng = channelMng;
	}

	@Autowired
	public void setContentMng(ContentMng contentMng) {
		this.contentMng = contentMng;
	}

	@Autowired
	public void setContentTypeMng(ContentTypeMng contentTypeMng) {
		this.contentTypeMng = contentTypeMng;
	}

	@Autowired
	public void setCmsSiteMng(CmsSiteMng cmsSiteMng) {
		this.cmsSiteMng = cmsSiteMng;
	}

	@Autowired
	public void setCmsUserMng(CmsUserMng cmsUserMng) {
		this.cmsUserMng = cmsUserMng;
	}

	@Autowired
	public void setDao(CmsJautopostDao dao) {
		this.dao = dao;
	}

}