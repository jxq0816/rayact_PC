package com.bra.modules.reserve.web;

import com.bra.common.config.Global;
import com.bra.common.persistence.Page;
import com.bra.common.utils.StringUtils;
import com.bra.common.web.BaseController;
import com.bra.common.web.annotation.Token;
import com.bra.modules.reserve.entity.ReserveProject;
import com.bra.modules.reserve.service.ReserveProjectService;
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
 * 项目管理Controller
 * @author xiaobin
 * @version 2015-12-29
 */
@Controller
@RequestMapping(value = "${adminPath}/reserve/reserveProject")
public class ReserveProjectController extends BaseController {

	@Autowired
	private ReserveProjectService reserveProjectService;
	
	@ModelAttribute
	public ReserveProject get(@RequestParam(required=false) String id) {
		ReserveProject entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = reserveProjectService.get(id);
		}
		if (entity == null){
			entity = new ReserveProject();
		}
		return entity;
	}
	
	@RequestMapping(value = {"list", ""})
	public String list(ReserveProject reserveProject, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ReserveProject> page = reserveProjectService.findPage(new Page<ReserveProject>(request, response), reserveProject); 
		model.addAttribute("page", page);
		return "reserve/project/reserveProjectList";
	}

	@RequestMapping(value = "form")
	@Token(save = true)
	public String form(ReserveProject reserveProject, Model model) {
		model.addAttribute("reserveProject", reserveProject);
		return "reserve/project/reserveProjectForm";
	}

	@RequestMapping(value = "save")
	@Token(remove = true)
	public String save(ReserveProject reserveProject, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, reserveProject)){
			return form(reserveProject, model);
		}
		reserveProjectService.save(reserveProject);
		addMessage(redirectAttributes, "保存项目成功");
		return "redirect:"+Global.getAdminPath()+"/reserve/reserveProject/list";
	}
	
	@RequestMapping(value = "delete")
	public String delete(ReserveProject reserveProject, RedirectAttributes redirectAttributes) {
		reserveProjectService.delete(reserveProject);
		addMessage(redirectAttributes, "删除项目成功");
		return "redirect:"+Global.getAdminPath()+"/reserve/reserveProject/list";
	}

}