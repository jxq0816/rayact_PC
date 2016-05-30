package com.bra.modules.cms.web;

import com.bra.common.config.Global;
import com.bra.common.persistence.Page;
import com.bra.common.utils.StringUtils;
import com.bra.common.web.BaseController;
import com.bra.modules.cms.entity.CmsSubject;
import com.bra.modules.cms.service.CmsSubjectService;
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
 * 话题Controller
 * @author ddt
 * @version 2016-05-25
 */
@Controller
@RequestMapping(value = "${adminPath}/cms/cmsSubject")
public class CmsSubjectController extends BaseController {

	@Autowired
	private CmsSubjectService cmsSubjectService;
	
	@ModelAttribute
	public CmsSubject get(@RequestParam(required=false) String id) {
		CmsSubject entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = cmsSubjectService.get(id);
		}
		if (entity == null){
			entity = new CmsSubject();
		}
		return entity;
	}
	
	@RequiresPermissions("cms:cmsSubject:view")
	@RequestMapping(value = {"list", ""})
	public String list(CmsSubject cmsSubject, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CmsSubject> page = cmsSubjectService.findPage(new Page<CmsSubject>(request, response), cmsSubject); 
		model.addAttribute("page", page);
		return "modules/cms/cmsSubjectList";
	}

	@RequiresPermissions("cms:cmsSubject:view")
	@RequestMapping(value = "form")
	public String form(CmsSubject cmsSubject, Model model) {
		model.addAttribute("cmsSubject", cmsSubject);
		return "modules/cms/cmsSubjectForm";
	}

	@RequiresPermissions("cms:cmsSubject:edit")
	@RequestMapping(value = "save")
	public String save(CmsSubject cmsSubject, AttMainForm attMainForm, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, cmsSubject)){
			return form(cmsSubject, model);
		}
		cmsSubjectService.save(cmsSubject,attMainForm);
		addMessage(redirectAttributes, "保存话题成功");
		return "redirect:"+Global.getAdminPath()+"/cms/cmsSubject/?repage";
	}
	
	@RequiresPermissions("cms:cmsSubject:edit")
	@RequestMapping(value = "delete")
	public String delete(CmsSubject cmsSubject, RedirectAttributes redirectAttributes) {
		cmsSubjectService.delete(cmsSubject);
		addMessage(redirectAttributes, "删除话题成功");
		return "redirect:"+Global.getAdminPath()+"/cms/cmsSubject/?repage";
	}

}