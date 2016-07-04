package com.bra.modules.cms.web;

import com.bra.common.config.Global;
import com.bra.common.persistence.Page;
import com.bra.common.utils.StringUtils;
import com.bra.common.web.BaseController;
import com.bra.modules.cms.entity.PostMainCheck;
import com.bra.modules.cms.service.PostMainCheckService;
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
 * 帖子核查Controller
 * @author ddt
 * @version 2016-06-24
 */
@Controller
@RequestMapping(value = "${adminPath}/cms/postMainCheck")
public class PostMainCheckController extends BaseController {

	@Autowired
	private PostMainCheckService postMainCheckService;
	
	@ModelAttribute
	public PostMainCheck get(@RequestParam(required=false) String id) {
		PostMainCheck entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = postMainCheckService.get(id);
		}
		if (entity == null){
			entity = new PostMainCheck();
		}
		return entity;
	}
	
	@RequiresPermissions("cms:postMainCheck:view")
	@RequestMapping(value = {"list", ""})
	public String list(PostMainCheck postMainCheck, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<PostMainCheck> page = postMainCheckService.findPage(new Page<PostMainCheck>(request, response), postMainCheck); 
		model.addAttribute("page", page);
		return "modules/cms/postMainCheckList";
	}

	@RequiresPermissions("cms:postMainCheck:view")
	@RequestMapping(value = "form")
	public String form(PostMainCheck postMainCheck, Model model) {
		model.addAttribute("postMainCheck", postMainCheck);
		return "modules/cms/postMainCheckForm";
	}

	@RequiresPermissions("cms:postMainCheck:edit")
	@RequestMapping(value = "save")
	public String save(PostMainCheck postMainCheck, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, postMainCheck)){
			return form(postMainCheck, model);
		}
		postMainCheckService.save(postMainCheck);
		addMessage(redirectAttributes, "保存帖子核查成功");
		return "redirect:"+Global.getAdminPath()+"/cms/postMainCheck/?repage";
	}
	
	@RequiresPermissions("cms:postMainCheck:edit")
	@RequestMapping(value = "delete")
	public String delete(PostMainCheck postMainCheck, RedirectAttributes redirectAttributes) {
		postMainCheckService.delete(postMainCheck);
		addMessage(redirectAttributes, "删除帖子核查成功");
		return "redirect:"+Global.getAdminPath()+"/cms/postMainCheck/?repage";
	}

}