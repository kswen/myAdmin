package com.jeecmstheme.jautopost.service;

import org.json.JSONException;

import com.jeecmstheme.jautopost.entity.CmsJautopostCfg;
import com.qiniu.api.auth.AuthException;

/**
 * @author jeecmstheme.com
 *
 */
public interface UploadSvc {

	public String upload(String fileName, CmsJautopostCfg jconfig) throws AuthException, JSONException ;
}
