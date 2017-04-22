package com.jeecmstheme.jautopost.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jeecms.core.manager.CmsLogMng;
import com.jeecms.core.web.WebErrors;
import com.jeecmstheme.jautopost.entity.CmsJautopostCfg;
import com.jeecmstheme.jautopost.manager.CmsJautopostCfgMng;

@Controller
public class CmsJautopostCfgAct {
	private static final Logger log = LoggerFactory
			.getLogger(CmsJautopostCfgAct.class);

	@RequiresPermissions("jautopost_config:v_list")
	@RequestMapping("/jautopost_config/v_list.do")
	public String list(HttpServletRequest request,
			ModelMap model) {
		List<CmsJautopostCfg> list = manager.getList();
		model.addAttribute("list", list);
		return "jautopost_config/list";
	}

	@RequiresPermissions("jautopost_config:v_add")
	@RequestMapping("/jautopost_config/v_add.do")
	public String add(ModelMap model) {
		return "jautopost_config/add";
	}

	@RequiresPermissions("jautopost_config:v_edit")
	@RequestMapping("/jautopost_config/v_edit.do")
	public String edit(Integer id, HttpServletRequest request,
			ModelMap model) {
		model.addAttribute("cmsJautopostCfg", manager.findById(id));
		return "jautopost_config/edit";
	}

	@RequiresPermissions("jautopost_config:o_save")
	@RequestMapping("/jautopost_config/o_save.do")
	public String save(CmsJautopostCfg bean, HttpServletRequest request,
			ModelMap model) {
		bean = manager.save(bean);
		log.info("save CmsJautopostCfg id={}", bean.getId());
		cmsLogMng.operating(request, "CmsJautopostCfg.log.save", "id="
				+ bean.getId() + ";name=" + bean.getName());
		return "redirect:v_list.do";
	}

	@RequiresPermissions("jautopost_config:o_update")
	@RequestMapping("/jautopost_config/o_update.do")
	public String update(CmsJautopostCfg bean,
			HttpServletRequest request, ModelMap model) {
		bean = manager.update(bean);
		log.info("update CmsJautopostCfg id={}.", bean.getId());
		cmsLogMng.operating(request, "CmsJautopostCfg.log.update", "id="
				+ bean.getId() + ";name=" + bean.getName());
		return list(request, model);
	}

	@RequiresPermissions("jautopost_config:o_delete")
	@RequestMapping("/jautopost_config/o_delete.do")
	public String delete(Integer[] ids,
			HttpServletRequest request, ModelMap model) {
		WebErrors errors = validateDelete(ids, request);
		if (errors.hasErrors()) {
			return errors.showErrorPage(model);
		}
		CmsJautopostCfg[] beans = manager.deleteByIds(ids);
		for (CmsJautopostCfg bean : beans) {
			log.info("delete CmsJautopostCfg id={}", bean.getId());
			cmsLogMng.operating(request, "CmsJautopostCfg.log.delete",
					"id=" + bean.getId() + ";name=" + bean.getName());
		}
		return list(request, model);
	}

	private WebErrors validateDelete(Integer[] ids, HttpServletRequest request) {
		WebErrors errors = WebErrors.create(request);
		if (errors.ifEmpty(ids, "ids")) {
			return errors;
		}
		return errors;
	}

	@Autowired
	private CmsLogMng cmsLogMng;
	@Autowired
	private CmsJautopostCfgMng manager;
}