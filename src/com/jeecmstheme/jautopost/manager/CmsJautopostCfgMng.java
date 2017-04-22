package com.jeecmstheme.jautopost.manager;

import java.util.List;

import com.jeecmstheme.jautopost.entity.CmsJautopostCfg;

public interface CmsJautopostCfgMng {
	/**
	 * @param siteId
	 * @return
	 */
	public List<CmsJautopostCfg> getList();

	/**
	 * @param id
	 * @return
	 */
	public CmsJautopostCfg findById(Integer id);

	/**
	 * @param bean
	 * @return
	 */
	public CmsJautopostCfg save(CmsJautopostCfg bean);

	/**
	 * @param id
	 * @return
	 */
	public CmsJautopostCfg deleteById(Integer id);

	/**
	 * @param bean
	 * @return
	 */
	public CmsJautopostCfg update(CmsJautopostCfg bean) ;

	/**
	 * @param ids
	 * @return
	 */
	CmsJautopostCfg[] deleteByIds(Integer[] ids);
}