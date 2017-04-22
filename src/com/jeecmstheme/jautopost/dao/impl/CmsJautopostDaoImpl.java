package com.jeecmstheme.jautopost.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.jeecms.common.hibernate3.Finder;
import com.jeecms.common.hibernate3.HibernateBaseDao;
import com.jeecmstheme.jautopost.dao.CmsJautopostDao;
import com.jeecmstheme.jautopost.entity.CmsJautopost;

@Repository
public class CmsJautopostDaoImpl extends
		HibernateBaseDao<CmsJautopost, Integer> implements CmsJautopostDao {
	@SuppressWarnings("unchecked")
	public List<CmsJautopost> getList(Integer siteId) {
		Finder f = Finder.create("from CmsJautopost bean");
		if (siteId != null) {
			f.append(" where bean.site.id=:siteId");
			f.setParam("siteId", siteId);
		}
		f.append(" order by bean.id asc");
		return find(f);
	}

	public CmsJautopost findById(Integer id) {
		CmsJautopost entity = get(id);
		return entity;
	}

	public CmsJautopost save(CmsJautopost bean) {
		getSession().save(bean);
		return bean;
	}

	public CmsJautopost deleteById(Integer id) {
		CmsJautopost entity = super.get(id);
		if (entity != null) {
			getSession().delete(entity);
		}
		return entity;
	}

	public int countByChannelId(Integer channelId) {
		String hql = "select count(*) from CmsJautopost bean"
				+ " where bean.channel.id=:channelId";
		Query query = getSession().createQuery(hql);
		query.setParameter("channelId", channelId);
		return ((Number) query.iterate().next()).intValue();
	}

	public CmsJautopost getStarted(Integer siteId) {
		Criteria crit = createCriteria(
				Restrictions.eq("status", CmsJautopost.START),
				Restrictions.eq("site.id", siteId)).setMaxResults(1);
		return (CmsJautopost) crit.uniqueResult();
	}

	public Integer getMaxQueue(Integer siteId) {
		Query query = createQuery("select max(bean.queue) from CmsJautopost bean where bean.site.id=?",siteId);
		return ((Number) query.iterate().next()).intValue();
	}

	@SuppressWarnings("unchecked")
	public List<CmsJautopost> getLargerQueues(Integer siteId, Integer queueNum) {
		Finder f = Finder.create("from CmsJautopost bean where bean.queue>:queueNum and bean.site.id=:siteId")
				.setParam("queueNum", queueNum)
				.setParam("siteId", siteId);
		return find(f);
	}

	public CmsJautopost popAcquFromQueue(Integer siteId) {
		Query query = getSession().createQuery("from CmsJautopost bean where bean.queue>0 and bean.site.id=:siteId order by bean.queue")
				.setParameter("siteId", siteId).setMaxResults(1);
		return (CmsJautopost) query.uniqueResult();
	}

	@Override
	protected Class<CmsJautopost> getEntityClass() {
		return CmsJautopost.class;
	}

}