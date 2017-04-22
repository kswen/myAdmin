package com.jeecmstheme.jautopost.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.jeecms.common.hibernate3.Finder;
import com.jeecms.common.hibernate3.HibernateBaseDao;
import com.jeecms.common.page.Pagination;
import com.jeecmstheme.jautopost.dao.CmsJautopostHistoryDao;
import com.jeecmstheme.jautopost.entity.CmsJautopostHistory;

@Repository
public class CmsJautopostHistoryDaoImpl extends
		HibernateBaseDao<CmsJautopostHistory, Integer> implements
		CmsJautopostHistoryDao {
	@SuppressWarnings("unchecked")
	public List<CmsJautopostHistory> getList(Integer siteId, Integer acquId) {
		Finder f = Finder.create("from CmsJautopostHistory bean where 1=1");
		if (siteId != null) {
			f.append(" and bean.jautopost.site.id=:siteId");
			f.setParam("siteId", siteId);
		}
		if (acquId != null) {
			f.append(" and bean.jautopost.id=:acquId");
			f.setParam("acquId", acquId);
		}
		f.append(" order by bean.id asc");
		return find(f);
	}

	public Pagination getPage(Integer siteId, Integer acquId, Integer pageNo,
			Integer pageSize) {
		Finder f = Finder.create("from CmsJautopostHistory bean where 1=1");
		if (siteId != null) {
			f.append(" and bean.jautopost.site.id=:siteId");
			f.setParam("siteId", siteId);
		}
		if (acquId != null) {
			f.append(" and bean.jautopost.id=:acquId");
			f.setParam("acquId", acquId);
		}
		f.append(" order by bean.id desc");
		return find(f, pageNo, pageSize);
	}

	public CmsJautopostHistory findById(Integer id) {
		CmsJautopostHistory entity = get(id);
		return entity;
	}

	public CmsJautopostHistory save(CmsJautopostHistory bean) {
		getSession().save(bean);
		return bean;
	}

	public CmsJautopostHistory deleteById(Integer id) {
		CmsJautopostHistory entity = super.get(id);
		if (entity != null) {
			getSession().delete(entity);
		}
		return entity;
	}
	
	public void deleteByJautopost(Integer acquId){
		String hql = "delete from CmsJautopostHistory bean where bean.jautopost.id=:acquId";
		Query query = getSession().createQuery(hql).setParameter("acquId", acquId);
		query.executeUpdate();
	}

	public Boolean checkExistByProperties(Boolean title, String value) {
		Criteria crit = createCriteria();
		if (title) {
			crit.add(Restrictions.eq("title", value));
		} else {
			crit.add(Restrictions.eq("contentUrl", value));
		}
		return crit.list().size() > 0 ? true : false;
	}

	@Override
	protected Class<CmsJautopostHistory> getEntityClass() {
		return CmsJautopostHistory.class;
	}

}