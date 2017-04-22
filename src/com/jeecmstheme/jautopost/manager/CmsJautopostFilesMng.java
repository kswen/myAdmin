package com.jeecmstheme.jautopost.manager;

import java.util.List;

import com.jeecmstheme.jautopost.entity.CmsJautopostFiles;

public interface CmsJautopostFilesMng {
	/**
	 * @param siteId
	 * @return
	 */
	public List<CmsJautopostFiles> getList();

	/**
	 * @param md5Str
	 * @return
	 */
	public CmsJautopostFiles findByMD5Str(String md5Str);
	/**
	 * @param id
	 * @return
	 */
	public CmsJautopostFiles findById(Integer id);

	/**
	 * @param bean
	 * @return
	 */
	public CmsJautopostFiles save(CmsJautopostFiles bean);

	/**
	 * @param id
	 * @return
	 */
	public CmsJautopostFiles deleteById(Integer id);

	/**
	 * @param bean
	 * @return
	 */
	public CmsJautopostFiles update(CmsJautopostFiles bean) ;
}