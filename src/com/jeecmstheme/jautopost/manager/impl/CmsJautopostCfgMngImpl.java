package com.jeecmstheme.jautopost.manager.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeecms.common.hibernate3.Updater;
import com.jeecmstheme.jautopost.dao.CmsJautopostCfgDao;
import com.jeecmstheme.jautopost.entity.CmsJautopostCfg;
import com.jeecmstheme.jautopost.manager.CmsJautopostCfgMng;

@Service
@Transactional
public class CmsJautopostCfgMngImpl implements CmsJautopostCfgMng {
	@Transactional(readOnly = true)
	public List<CmsJautopostCfg> getList() {
		return dao.getList();
	}

	@Transactional
	public CmsJautopostCfg findById(Integer id) {
		CmsJautopostCfg entity = dao.findById(id);
		return entity;
	}

	@Override
	public CmsJautopostCfg save(CmsJautopostCfg bean) {
		return dao.save(bean);
	}

	@Override
	public CmsJautopostCfg deleteById(Integer id) {
		return dao.deleteById(id);
	}
	
	@Override
	public CmsJautopostCfg[] deleteByIds(Integer[] ids) {
		CmsJautopostCfg[] beans = new CmsJautopostCfg[ids.length];
		for (int i = 0,len = ids.length; i < len; i++) {
			beans[i] = deleteById(ids[i]);
		}
		return beans;
	}
	
	private CmsJautopostCfgDao dao;
	@Autowired
	public void setDao(CmsJautopostCfgDao dao) {
		this.dao = dao;
	}
	
	public CmsJautopostCfg update(CmsJautopostCfg bean) {
		Updater<CmsJautopostCfg> updater = new Updater<CmsJautopostCfg>(bean);
		CmsJautopostCfg entity = dao.updateByUpdater(updater);
		return entity;
	}
}