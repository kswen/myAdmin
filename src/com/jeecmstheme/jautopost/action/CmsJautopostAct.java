package com.jeecmstheme.jautopost.action;

import static com.jeecms.common.page.SimplePage.cpn;

import java.net.URL;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jeecms.cms.entity.main.Channel;
import com.jeecms.cms.entity.main.ContentType;
import com.jeecms.cms.manager.main.ChannelMng;
import com.jeecms.cms.manager.main.ContentTypeMng;
import com.jeecms.common.page.Pagination;
import com.jeecms.common.web.CookieUtils;
import com.jeecms.common.web.ResponseUtils;
import com.jeecms.core.entity.CmsSite;
import com.jeecms.core.manager.CmsLogMng;
import com.jeecms.core.web.WebErrors;
import com.jeecms.core.web.util.CmsUtils;
import com.jeecmstheme.jautopost.Jautopost;
import com.jeecmstheme.jautopost.entity.CmsJautopost;
import com.jeecmstheme.jautopost.entity.CmsJautopostCfg;
import com.jeecmstheme.jautopost.entity.CmsJautopostHistory;
import com.jeecmstheme.jautopost.entity.CmsJautopostTemp;
import com.jeecmstheme.jautopost.helper.StringUtil;
import com.jeecmstheme.jautopost.manager.CmsJautopostCfgMng;
import com.jeecmstheme.jautopost.manager.CmsJautopostHistoryMng;
import com.jeecmstheme.jautopost.manager.CmsJautopostMng;
import com.jeecmstheme.jautopost.manager.CmsJautopostTempMng;
import com.jeecmstheme.jautopost.nodes.Document;
import com.jeecmstheme.jautopost.service.JautopostSvc;

@Controller
public class CmsJautopostAct {
	private static final Logger log = LoggerFactory
			.getLogger(CmsJautopostAct.class);
	
	@RequiresPermissions("jautopost:v_list")
	@RequestMapping("/jautopost/v_list.do")
	public String list(HttpServletRequest request, ModelMap model) {
		CmsSite site = CmsUtils.getSite(request);
		List<CmsJautopost> list = manager.getList(site.getId());
		model.addAttribute("list", list);
		return "jautopost/list";
	}
	
	@RequiresPermissions("jautopost:v_test")
	@RequestMapping("/jautopost/v_test.do")
	public String test(HttpServletRequest request, ModelMap model) {
		return "jautopost/test";
	}
	
	@RequiresPermissions("jautopost:o_test")
	@RequestMapping("/jautopost/o_test.do")
	public String test(String url,String pageEncoding, String autoCharset, HttpServletRequest request, ModelMap model) {
		String result = "No Result";
		try {
			Document doc = null;
			if(!StringUtil.isBlank(autoCharset)){
				doc = Jautopost.connect(url).userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/39.0.2171.95 Safari/537.36").timeout(1000*60).get();
			}else{
				doc = Jautopost.parse(new URL(url).openStream(), pageEncoding, url);
			}
			result = doc.html();
		} catch (Exception e) {
			 result = "URL test exception: "+e.getLocalizedMessage();
		}
		model.addAttribute("result", result);
		return "jautopost/test";
	}

	@RequiresPermissions("jautopost:v_add")
	@RequestMapping("/jautopost/v_add.do")
	public String add(HttpServletRequest request, ModelMap model) {
		CmsSite site = CmsUtils.getSite(request);
		// 内容类型
		List<ContentType> typeList = contentTypeMng.getList(false);
		// 栏目列表
		List<Channel> topList = channelMng.getTopList(site.getId(), true);
		List<Channel> channelList = Channel.getListForSelect(topList, null, true);
		List<CmsJautopostCfg> jautopostCfgList = cmsJautopostCfgMng.getList();
		model.addAttribute("channelList", channelList);
		model.addAttribute("typeList", typeList);
		model.addAttribute("jautopostCfgList", jautopostCfgList);
		return "jautopost/add";
	}

