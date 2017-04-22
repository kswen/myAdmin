package com.jeecmstheme.jautopost.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.jeecms.common.hibernate3.Finder;
import com.jeecms.common.hibernate3.HibernateBaseDao;
import com.jeecmstheme.jautopost.dao.CmsJautopostTempDao;
import com.jeecmstheme.jautopost.entity.CmsJautopostTemp;

@Repository
public class CmsJautopostTempDaoImpl extends
		HibernateBaseDao<CmsJautopostTemp, Integer> implements
		CmsJautopostTempDao {
	@SuppressWarnings("unchecked")
	public List<CmsJautopostTemp> getList(Integer siteId) {
		Finder f = Finder.create("from CmsJautopostTemp bean where 1=1");
		if (siteId != null) {
			f.append(" and bean.site.id=:siteId");
			f.setParam("siteId", siteId);
		}
		f.append(" order by bean.id desc");
		return find(f);
	}

	public CmsJautopostTemp findById(Integer id) {
		CmsJautopostTemp entity = get(id);
		return entity;
	}

	public CmsJautopostTemp save(CmsJautopostTemp bean) {
		getSession().save(bean);
		return bean;
	}

	public CmsJautopostTemp deleteById(Integer id) {
		CmsJautopostTemp entity = super.get(id);
		if (entity != null) {
			getSession().delete(entity);
		}
		return entity;
	}

	public Integer getPercent(Integer siteId) {
		Query query = getSession()
				.createQuery(
						"select max(percent) from CmsJautopostTemp where site.id=:siteId")
				.setParameter("siteId", siteId);
		return (Integer) (query.uniqueResult() == null ? 0 : query
				.uniqueResult());
	}

	public void clear(Integer siteId, String channelUrl) {
		StringBuilder sb = new StringBuilder(100);
		Map<String, Object> params = new HashMap<String, Object>();
		sb.append("delete from CmsJautopostTemp where site.id=:siteId");
		params.put("siteId", siteId);
		if (StringUtils.isNotBlank(channelUrl)) {
			sb.append(" and channelUrl!=:channelUrl");
			params.put("channelUrl", channelUrl);
		}
		Query query = getSession().createQuery(sb.toString());
		for (Entry<String, Object> entry : params.entrySet()) {
			query.setParameter(entry.getKey(), entry.getValue());
		}
		query.executeUpdate();
	}

	@Override
	protected Class<CmsJautopostTemp> getEntityClass() {
		return CmsJautopostTemp.class;
	}

}