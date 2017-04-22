package com.jeecmstheme.jautopost.entity.base;

import java.io.Serializable;

import com.jeecmstheme.jautopost.entity.CmsJautopost;


/**
 * This is an object that contains data related to the jc_jautopost table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="jc_jautopost"
 */

public abstract class BaseCmsJautopost  implements Serializable {
	
	private static final long serialVersionUID = 1368238210846212351L;
	public static String REF = "CmsJautopost";
	public static String PROP_USER = "user";
	public static String PROP_LINK_START = "linkStart";
	public static String PROP_DESCRIPTION_END = "descriptionEnd";
	public static String PROP_CHANNEL = "channel";
	public static String PROP_DYNAMIC_START = "dynamicStart";
	public static String PROP_CONTENT_START = "contentStart";
	public static String PROP_TYPE = "type";
	public static String PROP_PAGINATION_END = "paginationEnd";
	public static String PROP_LINKSET_START = "linksetStart";
	public static String PROP_DYNAMIC_ADDR = "dynamicAddr";
	public static String PROP_LINKSET_END = "linksetEnd";
	public static String PROP_KEYWORDS_END = "keywordsEnd";
	public static String PROP_CURR_NUM = "currNum";
	public static String PROP_QUEUE = "queue";
	public static String PROP_LINK_END = "linkEnd";
	public static String PROP_START_TIME = "startTime";
	public static String PROP_PAGINATION_START = "paginationStart";
	public static String PROP_SITE = "site";
	public static String PROP_TOTAL_ITEM = "totalItem";
	public static String PROP_CURR_ITEM = "currItem";
	public static String PROP_NAME = "name";
	public static String PROP_STATUS = "status";
	public static String PROP_PAUSE_TIME = "pauseTime";
	public static String PROP_IMG_SRC = "imgSrc";
	public static String PROP_AUTO_SET = "autoCharset";
	public static String PROP_CONTENT_END = "contentEnd";
	public static String PROP_PAGE_ENCODING = "pageEncoding";
	public static String PROP_ID = "id";
	public static String PROP_PLAN_LIST = "planList";
	public static String PROP_END_TIME = "endTime";
	public static String PROP_KEYWORDS_START = "keywordsStart";
	public static String PROP_DESCRIPTION_START = "descriptionStart";
	public static String PROP_DYNAMIC_END = "dynamicEnd";


	// constructors
	public BaseCmsJautopost () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseCmsJautopost (java.lang.Integer id) {
		this.setId(id);
		initialize();
	}

