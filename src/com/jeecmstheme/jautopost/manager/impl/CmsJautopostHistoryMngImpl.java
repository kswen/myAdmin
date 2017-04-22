package com.jeecmstheme.jautopost.manager.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeecms.common.hibernate3.Updater;
import com.jeecms.common.page.Pagination;
import com.jeecmstheme.jautopost.dao.CmsJautopostHistoryDao;
import com.jeecmstheme.jautopost.entity.CmsJautopostHistory;
import com.jeecmstheme.jautopost.manager.CmsJautopostHistoryMng;

@Service
@Transactional
public class CmsJautopostHistoryMngImpl implements CmsJautopostHistoryMng {
	@Transactional(readOnly = true)
	public List<CmsJautopostHistory> getList(Integer siteId,Integer acquId) {
		return dao.getList(siteId, acquId);
	}
	
	@Transactional(readOnly = true)
	public Pagination getPage(Integer siteId,Integer acquId, Integer pageNo, Integer pageSize) {
		return dao.getPage(siteId, acquId, pageNo, pageSize);
	}

	@Transactional(readOnly = true)
	public CmsJautopostHistory findById(Integer id) {
		CmsJautopostHistory entity = dao.findById(id);
		return entity;
	}


	public CmsJautopostHistory save(CmsJautopostHistory bean) {
		dao.save(bean);
		return bean;
	}

	public CmsJautopostHistory update(CmsJautopostHistory bean) {
		Updater<CmsJautopostHistory> updater = new Updater<CmsJautopostHistory>(bean);
		bean = dao.updateByUpdater(updater);
		return bean;
	}

	public CmsJautopostHistory deleteById(Integer id) {
		CmsJautopostHistory bean = dao.deleteById(id);
		return bean;
	}

	public CmsJautopostHistory[] deleteByIds(Integer[] ids) {
		CmsJautopostHistory[] beans = new CmsJautopostHistory[ids.length];
		for (int i = 0, len = ids.length; i < len; i++) {
			beans[i] = deleteById(ids[i]);
		}
		return beans;
	}

	public void deleteByJautopost(Integer acquId) {
		 dao.deleteByJautopost(acquId);
	}
	
	public Boolean checkExistByProperties(Boolean title, String value){
		return dao.checkExistByProperties(title, value);
	}

	private CmsJautopostHistoryDao dao;

	@Autowired
	public void setDao(CmsJautopostHistoryDao dao) {
		this.dao = dao;
	}

}