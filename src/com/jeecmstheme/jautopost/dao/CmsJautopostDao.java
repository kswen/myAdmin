package com.jeecmstheme.jautopost.dao;

import java.util.List;

import com.jeecms.common.hibernate3.Updater;
import com.jeecmstheme.jautopost.entity.CmsJautopost;

public interface CmsJautopostDao {
	public List<CmsJautopost> getList(Integer siteId);

	public CmsJautopost findById(Integer id);

	public CmsJautopost save(CmsJautopost bean);

	public CmsJautopost updateByUpdater(Updater<CmsJautopost> updater);

	public CmsJautopost deleteById(Integer id);

	public int countByChannelId(Integer channelId);

	public CmsJautopost getStarted(Integer siteId);

	public Integer getMaxQueue(Integer siteId);

	public List<CmsJautopost> getLargerQueues(Integer siteId, Integer queueNum);

	public CmsJautopost popAcquFromQueue(Integer siteId);
}