package com.bra.modules.reserve.web;

import com.bra.common.config.Global;
import com.bra.common.persistence.Page;
import com.bra.common.utils.StringUtils;
import com.bra.common.web.BaseController;
import com.bra.modules.reserve.entity.ReserveMember;
import com.bra.modules.reserve.entity.ReserveTimeCardPrepayment;
import com.bra.modules.reserve.service.ReserveTimeCardPrepaymentService;
import com.bra.modules.sys.utils.UserUtils;
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
 * 次卡预付款Controller
 * @author jiangxingqi
 * @version 2016-04-19
 */
@Controller
@RequestMapping(value = "${adminPath}/reserve/reserveTimeCardPrepayment")
public class ReserveTimeCardPrepaymentController extends BaseController {

	@Autowired
	private ReserveTimeCardPrepaymentService reserveTimeCardPrepaymentService;
	
	@ModelAttribute
	public ReserveTimeCardPrepayment get(@RequestParam(required=false) String id) {
		ReserveTimeCardPrepayment entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = reserveTimeCardPrepaymentService.get(id);
		}
		if (entity == null){
			entity = new ReserveTimeCardPrepayment();
		}
		return entity;
	}
	
	@RequestMapping(value = {"list", ""})
	public String list(String memberId,ReserveTimeCardPrepayment reserveTimeCardPrepayment, HttpServletRequest request, HttpServletResponse response, Model model) {
		ReserveMember member=new ReserveMember();
		member.setId(memberId);
		reserveTimeCardPrepayment.setReserveMember(member);
		Page<ReserveTimeCardPrepayment> page = reserveTimeCardPrepaymentService.findPage(new Page<ReserveTimeCardPrepayment>(request, response), reserveTimeCardPrepayment);
		model.addAttribute("page", page);
		String userType= UserUtils.getUser().getUserType();
		model.addAttribute("userType", userType);
		return "reserve/member/prepaymentList";
	}

	@RequestMapping(value = "form")
	public String form(ReserveTimeCardPrepayment reserveTimeCardPrepayment, Model model) {
		model.addAttribute("reserveTimeCardPrepayment", reserveTimeCardPrepayment);
		return "modules/reserve/reserveTimeCardPrepaymentForm";
	}

	@RequestMapping(value = "save")
	public String save(ReserveTimeCardPrepayment reserveTimeCardPrepayment, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, reserveTimeCardPrepayment)){
			return form(reserveTimeCardPrepayment, model);
		}
		reserveTimeCardPrepaymentService.save(reserveTimeCardPrepayment);
		addMessage(redirectAttributes, "保存次卡预付款成功");
		return "redirect:"+Global.getAdminPath()+"/reserve/reserveTimeCardPrepayment/?repage";
	}
	
	@RequestMapping(value = "delete")
	public String delete(ReserveTimeCardPrepayment reserveTimeCardPrepayment, RedirectAttributes redirectAttributes) {
		reserveTimeCardPrepaymentService.delete(reserveTimeCardPrepayment);
		addMessage(redirectAttributes, "删除次卡预付款成功");
		redirectAttributes.addAttribute("memberId",reserveTimeCardPrepayment.getReserveMember().getId());
		return "redirect:"+Global.getAdminPath()+"/reserve/reserveTimeCardPrepayment/list";
	}

}