	@RequiresPermissions("jautopost:v_edit")
	@RequestMapping("/jautopost/v_edit.do")
	public String edit(Integer id, HttpServletRequest request, ModelMap model) {
		WebErrors errors = validateEdit(id, request);
		if (errors.hasErrors()) {
			return errors.showErrorPage(model);
		}
		CmsSite site = CmsUtils.getSite(request);
		// 内容类型
		List<ContentType> typeList = contentTypeMng.getList(false);
		// 栏目列表
		List<Channel> topList = channelMng.getTopList(site.getId(), true);
		List<Channel> channelList = Channel.getListForSelect(topList, null, true);
		List<CmsJautopostCfg> jautopostCfgList = cmsJautopostCfgMng.getList();
		model.addAttribute("channelList", channelList);
		model.addAttribute("typeList", typeList);
		model.addAttribute("cmsJautopost", manager.findById(id));
		model.addAttribute("jautopostCfgList", jautopostCfgList);
		return "jautopost/edit";
	}

	@RequiresPermissions("jautopost:o_save")
	@RequestMapping("/jautopost/o_save.do")
	public String save(CmsJautopost bean, Integer channelId, Integer typeId, Integer jautopostCfgId,
			HttpServletRequest request, ModelMap model) {
		WebErrors errors = validateSave(bean, request);
		if (errors.hasErrors()) {
			return errors.showErrorPage(model);
		}
		Integer siteId = CmsUtils.getSiteId(request);
		Integer userId = CmsUtils.getUserId(request);
		bean = manager.save(bean, channelId, typeId, jautopostCfgId, userId, siteId);
		log.info("save CmsJautopost id={}", bean.getId());
		cmsLogMng.operating(request, "cmsJautopost.log.save", "id="
				+ bean.getId() + ";name=" + bean.getName());
		return "redirect:v_list.do";
	}

	@RequiresPermissions("jautopost:o_update")
	@RequestMapping("/jautopost/o_update.do")
	public String update(CmsJautopost bean, Integer channelId,
			Integer typeId, Integer jautopostCfgId, HttpServletRequest request, ModelMap model) {
		WebErrors errors = validateUpdate(bean.getId(), request);
		if (errors.hasErrors()) {
			return errors.showErrorPage(model);
		}
		bean = manager.update(bean, channelId, typeId, jautopostCfgId);
		log.info("update CmsJautopost id={}.", bean.getId());
		cmsLogMng.operating(request, "cmsJautopost.log.update", "id="
				+ bean.getId() + ";name=" + bean.getName());
		return list(request, model);
	}

	@RequiresPermissions("jautopost:o_delete")
	@RequestMapping("/jautopost/o_delete.do")
	public String delete(Integer[] ids, HttpServletRequest request,
			ModelMap model) {
		WebErrors errors = validateDelete(ids, request);
		if (errors.hasErrors()) {
			return errors.showErrorPage(model);
		}
		CmsJautopost[] beans = manager.deleteByIds(ids);
		for (CmsJautopost bean : beans) {
			log.info("delete CmsJautopost id={}", bean.getId());
			cmsLogMng.operating(request, "cmsJautopost.log.delete", "id="
					+ bean.getId() + ";name=" + bean.getName());
		}
		return list(request, model);
	}

	@RequiresPermissions("jautopost:o_start")
	@RequestMapping("/jautopost/o_start.do")
	public String start(Integer[] ids, HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		Integer siteId = CmsUtils.getSiteId(request);
		WebErrors errors = validateStart(ids, request);
		if (errors.hasErrors()) {
			return errors.showErrorPage(model);
		}
		Integer queueNum = manager.hasStarted(siteId);
		if(queueNum==0){
			jautopostSvc.start(ids[0]);
		}
		manager.addToQueue(ids, queueNum);
		log.info("start CmsJautopost ids={}", Arrays.toString(ids));
		return "jautopost/progress";
	}

