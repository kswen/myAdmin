package com.jeecmstheme.jautopost.entity;

import com.jeecmstheme.jautopost.entity.base.BaseCmsJautopostHistory;



public class CmsJautopostHistory extends BaseCmsJautopostHistory {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public CmsJautopostHistory () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public CmsJautopostHistory (java.lang.Integer id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public CmsJautopostHistory (
		java.lang.Integer id,
		java.lang.String channelUrl,
		java.lang.String contentUrl) {

		super (
			id,
			channelUrl,
			contentUrl);
	}

/*[CONSTRUCTOR MARKER END]*/


}