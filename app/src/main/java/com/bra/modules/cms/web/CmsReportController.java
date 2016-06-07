package com.bra.modules.cms.web;

import com.alibaba.fastjson.JSONObject;
import com.bra.common.config.Global;
import com.bra.common.persistence.Page;
import com.bra.common.utils.StringUtils;
import com.bra.common.web.BaseController;
import com.bra.modules.cms.entity.CmsReport;
import com.bra.modules.cms.service.CmsReportService;
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
import java.io.IOException;

/**
 * 举报Controller
 * @author ddt
 * @version 2016-06-04
 */
@Controller
@RequestMapping(value = "${adminPath}/cms/cmsReport")
public class CmsReportController extends BaseController {

	@Autowired
	private CmsReportService cmsReportService;
	
	@ModelAttribute
	public CmsReport get(@RequestParam(required=false) String id) {
		CmsReport entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = cmsReportService.get(id);
		}
		if (entity == null){
			entity = new CmsReport();
		}
		return entity;
	}
	
	@RequiresPermissions("cms:cmsReport:view")
	@RequestMapping(value = {"list", ""})
	public String list(CmsReport cmsReport, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CmsReport> page = cmsReportService.findPage(new Page<CmsReport>(request, response), cmsReport); 
		model.addAttribute("page", page);
		return "modules/cms/cmsReportList";
	}

	@RequiresPermissions("cms:cmsReport:view")
	@RequestMapping(value = "form")
	public String form(CmsReport cmsReport, Model model) {
		model.addAttribute("cmsReport", cmsReport);
		return "modules/cms/cmsReportForm";
	}

	@RequiresPermissions("cms:cmsReport:edit")
	@RequestMapping(value = "save")
	public String save(CmsReport cmsReport, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, cmsReport)){
			return form(cmsReport, model);
		}
		cmsReportService.save(cmsReport);
		addMessage(redirectAttributes, "保存举报成功");
		return "redirect:"+Global.getAdminPath()+"/cms/cmsReport/?repage";
	}
	
	@RequiresPermissions("cms:cmsReport:edit")
	@RequestMapping(value = "delete")
	public String delete(CmsReport cmsReport, RedirectAttributes redirectAttributes) {
		cmsReportService.delete(cmsReport);
		addMessage(redirectAttributes, "删除举报成功");
		return "redirect:"+Global.getAdminPath()+"/cms/cmsReport/?repage";
	}

	@RequestMapping(value = "app/save")
	public void saveApp(CmsReport cmsReport, HttpServletResponse response) {
		cmsReportService.save(cmsReport);
		JSONObject j = new JSONObject();
		j.put("status","success");
		j.put("msg","举报成功");
		try {
			response.reset();
			response.setContentType("application/json");
			response.setCharacterEncoding("utf-8");
			response.getWriter().print(j.toJSONString());
		} catch (IOException e) {
		}
	}

}