package com.jeecmstheme.jautopost.manager;

import java.util.List;

import com.jeecms.common.page.Pagination;
import com.jeecmstheme.jautopost.entity.CmsJautopostHistory;

public interface CmsJautopostHistoryMng {
	public List<CmsJautopostHistory> getList(Integer siteId, Integer acquId);

	public Pagination getPage(Integer siteId, Integer acquId, Integer pageNo,
			Integer pageSize);

	public CmsJautopostHistory findById(Integer id);

	public CmsJautopostHistory save(CmsJautopostHistory bean);

	public CmsJautopostHistory update(CmsJautopostHistory bean);

	public CmsJautopostHistory deleteById(Integer id);

	public CmsJautopostHistory[] deleteByIds(Integer[] ids);
	
	public void deleteByJautopost(Integer acquId);
	
	public Boolean checkExistByProperties(Boolean title, String value);
}