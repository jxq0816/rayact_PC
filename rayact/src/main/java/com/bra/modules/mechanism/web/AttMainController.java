package com.bra.modules.mechanism.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bra.common.config.Global;
import com.bra.common.persistence.Page;
import com.bra.common.web.BaseController;
import com.bra.common.utils.StringUtils;
import com.bra.modules.mechanism.entity.AttMain;
import com.bra.modules.mechanism.service.AttMainService;

/**
 * 文档管理Controller
 * @author 肖斌
 * @version 2015-12-30
 */
@Controller
@RequestMapping(value = "${adminPath}/mechanism/attMain")
public class AttMainController extends BaseController {

	@Autowired
	private AttMainService attMainService;
	
	@ModelAttribute
	public AttMain get(@RequestParam(required=false) String id) {
		AttMain entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = attMainService.get(id);
		}
		if (entity == null){
			entity = new AttMain();
		}
		return entity;
	}
	
	@RequiresPermissions("mechanism:attMain:view")
	@RequestMapping(value = {"list", ""})
	public String list(AttMain attMain, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AttMain> page = attMainService.findPage(new Page<AttMain>(request, response), attMain); 
		model.addAttribute("page", page);
		return "modules/mechanism/attMainList";
	}

	@RequiresPermissions("mechanism:attMain:view")
	@RequestMapping(value = "form")
	public String form(AttMain attMain, Model model) {
		model.addAttribute("attMain", attMain);
		return "modules/mechanism/attMainForm";
	}

	@RequiresPermissions("mechanism:attMain:edit")
	@RequestMapping(value = "save")
	public String save(AttMain attMain, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, attMain)){
			return form(attMain, model);
		}
		attMainService.save(attMain);
		addMessage(redirectAttributes, "保存文档管理成功");
		return "redirect:"+Global.getAdminPath()+"/mechanism/attMain/?repage";
	}
	
	@RequiresPermissions("mechanism:attMain:edit")
	@RequestMapping(value = "delete")
	public String delete(AttMain attMain, RedirectAttributes redirectAttributes) {
		attMainService.delete(attMain);
		addMessage(redirectAttributes, "删除文档管理成功");
		return "redirect:"+Global.getAdminPath()+"/mechanism/attMain/?repage";
	}

}