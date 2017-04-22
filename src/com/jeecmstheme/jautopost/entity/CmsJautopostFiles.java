package com.jeecmstheme.jautopost.entity;

import com.jeecmstheme.jautopost.entity.base.BaseCmsJautopostFiles;

public class CmsJautopostFiles extends BaseCmsJautopostFiles{

	private static final long serialVersionUID = -6892796580747108609L;

	/* [CONSTRUCTOR MARKER BEGIN] */
	public CmsJautopostFiles () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public CmsJautopostFiles (java.lang.Integer id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public CmsJautopostFiles (
			java.lang.Integer id,
			java.lang.String md5Str,
			java.lang.String path) {

		super (
			id,
			md5Str,
			path);
	}

	/* [CONSTRUCTOR MARKER END] */

}