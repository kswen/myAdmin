package com.jeecmstheme.jautopost.manager;

import java.util.Date;
import java.util.List;

import com.jeecms.cms.entity.main.Content;
import com.jeecmstheme.jautopost.entity.CmsJautopost;
import com.jeecmstheme.jautopost.entity.CmsJautopostHistory;
import com.jeecmstheme.jautopost.entity.CmsJautopostTemp;
import com.jeecmstheme.jautopost.entity.CmsJautopost.JautopostResultType;

public interface CmsJautopostMng {
	public List<CmsJautopost> getList(Integer siteId);

	public CmsJautopost findById(Integer id);

	public void stop(Integer id);

	public void pause(Integer id);

	public CmsJautopost start(Integer id);

	public void end(Integer id);

	public boolean isNeedBreak(Integer id, int currNum, int currItem,
			int totalItem);

	public CmsJautopost save(CmsJautopost bean, Integer channelId,
			Integer typeId, Integer jautopostCfgId,Integer userId, Integer siteId);

	public CmsJautopost update(CmsJautopost bean, Integer channelId,
			Integer typeId,Integer jautopostCfgId);

	public CmsJautopost deleteById(Integer id);

	public CmsJautopost[] deleteByIds(Integer[] ids);

	public Content saveContent(String title,String titleImg,  String txt, String origin,
			String author,String description,Date releaseDate,Integer acquId,
			JautopostResultType resultType, CmsJautopostTemp temp,
			CmsJautopostHistory history);

	public CmsJautopost getStarted(Integer siteId);
	
	public Integer getMaxQueue(Integer siteId);

	public Integer hasStarted(Integer siteId);
	
	public void addToQueue(Integer[] ids, Integer queueNum);
	
	public void cancel(Integer siteId, Integer id);
	
	public List<CmsJautopost> getLargerQueues(Integer siteId, Integer queueNum);
	
	public CmsJautopost popAcquFromQueue(Integer siteId);
}