package com.bra.modules.reserve.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bra.common.web.annotation.Token;
import com.bra.modules.reserve.entity.ReserveCommoditySell;
import com.bra.modules.reserve.entity.ReserveCommoditySellDetailList;
import com.bra.modules.reserve.entity.ReserveMember;
import com.bra.modules.reserve.service.ReserveCommoditySellService;
import com.bra.modules.reserve.service.ReserveMemberService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bra.common.config.Global;
import com.bra.common.persistence.Page;
import com.bra.common.web.BaseController;
import com.bra.common.utils.StringUtils;
import com.bra.modules.reserve.entity.ReserveCommoditySellDetail;
import com.bra.modules.reserve.service.ReserveCommoditySellDetailService;

import java.util.List;

/**
 * 商品销售明细Controller
 * @author jiangxingqi
 * @version 2016-01-12
 */
@Controller
@RequestMapping(value = "${adminPath}/reserve/reserveCommoditySellDetail")
public class ReserveCommoditySellDetailController extends BaseController {

	@Autowired
	private ReserveCommoditySellDetailService reserveCommoditySellDetailService;

	@Autowired
	private ReserveCommoditySellService reserveCommoditySellService;

	@Autowired
	private ReserveMemberService reserveMemberService;

	@ModelAttribute
	public ReserveCommoditySellDetail get(@RequestParam(required=false) String id) {
		ReserveCommoditySellDetail entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = reserveCommoditySellDetailService.get(id);
		}
		if (entity == null){
			entity = new ReserveCommoditySellDetail();
		}
		return entity;
	}

	@RequestMapping(value = "settlement")
	@Token(save = true)
	public  String settlement(ReserveCommoditySellDetailList sellDetailList, Model model) {
		ReserveMember rm=new ReserveMember();
		rm.setCartType("1");
		List<ReserveMember> reserveMemberList=reserveMemberService.findList(rm);
		model.addAttribute("reserveMemberList",reserveMemberList);
		model.addAttribute("sellDetailList",sellDetailList.getReserveCommoditySellDetailList());
		return "reserve/commodity/reserveCommodityPayForm";
	}

	@RequestMapping(value = "paySubmit")
	@ResponseBody
	@Token(remove = true)
	public  String paySubmit(ReserveCommoditySellDetailList sellDetailList) {
		//销售主表
		Double total=0.0;
		for(ReserveCommoditySellDetail sellDetail:sellDetailList.getReserveCommoditySellDetailList() ){
			Double price=sellDetail.getPrice();
			Integer num=sellDetail.getNum();
			Double detailSum=price*num;
			total+=detailSum;
		}
		ReserveCommoditySell reserveCommoditySell=new ReserveCommoditySell();
		reserveCommoditySell.setTotalSum(total);
		reserveCommoditySell.setGiftFlag("0");
		ReserveMember reserveMember=sellDetailList.getReserveStoredCardMember();

		reserveCommoditySell.setReserveMember(reserveMember);
		reserveCommoditySellService.save(reserveCommoditySell);

		if(reserveMember!=null){ //储值卡会员扣款
			reserveMember=reserveMemberService.get(reserveMember);
			double remainder=reserveMember.getRemainder();
			remainder-=total;
			reserveMember.setRemainder(remainder);
			reserveMemberService.save(reserveMember);
		}
		//销售次表
		for(ReserveCommoditySellDetail sellDetail:sellDetailList.getReserveCommoditySellDetailList() ){
			Double price=sellDetail.getPrice();
			Integer num=sellDetail.getNum();
			Double detailSum=price*num;
			sellDetail.setDetailSum(detailSum);
			sellDetail.setReserveCommoditySell(reserveCommoditySell);
			reserveCommoditySellDetailService.save(sellDetail);
		}
		return "付款成功";
	}

	@RequestMapping(value = {"list", ""})
	public String list(ReserveCommoditySellDetail reserveCommoditySellDetail, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ReserveCommoditySellDetail> page = reserveCommoditySellDetailService.findPage(new Page<ReserveCommoditySellDetail>(request, response), reserveCommoditySellDetail);
		model.addAttribute("page", page);
		return "reserve/record/reserveCommoditySellDetailList";
	}

	@RequestMapping(value = {"findSellDetailList", ""})
	public String findSellDetailList(ReserveCommoditySellDetail reserveCommoditySellDetail, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ReserveCommoditySellDetail> page = reserveCommoditySellDetailService.findSellDetailList(new Page<ReserveCommoditySellDetail>(request, response), reserveCommoditySellDetail);
		model.addAttribute("page", page);
		return "reserve/record/reserveCommoditySellDetailList";
	}

	@RequestMapping(value = "form")
	@Token(save = true)
	public String form(ReserveCommoditySellDetail reserveCommoditySellDetail, Model model) {
		model.addAttribute("reserveCommoditySellDetail", reserveCommoditySellDetail);
		return "reserve/commodity/reserveCommoditySellDetailForm";
	}

	@RequestMapping(value = "save")
	@Token(remove = true)
	public String save(ReserveCommoditySellDetail reserveCommoditySellDetail, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, reserveCommoditySellDetail)){
			return form(reserveCommoditySellDetail, model);
		}
		reserveCommoditySellDetailService.save(reserveCommoditySellDetail);
		addMessage(redirectAttributes, "保存商品销售明细成功");
		return "redirect:"+Global.getAdminPath()+"/reserve/reserveCommoditySellDetail/?repage";
	}

	@RequestMapping(value = "delete")
	public String delete(ReserveCommoditySellDetail reserveCommoditySellDetail, RedirectAttributes redirectAttributes) {
		reserveCommoditySellDetailService.delete(reserveCommoditySellDetail);
		addMessage(redirectAttributes, "删除商品销售明细成功");
		return "redirect:"+Global.getAdminPath()+"/reserve/reserveCommoditySellDetail/?repage";
	}

}