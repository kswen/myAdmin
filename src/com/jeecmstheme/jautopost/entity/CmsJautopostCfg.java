package com.jeecmstheme.jautopost.entity;

import com.jeecmstheme.jautopost.entity.base.BaseCmsJautopostCfg;

public class CmsJautopostCfg extends BaseCmsJautopostCfg{

	private static final long serialVersionUID = -6892796580747108609L;

	/* [CONSTRUCTOR MARKER BEGIN] */
	public CmsJautopostCfg () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public CmsJautopostCfg (java.lang.Integer id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public CmsJautopostCfg (
			java.lang.Integer id,
			java.lang.String name,
			java.lang.String domain,
			java.lang.String bucket,
			java.lang.String accessKey,
			java.lang.String secretKey,
			java.lang.String localSave) {

		super (
			id,
			name,
			domain,
			bucket,
			accessKey,
			secretKey,
			localSave);
	}

	/* [CONSTRUCTOR MARKER END] */

}