	@RequiresPermissions("jautopost:o_end")
	@RequestMapping("/jautopost/o_end.do")
	public String end(Integer id, HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		WebErrors errors = WebErrors.create(request);
		Integer siteId = CmsUtils.getSiteId(request);
		if (vldExist(id, siteId, errors)) {
			return errors.showErrorPage(model);
		}
		manager.end(id);
		CmsJautopost acqu = manager.popAcquFromQueue(siteId);
		if (acqu != null) {
			Integer acquId = acqu.getId();
			jautopostSvc.start(acquId);
		}
		jautopostSvc.end();
		log.info("end CmsJautopost id={}", id);
		return "redirect:v_list.do";
	}

	@RequiresPermissions("jautopost:o_pause")
	@RequestMapping("/jautopost/o_pause.do")
	public String pause(Integer id, HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		WebErrors errors = WebErrors.create(request);
		Integer siteId = CmsUtils.getSiteId(request);
		if (vldExist(id, siteId, errors)) {
			return errors.showErrorPage(model);
		}
		jautopostSvc.pause();
		manager.pause(id);
		log.info("pause CmsJautopost id={}", id);
		return "redirect:v_list.do";
	}

	@RequiresPermissions("jautopost:o_cancel")
	@RequestMapping("/jautopost/o_cancel.do")
	public String cancel(Integer id, Integer sortId, Integer pageNo,
			HttpServletRequest request, HttpServletResponse response,
			ModelMap model) {
		WebErrors errors = WebErrors.create(request);
		Integer siteId = CmsUtils.getSiteId(request);
		if (vldExist(id, siteId, errors)) {
			return errors.showErrorPage(model);
		}
		manager.cancel(siteId, id);
		log.info("cancel CmsJautopost id={}", id);
		return "redirect:v_list.do";
	}

	@RequiresPermissions("jautopost:v_check_complete")
	@RequestMapping("/jautopost/v_check_complete.do")
	public void checkComplete(Integer id, HttpServletRequest request,
			HttpServletResponse response, ModelMap model) throws JSONException {
		JSONObject json = new JSONObject();
		CmsSite site = CmsUtils.getSite(request);
		Integer siteId = site.getId();
		CmsJautopost acqu = manager.getStarted(siteId);
		json.put("completed", acqu == null ? true : false);
		ResponseUtils.renderJson(response, json.toString());
	}

	@RequiresPermissions("jautopost:v_progress_data")
	@RequestMapping("/jautopost/v_progress_data.do")
	public String progressData(Integer id, HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		CmsSite site = CmsUtils.getSite(request);
		Integer siteId = site.getId();
		CmsJautopost acqu = manager.getStarted(siteId);
		List<CmsJautopostTemp> list = cmsJautopostTempMng.getList(siteId);
		model.put("percent", cmsJautopostTempMng.getPercent(siteId));
		model.put("acqu", acqu);
		model.put("list", list);
		return "jautopost/progress_data";
	}

	@RequiresPermissions("jautopost:v_progress")
	@RequestMapping("/jautopost/v_progress.do")
	public String progress(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		CmsSite site = CmsUtils.getSite(request);
		Integer siteId = site.getId();
		CmsJautopost acqu = manager.getStarted(siteId);
		if (acqu == null) {
			cmsJautopostTempMng.clear(siteId);
		}
		return "jautopost/progress";
	}

	@RequiresPermissions("jautopost:v_history")
	@RequestMapping("/jautopost/v_history.do")
	public String history(Integer acquId, Integer pageNo,
			HttpServletRequest request, HttpServletResponse response,
			ModelMap model) {
		CmsSite site = CmsUtils.getSite(request);
		Integer siteId = site.getId();
		Pagination pagination = cmsJautopostHistoryMng.getPage(siteId,
				acquId, cpn(pageNo), CookieUtils.getPageSize(request));
		model.addAttribute("pagination", pagination);
		model.addAttribute("pageNo", pagination.getPageNo());
		return "jautopost/history";
	}

