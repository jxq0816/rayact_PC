/**
 * Copyright &copy; 2012-2014 <a href="https://github.com.bra.>JeeSite</a> All rights reserved.
 */
package com.bra.modules.cms.web;

import com.bra.common.web.BaseController;
import com.bra.modules.cms.service.CategoryService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * 内容管理Controller
 *
 * @version 2013-4-21
 */
@Controller
@RequestMapping(value = "${adminPath}/cms")
public class CmsController extends BaseController {

	@Autowired
	private CategoryService categoryService;
	
	@RequiresPermissions("cms:view")
	@RequestMapping(value = "")
	public String index(HttpServletRequest request) {
		String module = request.getParameter("module");
		request.setAttribute("mmo",module);
		return "modules/cms/cmsIndex";
	}
	
	@RequiresPermissions("cms:view")
	@RequestMapping(value = "tree")
	public String tree(Model model) {
		model.addAttribute("categoryList", categoryService.findByUser(true, "article"));
		return "modules/cms/cmsTree";
	}
	
	@RequiresPermissions("cms:view")
	@RequestMapping(value = "none")
	public String none() {
		return "modules/cms/cmsNone";
	}

	@RequiresPermissions("cms:view")
	@RequestMapping(value = "tree/postMain")
	public String treePostMain(Model model) {
		model.addAttribute("categoryList", categoryService.findByUser(true, "group"));
		return "modules/cms/cmsTreePostMain";
	}

	@RequiresPermissions("cms:view")
	@RequestMapping(value = "tree/team")
	public String treeTeam(Model model) {
		model.addAttribute("categoryList", categoryService.findByUser(true, "group"));
		return "modules/cms/cmsTreeTeam";
	}

	@RequiresPermissions("cms:view")
	@RequestMapping(value = "tree/activity")
	public String treeActivity(Model model) {
		model.addAttribute("categoryList", categoryService.findByUser(true, "activity"));
		return "modules/cms/cmsTreeActivity";
	}

}
