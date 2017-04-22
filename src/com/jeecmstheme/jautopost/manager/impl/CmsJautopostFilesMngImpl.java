package com.jeecmstheme.jautopost.manager.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeecms.common.hibernate3.Updater;
import com.jeecmstheme.jautopost.dao.CmsJautopostFilesDao;
import com.jeecmstheme.jautopost.entity.CmsJautopostFiles;
import com.jeecmstheme.jautopost.manager.CmsJautopostFilesMng;

@Service
@Transactional
public class CmsJautopostFilesMngImpl implements CmsJautopostFilesMng {
	@Transactional(readOnly = true)
	public List<CmsJautopostFiles> getList() {
		return dao.getList();
	}

	@Transactional
	public CmsJautopostFiles findById(Integer id) {
		CmsJautopostFiles entity = dao.findById(id);
		return entity;
	}

	@Override
	public CmsJautopostFiles findByMD5Str(String md5Str) {
		return dao.findByMD5Str(md5Str);
	}

	@Override
	public CmsJautopostFiles save(CmsJautopostFiles bean) {
		return dao.save(bean);
	}

	@Override
	public CmsJautopostFiles deleteById(Integer id) {
		return dao.deleteById(id);
	}
	
	private CmsJautopostFilesDao dao;
	@Autowired
	public void setDao(CmsJautopostFilesDao dao) {
		this.dao = dao;
	}
	
	public CmsJautopostFiles update(CmsJautopostFiles bean) {
		Updater<CmsJautopostFiles> updater = new Updater<CmsJautopostFiles>(bean);
		CmsJautopostFiles entity = dao.updateByUpdater(updater);
		return entity;
	}
}