	/**
	 * Constructor for required fields
	 */
	public BaseCmsJautopost (
		java.lang.Integer id,
		com.jeecms.core.entity.CmsUser user,
		com.jeecms.cms.entity.main.ContentType type,
		com.jeecms.core.entity.CmsSite site,
		com.jeecms.cms.entity.main.Channel channel,
		com.jeecmstheme.jautopost.entity.CmsJautopostCfg jautopostCfg,
		java.lang.String name,
		java.lang.Integer status,
		java.lang.Integer currNum,
		java.lang.Integer currItem,
		java.lang.Integer totalItem,
		java.lang.Integer pauseTime,
		java.lang.String pageEncoding,
		java.lang.Integer queue) {

		this.setId(id);
		this.setUser(user);
		this.setType(type);
		this.setSite(site);
		this.setChannel(channel);
		this.setName(name);
		this.setStatus(status);
		this.setCurrNum(currNum);
		this.setCurrItem(currItem);
		this.setTotalItem(totalItem);
		this.setPauseTime(pauseTime);
		this.setPageEncoding(pageEncoding);
		this.setQueue(queue);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.Integer id;

	// fields
	private java.lang.String name;
	private java.util.Date startTime;
	private java.util.Date endTime;
	private java.lang.Integer status;
	private java.lang.Integer currNum;
	private java.lang.Integer currItem;
	private java.lang.Integer totalItem;
	private java.lang.Integer pauseTime;
	private java.lang.Boolean imgAcqu;
	private java.lang.String pageEncoding;
	private java.lang.String planList;
	private java.lang.String dynamicAddr;
	private java.lang.Integer dynamicStart;
	private java.lang.Integer dynamicEnd;
	private java.lang.String linksetStart;
	private java.lang.String linksetEnd;
	private java.lang.String linkStart;
	private java.lang.String linkEnd;
	private java.lang.String imgSrc;
	private java.lang.String autoCharset;
	private java.lang.String keywordsStart;
	private java.lang.String keywordsEnd;
	private java.lang.String descriptionStart;
	private java.lang.String descriptionEnd;
	private java.lang.String contentStart;
	private java.lang.String contentEnd;
	private java.lang.String paginationStart;
	private java.lang.String paginationEnd;
	private java.lang.String viewStart;
	private java.lang.String viewEnd;
	private java.lang.String viewIdStart;
	private java.lang.String viewIdEnd;
	private java.lang.String viewLink;
	private java.lang.String releaseTimeStart;
	private java.lang.String releaseTimeEnd;
	private java.lang.String releaseTimeFormat;
	private java.lang.String authorStart;
	private java.lang.String authorEnd;
	private java.lang.String originStart;
	private java.lang.String originEnd;
	private java.lang.String contentPrefix;
	private java.lang.String imgPrefix;
	private java.lang.Integer queue;
	private java.lang.String listSelector;
	private java.lang.String listByURL ;
	private java.lang.String listByCSS ;
	private java.lang.String titleSelector; 
	private java.lang.String titleByTagBegin ;
	private java.lang.String titleByTagEnd ;
	private java.lang.String titleByCSS ;
	private java.lang.String contentSelector ;
	private java.lang.String contentByTagBegin ;
	private java.lang.String contentByTagEnd ;
	private java.lang.String contentByCSS ;
	private java.lang.String isPagination ;
	private java.lang.String pageCssSelector ;
	private java.lang.String publishWithPage ;
	private java.lang.String removeContentLink ;
	private java.lang.String removeContentByTagBegin ;
	private java.lang.String removeContentByTagEnd ;
	private java.lang.String removeContentByCSS ;

	// many to one
	private com.jeecms.core.entity.CmsUser user;
	private com.jeecmstheme.jautopost.entity.CmsJautopostCfg jautopostCfg;
	private com.jeecms.cms.entity.main.ContentType type;
	private com.jeecms.core.entity.CmsSite site;
	private com.jeecms.cms.entity.main.Channel channel;


	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  generator-class="identity"
     *  column="jautopost_id"
     */
	public java.lang.Integer getId () {
		return id;
	}

	/**
	 * Set the unique identifier of this class
	 * @param id the new ID
	 */
	public void setId (java.lang.Integer id) {
		this.id = id;
		this.hashCode = Integer.MIN_VALUE;
	}




	/**
	 * Return the value associated with the column: acq_name
	 */
	public java.lang.String getName () {
		return name;
	}

	/**
	 * Set the value related to the column: acq_name
	 * @param name the acq_name value
	 */
	public void setName (java.lang.String name) {
		this.name = name;
	}


	/**
	 * Return the value associated with the column: start_time
	 */
	public java.util.Date getStartTime () {
		return startTime;
	}

	/**
	 * Set the value related to the column: start_time
	 * @param startTime the start_time value
	 */
	public void setStartTime (java.util.Date startTime) {
		this.startTime = startTime;
	}


	/**
	 * Return the value associated with the column: end_time
	 */
	public java.util.Date getEndTime () {
		return endTime;
	}

	/**
	 * Set the value related to the column: end_time
	 * @param endTime the end_time value
	 */
	public void setEndTime (java.util.Date endTime) {
		this.endTime = endTime;
	}


	/**
	 * Return the value associated with the column: status
	 */
	public java.lang.Integer getStatus () {
		return status;
	}

	/**
	 * Set the value related to the column: status
	 * @param status the status value
	 */
	public void setStatus (java.lang.Integer status) {
		this.status = status;
	}


	/**
	 * Return the value associated with the column: curr_num
	 */
	public java.lang.Integer getCurrNum () {
		return currNum;
	}

	/**
	 * Set the value related to the column: curr_num
	 * @param currNum the curr_num value
	 */
	public void setCurrNum (java.lang.Integer currNum) {
		this.currNum = currNum;
	}


	/**
	 * Return the value associated with the column: curr_item
	 */
	public java.lang.Integer getCurrItem () {
		return currItem;
	}

	/**
	 * Set the value related to the column: curr_item
	 * @param currItem the curr_item value
	 */
	public void setCurrItem (java.lang.Integer currItem) {
		this.currItem = currItem;
	}


	/**
	 * Return the value associated with the column: total_item
	 */
	public java.lang.Integer getTotalItem () {
		return totalItem;
	}

	/**
	 * Set the value related to the column: total_item
	 * @param totalItem the total_item value
	 */
	public void setTotalItem (java.lang.Integer totalItem) {
		this.totalItem = totalItem;
	}


	/**
	 * Return the value associated with the column: pause_time
	 */
	public java.lang.Integer getPauseTime () {
		return pauseTime;
	}

	/**
	 * Set the value related to the column: pause_time
	 * @param pauseTime the pause_time value
	 */
	public void setPauseTime (java.lang.Integer pauseTime) {
		this.pauseTime = pauseTime;
	}

	

	public java.lang.Boolean getImgAcqu() {
		return imgAcqu;
	}

	public void setImgAcqu(java.lang.Boolean imgAcqu) {
		this.imgAcqu = imgAcqu;
	}

	/**
	 * Return the value associated with the column: page_encoding
	 */
	public java.lang.String getPageEncoding () {
		return pageEncoding;
	}

	/**
	 * Set the value related to the column: page_encoding
	 * @param pageEncoding the page_encoding value
	 */
	public void setPageEncoding (java.lang.String pageEncoding) {
		this.pageEncoding = pageEncoding;
	}


	/**
	 * Return the value associated with the column: plan_list
	 */
	public java.lang.String getPlanList () {
		return planList;
	}

	/**
	 * Set the value related to the column: plan_list
	 * @param planList the plan_list value
	 */
	public void setPlanList (java.lang.String planList) {
		this.planList = planList;
	}


	/**
	 * Return the value associated with the column: dynamic_addr
	 */
	public java.lang.String getDynamicAddr () {
		return dynamicAddr;
	}

	/**
	 * Set the value related to the column: dynamic_addr
	 * @param dynamicAddr the dynamic_addr value
	 */
	public void setDynamicAddr (java.lang.String dynamicAddr) {
		this.dynamicAddr = dynamicAddr;
	}


	/**
	 * Return the value associated with the column: dynamic_start
	 */
	public java.lang.Integer getDynamicStart () {
		return dynamicStart;
	}

	/**
	 * Set the value related to the column: dynamic_start
	 * @param dynamicStart the dynamic_start value
	 */
	public void setDynamicStart (java.lang.Integer dynamicStart) {
		this.dynamicStart = dynamicStart;
	}


	/**
	 * Return the value associated with the column: dynamic_end
	 */
	public java.lang.Integer getDynamicEnd () {
		return dynamicEnd;
	}

	/**
	 * Set the value related to the column: dynamic_end
	 * @param dynamicEnd the dynamic_end value
	 */
	public void setDynamicEnd (java.lang.Integer dynamicEnd) {
		this.dynamicEnd = dynamicEnd;
	}


	/**
	 * Return the value associated with the column: linkset_start
	 */
	public java.lang.String getLinksetStart () {
		return linksetStart;
	}

	/**
	 * Set the value related to the column: linkset_start
	 * @param linksetStart the linkset_start value
	 */
	public void setLinksetStart (java.lang.String linksetStart) {
		this.linksetStart = linksetStart;
	}


	/**
	 * Return the value associated with the column: linkset_end
	 */
	public java.lang.String getLinksetEnd () {
		return linksetEnd;
	}

	/**
	 * Set the value related to the column: linkset_end
	 * @param linksetEnd the linkset_end value
	 */
	public void setLinksetEnd (java.lang.String linksetEnd) {
		this.linksetEnd = linksetEnd;
	}


	/**
	 * Return the value associated with the column: link_start
	 */
	public java.lang.String getLinkStart () {
		return linkStart;
	}

	/**
	 * Set the value related to the column: link_start
	 * @param linkStart the link_start value
	 */
	public void setLinkStart (java.lang.String linkStart) {
		this.linkStart = linkStart;
	}


	/**
	 * Return the value associated with the column: link_end
	 */
	public java.lang.String getLinkEnd () {
		return linkEnd;
	}

	/**
	 * Set the value related to the column: link_end
	 * @param linkEnd the link_end value
	 */
	public void setLinkEnd (java.lang.String linkEnd) {
		this.linkEnd = linkEnd;
	}


	/**
	 * Return the value associated with the column: img_src
	 */
	public java.lang.String getImgSrc() {
		return imgSrc;
	}

	/**
	 * Set the value related to the column: img_src
	 * @paImgSrctart the img_src value
	 */
	public void setImgSrc(java.lang.String imgSrc) {
		this.imgSrc = imgSrc;
	}


	/**
	 * Return the value associated with the column: auto_charset
	 */
	public java.lang.String getAutoCharset () {
		return autoCharset;
	}

	/**
	 * Set the value related to the column: autoCharset
	 * @param titleEnd the auto_charset value
	 */
	public void setAutoCharset (java.lang.String autoCharset) {
		this.autoCharset = autoCharset;
	}


	/**
	 * Return the value associated with the column: keywords_start
	 */
	public java.lang.String getKeywordsStart () {
		return keywordsStart;
	}

	/**
	 * Set the value related to the column: keywords_start
	 * @param keywordsStart the keywords_start value
	 */
	public void setKeywordsStart (java.lang.String keywordsStart) {
		this.keywordsStart = keywordsStart;
	}


	/**
	 * Return the value associated with the column: keywords_end
	 */
	public java.lang.String getKeywordsEnd () {
		return keywordsEnd;
	}

	/**
	 * Set the value related to the column: keywords_end
	 * @param keywordsEnd the keywords_end value
	 */
	public void setKeywordsEnd (java.lang.String keywordsEnd) {
		this.keywordsEnd = keywordsEnd;
	}


	/**
	 * Return the value associated with the column: description_start
	 */
	public java.lang.String getDescriptionStart () {
		return descriptionStart;
	}

	/**
	 * Set the value related to the column: description_start
	 * @param descriptionStart the description_start value
	 */
	public void setDescriptionStart (java.lang.String descriptionStart) {
		this.descriptionStart = descriptionStart;
	}


	/**
	 * Return the value associated with the column: description_end
	 */
	public java.lang.String getDescriptionEnd () {
		return descriptionEnd;
	}

	/**
	 * Set the value related to the column: description_end
	 * @param descriptionEnd the description_end value
	 */
	public void setDescriptionEnd (java.lang.String descriptionEnd) {
		this.descriptionEnd = descriptionEnd;
	}


	/**
	 * Return the value associated with the column: content_start
	 */
	public java.lang.String getContentStart () {
		return contentStart;
	}

	/**
	 * Set the value related to the column: content_start
	 * @param contentStart the content_start value
	 */
	public void setContentStart (java.lang.String contentStart) {
		this.contentStart = contentStart;
	}


	/**
	 * Return the value associated with the column: content_end
	 */
	public java.lang.String getContentEnd () {
		return contentEnd;
	}

	/**
	 * Set the value related to the column: content_end
	 * @param contentEnd the content_end value
	 */
	public void setContentEnd (java.lang.String contentEnd) {
		this.contentEnd = contentEnd;
	}


	/**
	 * Return the value associated with the column: pagination_start
	 */
	public java.lang.String getPaginationStart () {
		return paginationStart;
	}

	/**
	 * Set the value related to the column: pagination_start
	 * @param paginationStart the pagination_start value
	 */
	public void setPaginationStart (java.lang.String paginationStart) {
		this.paginationStart = paginationStart;
	}


	/**
	 * Return the value associated with the column: pagination_end
	 */
	public java.lang.String getPaginationEnd () {
		return paginationEnd;
	}

	/**
	 * Set the value related to the column: pagination_end
	 * @param paginationEnd the pagination_end value
	 */
	public void setPaginationEnd (java.lang.String paginationEnd) {
		this.paginationEnd = paginationEnd;
	}

	

	public java.lang.String getViewStart() {
		return viewStart;
	}

	public void setViewStart(java.lang.String viewStart) {
		this.viewStart = viewStart;
	}

	public java.lang.String getViewEnd() {
		return viewEnd;
	}

	public void setViewEnd(java.lang.String viewEnd) {
		this.viewEnd = viewEnd;
	}

	public java.lang.String getViewIdStart() {
		return viewIdStart;
	}

	public void setViewIdStart(java.lang.String viewIdStart) {
		this.viewIdStart = viewIdStart;
	}

	public java.lang.String getViewIdEnd() {
		return viewIdEnd;
	}

	public void setViewIdEnd(java.lang.String viewIdEnd) {
		this.viewIdEnd = viewIdEnd;
	}

	public java.lang.String getViewLink() {
		return viewLink;
	}

	public void setViewLink(java.lang.String viewLink) {
		this.viewLink = viewLink;
	}

	public java.lang.String getReleaseTimeStart() {
		return releaseTimeStart;
	}

	public void setReleaseTimeStart(java.lang.String releaseTimeStart) {
		this.releaseTimeStart = releaseTimeStart;
	}

	public java.lang.String getReleaseTimeEnd() {
		return releaseTimeEnd;
	}

	public void setReleaseTimeEnd(java.lang.String releaseTimeEnd) {
		this.releaseTimeEnd = releaseTimeEnd;
	}
	

	public java.lang.String getReleaseTimeFormat() {
		return releaseTimeFormat;
	}

	public void setReleaseTimeFormat(java.lang.String releaseTimeFormat) {
		this.releaseTimeFormat = releaseTimeFormat;
	}

	public java.lang.String getAuthorStart() {
		return authorStart;
	}

	public void setAuthorStart(java.lang.String authorStart) {
		this.authorStart = authorStart;
	}

	public java.lang.String getAuthorEnd() {
		return authorEnd;
	}

	public void setAuthorEnd(java.lang.String authorEnd) {
		this.authorEnd = authorEnd;
	}

	public java.lang.String getOriginStart() {
		return originStart;
	}

	public void setOriginStart(java.lang.String originStart) {
		this.originStart = originStart;
	}

	public java.lang.String getOriginEnd() {
		return originEnd;
	}

	public void setOriginEnd(java.lang.String originEnd) {
		this.originEnd = originEnd;
	}

	public java.lang.String getContentPrefix() {
		return contentPrefix;
	}

	public void setContentPrefix(java.lang.String contentPrefix) {
		this.contentPrefix = contentPrefix;
	}

	public java.lang.String getImgPrefix() {
		return imgPrefix;
	}

	public void setImgPrefix(java.lang.String imgPrefix) {
		this.imgPrefix = imgPrefix;
	}

	/**
	 * Return the value associated with the column: queue
	 */
	public java.lang.Integer getQueue () {
		return queue;
	}

	/**
	 * Set the value related to the column: queue
	 * @param queue the queue value
	 */
	public void setQueue (java.lang.Integer queue) {
		this.queue = queue;
	}


	/**
	 * @return the listSelector
	 */
	public java.lang.String getListSelector() {
		return listSelector;
	}

	/**
	 * @param listSelector the listSelector to set
	 */
	public void setListSelector(java.lang.String listSelector) {
		this.listSelector = listSelector;
	}

	/**
	 * @return the listByURL
	 */
	public java.lang.String getListByURL() {
		return listByURL;
	}

	/**
	 * @param listByURL the listByURL to set
	 */
	public void setListByURL(java.lang.String listByURL) {
		this.listByURL = listByURL;
	}

	/**
	 * @return the listByCSS
	 */
	public java.lang.String getListByCSS() {
		return listByCSS;
	}

	/**
	 * @param listByCSS the listByCSS to set
	 */
	public void setListByCSS(java.lang.String listByCSS) {
		this.listByCSS = listByCSS;
	}

	/**
	 * @return the titleSelector
	 */
	public java.lang.String getTitleSelector() {
		return titleSelector;
	}

	/**
	 * @param titleSelector the titleSelector to set
	 */
	public void setTitleSelector(java.lang.String titleSelector) {
		this.titleSelector = titleSelector;
	}

	/**
	 * @return the titleByTagBegin
	 */
	public java.lang.String getTitleByTagBegin() {
		return titleByTagBegin;
	}

	/**
	 * @param titleByTagBegin the titleByTagBegin to set
	 */
	public void setTitleByTagBegin(java.lang.String titleByTagBegin) {
		this.titleByTagBegin = titleByTagBegin;
	}

	/**
	 * @return the titleByTagEnd
	 */
	public java.lang.String getTitleByTagEnd() {
		return titleByTagEnd;
	}

	/**
	 * @param titleByTagEnd the titleByTagEnd to set
	 */
	public void setTitleByTagEnd(java.lang.String titleByTagEnd) {
		this.titleByTagEnd = titleByTagEnd;
	}

	/**
	 * @return the titleByCSS
	 */
	public java.lang.String getTitleByCSS() {
		return titleByCSS;
	}

	/**
	 * @param titleByCSS the titleByCSS to set
	 */
	public void setTitleByCSS(java.lang.String titleByCSS) {
		this.titleByCSS = titleByCSS;
	}

	/**
	 * @return the contentSelector
	 */
	public java.lang.String getContentSelector() {
		return contentSelector;
	}

	/**
	 * @param contentSelector the contentSelector to set
	 */
	public void setContentSelector(java.lang.String contentSelector) {
		this.contentSelector = contentSelector;
	}

	/**
	 * @return the contentByTagBegin
	 */
	public java.lang.String getContentByTagBegin() {
		return contentByTagBegin;
	}

	/**
	 * @param contentByTagBegin the contentByTagBegin to set
	 */
	public void setContentByTagBegin(java.lang.String contentByTagBegin) {
		this.contentByTagBegin = contentByTagBegin;
	}

	/**
	 * @return the contentByTagEnd
	 */
	public java.lang.String getContentByTagEnd() {
		return contentByTagEnd;
	}

	/**
	 * @param contentByTagEnd the contentByTagEnd to set
	 */
	public void setContentByTagEnd(java.lang.String contentByTagEnd) {
		this.contentByTagEnd = contentByTagEnd;
	}

	/**
	 * @return the contentByCSS
	 */
	public java.lang.String getContentByCSS() {
		return contentByCSS;
	}

	/**
	 * @param contentByCSS the contentByCSS to set
	 */
	public void setContentByCSS(java.lang.String contentByCSS) {
		this.contentByCSS = contentByCSS;
	}

	/**
	 * @return the removeContentLink
	 */
	public java.lang.String getRemoveContentLink() {
		return removeContentLink;
	}

	/**
	 * @param removeContentLink the removeContentLink to set
	 */
	public void setRemoveContentLink(java.lang.String removeContentLink) {
		this.removeContentLink = removeContentLink;
	}

	/**
	 * @return the removeContentByTagBegin
	 */
	public java.lang.String getRemoveContentByTagBegin() {
		return removeContentByTagBegin;
	}

	/**
	 * @param removeContentByTagBegin the removeContentByTagBegin to set
	 */
	public void setRemoveContentByTagBegin(java.lang.String removeContentByTagBegin) {
		this.removeContentByTagBegin = removeContentByTagBegin;
	}

	/**
	 * @return the removeContentByTagEnd
	 */
	public java.lang.String getRemoveContentByTagEnd() {
		return removeContentByTagEnd;
	}

	/**
	 * @param removeContentByTagEnd the removeContentByTagEnd to set
	 */
	public void setRemoveContentByTagEnd(java.lang.String removeContentByTagEnd) {
		this.removeContentByTagEnd = removeContentByTagEnd;
	}

	/**
	 * @return the removeContentByCSS
	 */
	public java.lang.String getRemoveContentByCSS() {
		return removeContentByCSS;
	}

	/**
	 * @param removeContentByCSS the removeContentByCSS to set
	 */
	public void setRemoveContentByCSS(java.lang.String removeContentByCSS) {
		this.removeContentByCSS = removeContentByCSS;
	}
	
	/**
	 * Return the value associated with the column: user_id
	 */
	public com.jeecms.core.entity.CmsUser getUser () {
		return user;
	}

	/**
	 * Set the value related to the column: user_id
	 * @param user the user_id value
	 */
	public void setUser (com.jeecms.core.entity.CmsUser user) {
		this.user = user;
	}

	/**
	 * @return the jautopostCfg
	 */
	public com.jeecmstheme.jautopost.entity.CmsJautopostCfg getJautopostCfg() {
		return jautopostCfg;
	}

	/**
	 * @param jautopostCfg the jautopostCfg to set
	 */
	public void setJautopostCfg(
			com.jeecmstheme.jautopost.entity.CmsJautopostCfg jautopostCfg) {
		this.jautopostCfg = jautopostCfg;
	}

	/**
	 * Return the value associated with the column: type_id
	 */
	public com.jeecms.cms.entity.main.ContentType getType () {
		return type;
	}

	/**
	 * Set the value related to the column: type_id
	 * @param type the type_id value
	 */
	public void setType (com.jeecms.cms.entity.main.ContentType type) {
		this.type = type;
	}


	/**
	 * Return the value associated with the column: site_id
	 */
	public com.jeecms.core.entity.CmsSite getSite () {
		return site;
	}

	/**
	 * Set the value related to the column: site_id
	 * @param site the site_id value
	 */
	public void setSite (com.jeecms.core.entity.CmsSite site) {
		this.site = site;
	}

	/**
	 * Return the value associated with the column: channel_id
	 */
	public com.jeecms.cms.entity.main.Channel getChannel () {
		return channel;
	}

	/**
	 * Set the value related to the column: channel_id
	 * @param channel the channel_id value
	 */
	public void setChannel (com.jeecms.cms.entity.main.Channel channel) {
		this.channel = channel;
	}

	/**
	 * @return the isPagination
	 */
	public java.lang.String getIsPagination() {
		return isPagination;
	}

	/**
	 * @param isPagination the isPagination to set
	 */
	public void setIsPagination(java.lang.String isPagination) {
		this.isPagination = isPagination;
	}

	/**
	 * @return the pageCssSelector
	 */
	public java.lang.String getPageCssSelector() {
		return pageCssSelector;
	}

	/**
	 * @param pageCssSelector the pageCssSelector to set
	 */
	public void setPageCssSelector(java.lang.String pageCssSelector) {
		this.pageCssSelector = pageCssSelector;
	}

	/**
	 * @return the publishWithPage
	 */
	public java.lang.String getPublishWithPage() {
		return publishWithPage;
	}

	/**
	 * @param publishWithPage the publishWithPage to set
	 */
	public void setPublishWithPage(java.lang.String publishWithPage) {
		this.publishWithPage = publishWithPage;
	}

	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof CmsJautopost)) return false;
		else {
			CmsJautopost cmsJautopost = (CmsJautopost) obj;
			if (null == this.getId() || null == cmsJautopost.getId()) return false;
			else return (this.getId().equals(cmsJautopost.getId()));
		}
	}

	public int hashCode () {
		if (Integer.MIN_VALUE == this.hashCode) {
			if (null == this.getId()) return super.hashCode();
			else {
				String hashStr = this.getClass().getName() + ":" + this.getId().hashCode();
				this.hashCode = hashStr.hashCode();
			}
		}
		return this.hashCode;
	}


	public String toString () {
		return super.toString();
	}
}