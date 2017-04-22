package com.jeecmstheme.jautopost.service.impl;

import org.json.JSONException;

import com.jeecmstheme.jautopost.entity.CmsJautopostCfg;
import com.jeecmstheme.jautopost.helper.StringUtil;
import com.jeecmstheme.jautopost.service.UploadSvc;
import com.qiniu.api.auth.AuthException;
import com.qiniu.api.auth.digest.Mac;
import com.qiniu.api.io.IoApi;
import com.qiniu.api.io.PutExtra;
import com.qiniu.api.io.PutRet;
import com.qiniu.api.rs.PutPolicy;

/**
 * upload files to Qiniu cloud
 * @author jeecmstheme.com
 */
public class Upload2QiniuSvcImpl implements UploadSvc{

	@Override
	public String upload(String fileName, CmsJautopostCfg jconfig) throws AuthException, JSONException {
			String filePath = jconfig.getDomain();
		 	Mac mac = new Mac(jconfig.getAccessKey(), jconfig.getSecretKey());
	        PutPolicy putPolicy = new PutPolicy(jconfig.getBucket());
			String uptoken = putPolicy.token(mac);
	        PutExtra extra = new PutExtra();
	        PutRet ret = IoApi.putFile(uptoken, null, fileName, extra);
	        if(StringUtil.isBlank(ret.getHash())){
	        	 return "";
	        }
	        return "http://"+filePath+"/"+ret.getHash();
	}

	
}