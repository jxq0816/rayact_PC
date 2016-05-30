package com.bra.modules.reserve.web;

import com.bra.common.config.Global;
import com.bra.common.utils.StringUtils;
import com.bra.common.web.BaseController;
import com.bra.modules.reserve.entity.ReserveMultiplePayment;
import com.bra.modules.reserve.service.ReserveMultiplePaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 多方式付款Controller
 * @author jiangxingqi
 * @version 2016-04-20
 */
@Controller
@RequestMapping(value = "${adminPath}/reserve/reserveMultiplePayment")
public class ReserveMultiplePaymentController extends BaseController {

	@Autowired
	private ReserveMultiplePaymentService reserveMultiplePaymentService;
	
	@ModelAttribute
	public ReserveMultiplePayment get(@RequestParam(required=false) String id) {
		ReserveMultiplePayment entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = reserveMultiplePaymentService.get(id);
		}
		if (entity == null){
			entity = new ReserveMultiplePayment();
		}
		return entity;
	}
	
	@RequestMapping(value = {"list", ""})
	public String list(ReserveMultiplePayment reserveMultiplePayment,String orderId, HttpServletRequest request, HttpServletResponse response, Model model) {
		reserveMultiplePayment.setOrderId(orderId);
		List<ReserveMultiplePayment> list = reserveMultiplePaymentService.findList(reserveMultiplePayment);
		model.addAttribute("list", list);
		return "reserve/saleField/multiplePaymentList";
	}

	@RequestMapping(value = "form")
	public String form(ReserveMultiplePayment reserveMultiplePayment, Model model) {
		model.addAttribute("reserveMultiplePayment", reserveMultiplePayment);
		return "modules/reserve/reserveMultiplePaymentForm";
	}

	@RequestMapping(value = "save")
	public String save(ReserveMultiplePayment reserveMultiplePayment, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, reserveMultiplePayment)){
			return form(reserveMultiplePayment, model);
		}
		reserveMultiplePaymentService.save(reserveMultiplePayment);
		addMessage(redirectAttributes, "保存多方式付款成功");
		return "redirect:"+Global.getAdminPath()+"/reserve/reserveMultiplePayment/?repage";
	}
	
	@RequestMapping(value = "delete")
	public String delete(ReserveMultiplePayment reserveMultiplePayment, RedirectAttributes redirectAttributes) {
		reserveMultiplePaymentService.delete(reserveMultiplePayment);
		addMessage(redirectAttributes, "删除多方式付款成功");
		return "redirect:"+Global.getAdminPath()+"/reserve/reserveMultiplePayment/?repage";
	}

}