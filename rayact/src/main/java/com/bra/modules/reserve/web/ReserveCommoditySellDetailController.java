package com.bra.modules.reserve.web;

import com.bra.common.config.Global;
import com.bra.common.persistence.Page;
import com.bra.common.utils.StringUtils;
import com.bra.common.web.BaseController;
import com.bra.common.web.annotation.Token;
import com.bra.modules.reserve.entity.*;
import com.bra.modules.reserve.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
	private ReserveCommodityService reserveCommodityService;

	@Autowired
	private ReserveCommoditySellDetailService reserveCommoditySellDetailService;

	@Autowired
	private ReserveCommoditySellService reserveCommoditySellService;

	@Autowired
	private ReserveMemberService reserveMemberService;

	@Autowired
	private ReserveCardStatementsService reserveCardStatementsService;

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
		String payType=sellDetailList.getPayType();
		for(ReserveCommoditySellDetail sellDetail:sellDetailList.getReserveCommoditySellDetailList() ){
			Double price=sellDetail.getPrice();
			Integer num=sellDetail.getNum();
			Double detailSum=price*num;
			total+=detailSum;
		}
		ReserveCommoditySell reserveCommoditySell=new ReserveCommoditySell();
		reserveCommoditySell.setTotalSum(total);
		reserveCommoditySell.setGiftFlag("0");
		reserveCommoditySell.setPayType(payType);
		ReserveMember reserveMember=sellDetailList.getReserveStoredCardMember();

		reserveCommoditySell.setReserveMember(reserveMember);
		reserveCommoditySellService.save(reserveCommoditySell);

		String sellId=reserveCommoditySell.getId();

		//销售明细表
		for(ReserveCommoditySellDetail sellDetail:sellDetailList.getReserveCommoditySellDetailList() ){
			Double price=sellDetail.getPrice();
			Integer num=sellDetail.getNum();
			Double detailSum=price*num;
			ReserveCommodity commodity=sellDetail.getReserveCommodity();//买的啥
			sellDetail.setDetailSum(detailSum);
			sellDetail.setReserveMember(reserveMember);
			sellDetail.setReserveCommoditySell(reserveCommoditySell);
			commodity=reserveCommodityService.get(commodity);
			int repertoryNum=commodity.getRepertoryNum();
			repertoryNum-=num;//商品出库
			commodity.setRepertoryNum(repertoryNum);
			reserveCommodityService.save(commodity);

			reserveCommoditySellDetailService.save(sellDetail);
		}
		if("1".equals(payType)){// 1代表会员
			if(reserveMember!=null){
				reserveMember=reserveMemberService.get(reserveMember);
				double remainder=reserveMember.getRemainder();
				remainder-=total;//减去余额
				reserveMember.setRemainder(remainder);
				reserveMemberService.save(reserveMember);
			}
		}
		//销售记录
		ReserveCardStatements reserveCardStatements=new ReserveCardStatements();
		reserveCardStatements.setReserveMember(reserveMember);
		reserveCardStatements.setTransactionType("3");//3代表消费
		reserveCardStatements.setPayType(payType);
		reserveCardStatements.setTransactionVolume(total);//消费额
		reserveCardStatementsService.save(reserveCardStatements);

		return sellId;
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