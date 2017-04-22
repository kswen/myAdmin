package com.jeecmstheme.jautopost.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.jeecms.common.hibernate3.Finder;
import com.jeecms.common.hibernate3.HibernateBaseDao;
import com.jeecmstheme.jautopost.dao.CmsJautopostCfgDao;
import com.jeecmstheme.jautopost.entity.CmsJautopostCfg;

@Repository
public class CmsJautopostCfgDaoImpl extends
		HibernateBaseDao<CmsJautopostCfg, Integer> implements CmsJautopostCfgDao {

	@Override
	protected Class<CmsJautopostCfg> getEntityClass() {
		return CmsJautopostCfg.class;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CmsJautopostCfg> getList() {
		Finder f = Finder.create("from CmsJautopostCfg bean order by bean.id asc");
		return find(f);
	}

	@Override
	public CmsJautopostCfg findById(Integer id) {
		CmsJautopostCfg entity = get(id);
		return entity;
	}

	@Override
	public CmsJautopostCfg save(CmsJautopostCfg bean) {
		getSession().save(bean);
		return bean;
	}

	@Override
	public CmsJautopostCfg deleteById(Integer id) {
		CmsJautopostCfg entity = super.get(id);
		if (entity != null) {
			getSession().delete(entity);
		}
		return entity;
	}

}