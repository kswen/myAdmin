/**
 * 
 */
package com.jeecmstheme.jautopost;

import org.apache.http.HttpEntity;
import org.apache.http.entity.ContentType;

import com.jeecmstheme.jautopost.helper.StringUtil;
import com.jeecmstheme.jautopost.nodes.Document;
import com.jeecmstheme.jautopost.nodes.Element;
import com.jeecmstheme.jautopost.select.Elements;

/**
 * @author jeecmstheme.com
 *
 */
public class ParseHelper {

	/**
	 * get file extension
	 * @param entity
	 * @return extension without dot(.)
	 */
	public static  String getImageExt(HttpEntity entity){
		ContentType contentType = ContentType.getOrDefault(entity);
		return MIMEType.getExt(contentType.getMimeType()).replaceAll("\\.", "");
	}
	
	/**
	 * @param txt html content
	 * @return txt html content without blank section
	 */
	public static  String removeBlankSection(String txt){
		String[]  blanList = new String[]{"<p><br></p>","<p>&nbsp;</p>","<p></p>","<p><p></p></p>","<p> </p>","<span class=\"img-center-box\" style=\"display:block;text-align:center\"><br></span>"};
		for (int i = 0; i < blanList.length; i++) {
			txt= txt.replace(blanList[i],"");
		}
		return txt;
	}
	
	/**
	 * @param content txt
	 * @param removeContentByTagBegin
	 * @param removeContentByTagEnd
	 * @return content text html code
	 */
	public static  String filterContent(String txt, String removeContentByTagBegin, String removeContentByTagEnd){
		String txtC = txt;
		int start= -1;
		int end = -1;
		while(txtC.indexOf(removeContentByTagBegin) != -1){
			start = txt.indexOf(removeContentByTagBegin);
			if(!StringUtil.isBlank(removeContentByTagBegin)&&!StringUtil.isBlank(removeContentByTagEnd)){
				String firstPartTxt = "";
				String lastPartTxt = "";
				if(start != -1){
					firstPartTxt = txt.substring(0, start);
					String tempTxt = txt.substring(start, txt.length());
					end = tempTxt.indexOf(removeContentByTagEnd);
					if(end != -1){
						lastPartTxt = tempTxt.substring(end+removeContentByTagEnd.length(), tempTxt.length());
					}
				}
				if(!firstPartTxt.equals("")){
					txt = firstPartTxt;
				}else if(!lastPartTxt.equals("")){
					txt = lastPartTxt;
				}else{
					txt += lastPartTxt ;
				}
				
			}
			txtC = txt;
		}
		return txtC;
	}
	
	/**
	 * @param txt html content
	 * @param tag specify tag for remove or get text only
	 * @param needRemove if true then remove selected element, if false then get selected element text only, remove html tag
	 * @return content text html code
	 */
	public static String filterTag(String txt, String tag, boolean needRemove) {
		String txtR = txt;
		if (!StringUtil.isBlank(txt)&&!StringUtil.isBlank(tag)) {
			Document doc = Jautopost.parse(txt);
			Elements selectedList = doc.select(tag);
			if (needRemove) {
				for (Element e : selectedList) {
					e.remove();
				}
			} else {
				for (Element e : selectedList) {
					e.unwrap();
				}
			}
			txtR = doc.html();
		}
		return txtR;
	}
	
	/**
	 * @param txt html content
	 * @param needRemove link href if true, if false add rel="nofollow" attribute
	 * @return
	 */
	public static String filterLink(String txt,boolean needRemove) {
		String txtR = txt;
		if (!StringUtil.isBlank(txt)) {
			Document doc = Jautopost.parse(txt);
			Elements selectedList = doc.select("a");
			if (needRemove) {
				for (Element e : selectedList) {
					e.unwrap();
				}
			}
			txtR = doc.html();
		}
		return txtR;
	}
}
