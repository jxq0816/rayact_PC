package com.bra.modules.reserve.web;

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
import com.bra.modules.reserve.entity.ReserveCheckDetail;
import com.bra.modules.reserve.service.ReserveCheckDetailService;

/**
 * 账目审核Controller
 * @author xudl
 * @version 2016-04-22
 */
@Controller
@RequestMapping(value = "${adminPath}/reserve/reserveCheckDetail")
public class ReserveCheckDetailController extends BaseController {

	@Autowired
	private ReserveCheckDetailService reserveCheckDetailService;
	
	@ModelAttribute
	public ReserveCheckDetail get(@RequestParam(required=false) String id) {
		ReserveCheckDetail entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = reserveCheckDetailService.get(id);
		}
		if (entity == null){
			entity = new ReserveCheckDetail();
		}
		return entity;
	}
	
	@RequiresPermissions("reserve:reserveCheckDetail:view")
	@RequestMapping(value = {"list", ""})
	public String list(ReserveCheckDetail reserveCheckDetail, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ReserveCheckDetail> page = reserveCheckDetailService.findPage(new Page<ReserveCheckDetail>(request, response), reserveCheckDetail); 
		model.addAttribute("page", page);
		return "modules/reserve/reserveCheckDetailList";
	}

	@RequiresPermissions("reserve:reserveCheckDetail:view")
	@RequestMapping(value = "form")
	public String form(ReserveCheckDetail reserveCheckDetail, Model model) {
		model.addAttribute("reserveCheckDetail", reserveCheckDetail);
		return "modules/reserve/reserveCheckDetailForm";
	}

	@RequiresPermissions("reserve:reserveCheckDetail:edit")
	@RequestMapping(value = "save")
	public String save(ReserveCheckDetail reserveCheckDetail, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, reserveCheckDetail)){
			return form(reserveCheckDetail, model);
		}
		reserveCheckDetailService.save(reserveCheckDetail);
		addMessage(redirectAttributes, "保存账目审核成功");
		return "redirect:"+Global.getAdminPath()+"/reserve/reserveCheckDetail/?repage";
	}
	
	@RequiresPermissions("reserve:reserveCheckDetail:edit")
	@RequestMapping(value = "delete")
	public String delete(ReserveCheckDetail reserveCheckDetail, RedirectAttributes redirectAttributes) {
		reserveCheckDetailService.delete(reserveCheckDetail);
		addMessage(redirectAttributes, "删除账目审核成功");
		return "redirect:"+Global.getAdminPath()+"/reserve/reserveCheckDetail/?repage";
	}

}