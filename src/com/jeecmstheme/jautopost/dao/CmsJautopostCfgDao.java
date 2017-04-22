package com.jeecmstheme.jautopost.dao;

import java.util.List;

import com.jeecms.common.hibernate3.Updater;
import com.jeecmstheme.jautopost.entity.CmsJautopostCfg;

public interface CmsJautopostCfgDao {
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
	 * @param updater
	 * @return
	 */
	public CmsJautopostCfg updateByUpdater(Updater<CmsJautopostCfg> updater);

}