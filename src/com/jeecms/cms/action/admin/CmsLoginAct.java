package com.jeecms.cms.action.admin;

import static org.apache.shiro.web.filter.authc.FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.jeecms.core.manager.ConfigMng;
import com.jeecms.core.manager.UnifiedUserMng;

@Controller
public class CmsLoginAct {

	@RequestMapping(value = "/login.do", method = RequestMethod.GET)
	public String input(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		Integer errorTimes=configMng.getConfigLogin().getErrorTimes();
		model.addAttribute("errorTimes", errorTimes);
		return "login";
	}

	@RequestMapping(value = "/login.do", method = RequestMethod.POST)
	public String submit(String username, String password, String captcha,
			String message, HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		Object error = request.getAttribute(DEFAULT_ERROR_KEY_ATTRIBUTE_NAME);
		if (error != null) {
			model.addAttribute("error", error);
			Integer errorRemaining= unifiedUserMng.errorRemaining(username);
			model.addAttribute("errorRemaining", errorRemaining);
		}
		return "login";
	}
	@Autowired
	private ConfigMng configMng;
	@Autowired
	private UnifiedUserMng unifiedUserMng;
}