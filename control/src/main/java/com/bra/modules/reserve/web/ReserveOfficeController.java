package com.bra.modules.reserve.web;

import com.bra.common.config.Global;
import com.bra.common.persistence.Page;
import com.bra.common.utils.StringUtils;
import com.bra.common.web.BaseController;
import com.bra.modules.reserve.entity.ReserveOffice;
import com.bra.modules.reserve.service.ReserveOfficeService;
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
 * 集团Controller
 * @author jiangxingqi
 * @version 2016-07-11
 */
@Controller
@RequestMapping(value = "${adminPath}/reserve/reserveOffice")
public class ReserveOfficeController extends BaseController {

	@Autowired
	private ReserveOfficeService reserveOfficeService;
	
	@ModelAttribute
	public ReserveOffice get(@RequestParam(required=false) String id) {
		ReserveOffice entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = reserveOfficeService.get(id);
		}
		if (entity == null){
			entity = new ReserveOffice();
		}
		return entity;
	}
	
	@RequiresPermissions("reserve:reserveOffice:view")
	@RequestMapping(value = {"list", ""})
	public String list(ReserveOffice reserveOffice, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ReserveOffice> page = reserveOfficeService.findPage(new Page<ReserveOffice>(request, response), reserveOffice); 
		model.addAttribute("page", page);
		return "reserve/office/reserveOfficeList";
	}

	@RequiresPermissions("reserve:reserveOffice:view")
	@RequestMapping(value = "form")
	public String form(ReserveOffice reserveOffice, Model model) {
		model.addAttribute("reserveOffice", reserveOffice);
		return "reserve/office/reserveOfficeForm";
	}

	@RequiresPermissions("reserve:reserveOffice:edit")
	@RequestMapping(value = "save")
	public String save(ReserveOffice reserveOffice, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, reserveOffice)){
			return form(reserveOffice, model);
		}
		reserveOfficeService.save(reserveOffice);
		addMessage(redirectAttributes, "保存集团成功");
		return "redirect:"+Global.getAdminPath()+"/reserve/reserveOffice/?repage";
	}
	
	@RequiresPermissions("reserve:reserveOffice:edit")
	@RequestMapping(value = "delete")
	public String delete(ReserveOffice reserveOffice, RedirectAttributes redirectAttributes) {
		reserveOfficeService.delete(reserveOffice);
		addMessage(redirectAttributes, "删除集团成功");
		return "redirect:"+Global.getAdminPath()+"/reserve/reserveOffice/?repage";
	}

}