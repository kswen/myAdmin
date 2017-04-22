package com.jeecmstheme.jautopost.manager.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeecms.common.hibernate3.Updater;
import com.jeecmstheme.jautopost.dao.CmsJautopostTempDao;
import com.jeecmstheme.jautopost.entity.CmsJautopostTemp;
import com.jeecmstheme.jautopost.manager.CmsJautopostTempMng;

@Service
@Transactional
public class CmsJautopostTempMngImpl implements CmsJautopostTempMng {
	@Transactional(readOnly = true)
	public List<CmsJautopostTemp> getList(Integer siteId) {
		return dao.getList(siteId);
	}

	@Transactional(readOnly = true)
	public CmsJautopostTemp findById(Integer id) {
		CmsJautopostTemp entity = dao.findById(id);
		return entity;
	}

	public CmsJautopostTemp save(CmsJautopostTemp bean) {
		clear(bean.getSite().getId(), bean.getChannelUrl());
		dao.save(bean);
		return bean;
	}

	public CmsJautopostTemp update(CmsJautopostTemp bean) {
		Updater<CmsJautopostTemp> updater = new Updater<CmsJautopostTemp>(
				bean);
		bean = dao.updateByUpdater(updater);
		return bean;
	}

	public CmsJautopostTemp deleteById(Integer id) {
		CmsJautopostTemp bean = dao.deleteById(id);
		return bean;
	}

	public CmsJautopostTemp[] deleteByIds(Integer[] ids) {
		CmsJautopostTemp[] beans = new CmsJautopostTemp[ids.length];
		for (int i = 0, len = ids.length; i < len; i++) {
			beans[i] = deleteById(ids[i]);
		}
		return beans;
	}

	public Integer getPercent(Integer siteId) {
		return dao.getPercent(siteId);
	}

	public void clear(Integer siteId) {
		dao.clear(siteId, null);
	}

	public void clear(Integer siteId, String channelUrl) {
		dao.clear(siteId, channelUrl);
	}

	private CmsJautopostTempDao dao;

	@Autowired
	public void setDao(CmsJautopostTempDao dao) {
		this.dao = dao;
	}
}