package com.bra.modules.cms.web;

import com.bra.common.config.Global;
import com.bra.common.persistence.Page;
import com.bra.common.utils.StringUtils;
import com.bra.common.web.BaseController;
import com.bra.modules.cms.entity.Activity;
import com.bra.modules.cms.service.ActivityService;
import com.bra.modules.mechanism.web.bean.AttMainForm;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 活动Controller
 * @author ddt
 * @version 2016-05-18
 */
@Controller
@RequestMapping(value = "${adminPath}/cms/activity")
public class ActivityController extends BaseController {

	@Autowired
	private ActivityService activityService;
	
	@ModelAttribute
	public Activity get(@RequestParam(required=false) String id) {
		Activity entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = activityService.get(id);
		}
		if (entity == null){
			entity = new Activity();
		}
		return entity;
	}
	
	@RequiresPermissions("cms:activity:view")
	@RequestMapping(value = {"list", ""})
	public String list(Activity activity, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Activity> page = activityService.findPage(new Page<Activity>(request, response), activity); 
		model.addAttribute("page", page);
		return "modules/cms/activityList";
	}

	@RequiresPermissions("cms:activity:view")
	@RequestMapping(value = "form")
	public String form(Activity activity, Model model) {
		model.addAttribute("activity", activity);
		return "modules/cms/activityForm";
	}

	@RequiresPermissions("cms:activity:edit")
	@RequestMapping(value = "save")
	public String save(Activity activity, AttMainForm attMainForm, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, activity)){
			return form(activity, model);
		}
		activityService.save(activity, attMainForm);
		addMessage(redirectAttributes, "保存活动成功");
		return "redirect:"+Global.getAdminPath()+"/cms/activity/?repage";
	}
	
	@RequiresPermissions("cms:activity:edit")
	@RequestMapping(value = "delete")
	public String delete(Activity activity, RedirectAttributes redirectAttributes) {
		activityService.delete(activity);
		addMessage(redirectAttributes, "删除活动成功");
		return "redirect:"+Global.getAdminPath()+"/cms/activity/?repage";
	}

}