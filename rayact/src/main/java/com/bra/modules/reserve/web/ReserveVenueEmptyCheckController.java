package com.bra.modules.reserve.web;

import com.bra.common.config.Global;
import com.bra.common.persistence.Page;
import com.bra.common.utils.StringUtils;
import com.bra.common.web.BaseController;
import com.bra.modules.reserve.entity.ReserveVenueEmptyCheck;
import com.bra.modules.reserve.service.ReserveVenueEmptyCheckService;
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
 * 空场审核Controller
 * @author xudl
 * @version 2016-04-21
 */
@Controller
@RequestMapping(value = "${adminPath}/reserve/reserveVenueEmptyCheck")
public class ReserveVenueEmptyCheckController extends BaseController {

	@Autowired
	private ReserveVenueEmptyCheckService reserveVenueEmptyCheckService;
	
	@ModelAttribute
	public ReserveVenueEmptyCheck get(@RequestParam(required=false) String id) {
		ReserveVenueEmptyCheck entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = reserveVenueEmptyCheckService.get(id);
		}
		if (entity == null){
			entity = new ReserveVenueEmptyCheck();
		}
		return entity;
	}
	
	@RequiresPermissions("reserve:reserveVenueEmptyCheck:view")
	@RequestMapping(value = {"list", ""})
	public String list(ReserveVenueEmptyCheck reserveVenueEmptyCheck, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ReserveVenueEmptyCheck> page = reserveVenueEmptyCheckService.findPage(new Page<ReserveVenueEmptyCheck>(request, response), reserveVenueEmptyCheck); 
		model.addAttribute("page", page);
		return "modules/reserve/reserveVenueEmptyCheckList";
	}

	@RequiresPermissions("reserve:reserveVenueEmptyCheck:view")
	@RequestMapping(value = "form")
	public String form(ReserveVenueEmptyCheck reserveVenueEmptyCheck, Model model) {
		model.addAttribute("reserveVenueEmptyCheck", reserveVenueEmptyCheck);
		return "modules/reserve/reserveVenueEmptyCheckForm";
	}

	@RequiresPermissions("reserve:reserveVenueEmptyCheck:edit")
	@RequestMapping(value = "save")
	public String save(ReserveVenueEmptyCheck reserveVenueEmptyCheck, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, reserveVenueEmptyCheck)){
			return form(reserveVenueEmptyCheck, model);
		}
		reserveVenueEmptyCheckService.save(reserveVenueEmptyCheck);
		addMessage(redirectAttributes, "保存空场审核成功");
		return "redirect:"+Global.getAdminPath()+"/reserve/reserveVenueEmptyCheck/?repage";
	}
	
	@RequiresPermissions("reserve:reserveVenueEmptyCheck:edit")
	@RequestMapping(value = "delete")
	public String delete(ReserveVenueEmptyCheck reserveVenueEmptyCheck, RedirectAttributes redirectAttributes) {
		reserveVenueEmptyCheckService.delete(reserveVenueEmptyCheck);
		addMessage(redirectAttributes, "删除空场审核成功");
		return "redirect:"+Global.getAdminPath()+"/reserve/reserveVenueEmptyCheck/?repage";
	}

}