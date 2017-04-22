package com.jeecmstheme.jautopost.dao;

import java.util.List;

import com.jeecms.common.hibernate3.Updater;
import com.jeecmstheme.jautopost.entity.CmsJautopostTemp;

public interface CmsJautopostTempDao {
	public List<CmsJautopostTemp> getList(Integer siteId);

	public CmsJautopostTemp findById(Integer id);

	public CmsJautopostTemp save(CmsJautopostTemp bean);

	public CmsJautopostTemp updateByUpdater(Updater<CmsJautopostTemp> updater);

	public CmsJautopostTemp deleteById(Integer id);
	
	public Integer getPercent(Integer siteId);
	
	public void clear(Integer siteId,String channelUrl);
}