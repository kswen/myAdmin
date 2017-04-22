package com.jeecmstheme.jautopost.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.jeecms.common.hibernate3.Finder;
import com.jeecms.common.hibernate3.HibernateBaseDao;
import com.jeecmstheme.jautopost.dao.CmsJautopostFilesDao;
import com.jeecmstheme.jautopost.entity.CmsJautopostFiles;

@Repository
public class CmsJautopostFilesDaoImpl extends
		HibernateBaseDao<CmsJautopostFiles, Integer> implements CmsJautopostFilesDao {

	@Override
	protected Class<CmsJautopostFiles> getEntityClass() {
		return CmsJautopostFiles.class;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CmsJautopostFiles> getList() {
		Finder f = Finder.create("from CmsJautopostFiles bean order by bean.id asc");
		return find(f);
	}

	@Override
	public CmsJautopostFiles findByMD5Str(String md5Str) {
		Criteria crit = createCriteria(Restrictions.eq("md5Str", md5Str)).setMaxResults(1);
		return (CmsJautopostFiles) crit.uniqueResult();
	}

	@Override
	public CmsJautopostFiles findById(Integer id) {
		CmsJautopostFiles entity = get(id);
		return entity;
	}

	@Override
	public CmsJautopostFiles save(CmsJautopostFiles bean) {
		getSession().save(bean);
		return bean;
	}

	@Override
	public CmsJautopostFiles deleteById(Integer id) {
		CmsJautopostFiles entity = super.get(id);
		if (entity != null) {
			getSession().delete(entity);
		}
		return entity;
	}

}