	@RequiresPermissions("jautopost:o_delete_history")
	@RequestMapping("/jautopost/o_delete_history.do")
	public String deleteHistory(Integer[] ids, Integer pageNo,
			HttpServletRequest request, HttpServletResponse response,
			ModelMap model) {
		WebErrors errors = validateHistoryDelete(ids, request);
		if (errors.hasErrors()) {
			return errors.showErrorPage(model);
		}
		CmsJautopostHistory[] beans = cmsJautopostHistoryMng
				.deleteByIds(ids);
		for (CmsJautopostHistory bean : beans) {
			log.info("delete CmsJautopostHistory id={}", bean.getId());
			cmsLogMng.operating(request, "cmsJautopostHistory.log.delete",
					"id=" + bean.getId() + ";name=" + bean.getTitle());
		}
		return history(null, pageNo, request, response, model);
	}

	private WebErrors validateSave(CmsJautopost bean,
			HttpServletRequest request) {
		WebErrors errors = WebErrors.create(request);
		CmsSite site = CmsUtils.getSite(request);
		bean.setSite(site);
		return errors;
	}

	private WebErrors validateEdit(Integer id, HttpServletRequest request) {
		WebErrors errors = WebErrors.create(request);
		CmsSite site = CmsUtils.getSite(request);
		if (vldExist(id, site.getId(), errors)) {
			return errors;
		}
		return errors;
	}

	private WebErrors validateUpdate(Integer id, HttpServletRequest request) {
		WebErrors errors = WebErrors.create(request);
		CmsSite site = CmsUtils.getSite(request);
		if (vldExist(id, site.getId(), errors)) {
			return errors;
		}
		return errors;
	}

	private WebErrors validateDelete(Integer[] ids, HttpServletRequest request) {
		WebErrors errors = WebErrors.create(request);
		CmsSite site = CmsUtils.getSite(request);
		if (errors.ifEmpty(ids, "ids")) {
			return errors;
		}
		for (Integer id : ids) {
			vldExist(id, site.getId(), errors);
		}
		return errors;
	}
	
	private WebErrors validateStart(Integer[] ids, HttpServletRequest request) {
		WebErrors errors = WebErrors.create(request);
		CmsSite site = CmsUtils.getSite(request);
		if (errors.ifEmpty(ids, "ids")) {
			return errors;
		}
		for (Integer id : ids) {
			vldExist(id, site.getId(), errors);
		}
		return errors;
	}
	
	

	private boolean vldExist(Integer id, Integer siteId, WebErrors errors) {
		if (errors.ifNull(id, "id")) {
			return true;
		}
		CmsJautopost entity = manager.findById(id);
		if (errors.ifNotExist(entity, CmsJautopost.class, id)) {
			return true;
		}
		if (!entity.getSite().getId().equals(siteId)) {
			errors.notInSite(CmsJautopost.class, id);
			return true;
		}
		return false;
	}

	private WebErrors validateHistoryDelete(Integer[] ids,
			HttpServletRequest request) {
		WebErrors errors = WebErrors.create(request);
		CmsSite site = CmsUtils.getSite(request);
		if (errors.ifEmpty(ids, "ids")) {
			return errors;
		}
		for (Integer id : ids) {
			vldHistoryExist(id, site.getId(), errors);
		}
		return errors;
	}

	private boolean vldHistoryExist(Integer id, Integer siteId, WebErrors errors) {
		if (errors.ifNull(id, "id")) {
			return true;
		}
		CmsJautopostHistory entity = cmsJautopostHistoryMng.findById(id);
		if (errors.ifNotExist(entity, CmsJautopostHistory.class, id)) {
			return true;
		}
		if (!entity.getJautopost().getSite().getId().equals(siteId)) {
			errors.notInSite(CmsJautopostHistory.class, id);
			return true;
		}
		return false;
	}

	@Autowired
	private ContentTypeMng contentTypeMng;
	@Autowired
	private ChannelMng channelMng;
	@Autowired
	private JautopostSvc jautopostSvc;
	@Autowired
	private CmsLogMng cmsLogMng;
	@Autowired
	private CmsJautopostCfgMng cmsJautopostCfgMng;
	@Autowired
	private CmsJautopostMng manager;
	@Autowired
	private CmsJautopostHistoryMng cmsJautopostHistoryMng;
	@Autowired
	private CmsJautopostTempMng cmsJautopostTempMng;
}