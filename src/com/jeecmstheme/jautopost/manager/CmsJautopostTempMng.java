package com.jeecmstheme.jautopost.manager;

import java.util.List;

import com.jeecmstheme.jautopost.entity.CmsJautopostTemp;

public interface CmsJautopostTempMng {
	public List<CmsJautopostTemp> getList(Integer siteId);

	public CmsJautopostTemp findById(Integer id);

	public CmsJautopostTemp save(CmsJautopostTemp bean);

	public CmsJautopostTemp update(CmsJautopostTemp bean);

	public CmsJautopostTemp deleteById(Integer id);

	public CmsJautopostTemp[] deleteByIds(Integer[] ids);
	
	public Integer getPercent(Integer siteId);
	
	public void clear(Integer siteId);
	
	public void clear(Integer siteId, String channelUrl);
}