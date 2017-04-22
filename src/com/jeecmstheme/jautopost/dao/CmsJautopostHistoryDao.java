package com.jeecmstheme.jautopost.dao;

import java.util.List;

import com.jeecms.common.hibernate3.Updater;
import com.jeecms.common.page.Pagination;
import com.jeecmstheme.jautopost.entity.CmsJautopostHistory;

public interface CmsJautopostHistoryDao {
	public List<CmsJautopostHistory> getList(Integer siteId,Integer acquId);

	public Pagination getPage(Integer siteId,Integer acquId, Integer pageNo, Integer pageSize);

	public CmsJautopostHistory findById(Integer id);

	public CmsJautopostHistory save(CmsJautopostHistory bean);

	public CmsJautopostHistory updateByUpdater(Updater<CmsJautopostHistory> updater);

	public CmsJautopostHistory deleteById(Integer id);
	
	public void deleteByJautopost(Integer acquId);

	public Boolean checkExistByProperties(Boolean title, String value);
}