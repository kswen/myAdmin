package com.jeecmstheme.jautopost.entity;

import com.jeecmstheme.jautopost.entity.base.BaseCmsJautopostTemp;



public class CmsJautopostTemp extends BaseCmsJautopostTemp {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public CmsJautopostTemp () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public CmsJautopostTemp (java.lang.Integer id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public CmsJautopostTemp (
		java.lang.Integer id,
		java.lang.String channelUrl,
		java.lang.String contentUrl,
		java.lang.Integer percent,
		java.lang.String description,
		java.lang.Integer seq) {

		super (
			id,
			channelUrl,
			contentUrl,
			percent,
			description,
			seq);
	}

/*[CONSTRUCTOR MARKER END]*/


}