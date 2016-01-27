package com.bra.modules.reserve.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bra.common.web.annotation.Token;
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
import com.bra.modules.reserve.entity.ReserveTutorOrder;
import com.bra.modules.reserve.service.ReserveTutorOrderService;

/**
 * 教练订单Controller
 * @author 肖斌
 * @version 2016-01-19
 */
@Controller
@RequestMapping(value = "${adminPath}/reserve/reserveTutorOrder")
public class ReserveTutorOrderController extends BaseController {

	@Autowired
	private ReserveTutorOrderService reserveTutorOrderService;
	
	@ModelAttribute
	public ReserveTutorOrder get(@RequestParam(required=false) String id) {
		ReserveTutorOrder entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = reserveTutorOrderService.get(id);
		}
		if (entity == null){
			entity = new ReserveTutorOrder();
		}
		return entity;
	}
	
	@RequestMapping(value = {"list", ""})
	public String list(ReserveTutorOrder reserveTutorOrder, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ReserveTutorOrder> page = reserveTutorOrderService.findPage(new Page<ReserveTutorOrder>(request, response), reserveTutorOrder); 
		model.addAttribute("page", page);
		return "modules/reserve/reserveTutorOrderList";
	}

	@RequestMapping(value = "form")
	@Token(save = true)
	public String form(ReserveTutorOrder reserveTutorOrder, Model model) {
		model.addAttribute("reserveTutorOrder", reserveTutorOrder);
		return "modules/reserve/reserveTutorOrderForm";
	}

	@RequestMapping(value = "save")
	@Token(remove = true)
	public String save(ReserveTutorOrder reserveTutorOrder, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, reserveTutorOrder)){
			return form(reserveTutorOrder, model);
		}
		reserveTutorOrderService.save(reserveTutorOrder);
		addMessage(redirectAttributes, "保存教练订单成功");
		return "redirect:"+Global.getAdminPath()+"/reserve/reserveTutorOrder/?repage";
	}
	
	@RequestMapping(value = "delete")
	public String delete(ReserveTutorOrder reserveTutorOrder, RedirectAttributes redirectAttributes) {
		reserveTutorOrderService.delete(reserveTutorOrder);
		addMessage(redirectAttributes, "删除教练订单成功");
		return "redirect:"+Global.getAdminPath()+"/reserve/reserveTutorOrder/?repage";
	}

}