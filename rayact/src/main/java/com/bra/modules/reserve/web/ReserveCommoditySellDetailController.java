package com.bra.modules.reserve.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bra.common.web.annotation.Token;
import com.bra.modules.reserve.entity.ReserveCommoditySellDetailList;
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
	private ReserveCommoditySellDetailList reserveCommoditySellDetailList;
	
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
	@RequestMapping(value = "pay")
	@ResponseBody
	@Token(remove = true)
	public  String pay(ReserveCommoditySellDetailList sellDetailList) {
		List<ReserveCommoditySellDetail> list=sellDetailList.getReserveCommoditySellDetailList();
		for(ReserveCommoditySellDetail sellDetail:list ){
			Double price=sellDetail.getPrice();
			Integer num=sellDetail.getNum();
			Double detailSum=price*num;
			sellDetail.setDetailSum(detailSum);
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

	@RequiresPermissions("reserve:reserveCommoditySellDetail:view")
	@RequestMapping(value = "form")
	@Token(save = true)
	public String form(ReserveCommoditySellDetail reserveCommoditySellDetail, Model model) {
		model.addAttribute("reserveCommoditySellDetail", reserveCommoditySellDetail);
		return "reserve/commodity/reserveCommoditySellDetailForm";
	}

	@RequiresPermissions("reserve:reserveCommoditySellDetail:edit")
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
	
	@RequiresPermissions("reserve:reserveCommoditySellDetail:edit")
	@RequestMapping(value = "delete")
	public String delete(ReserveCommoditySellDetail reserveCommoditySellDetail, RedirectAttributes redirectAttributes) {
		reserveCommoditySellDetailService.delete(reserveCommoditySellDetail);
		addMessage(redirectAttributes, "删除商品销售明细成功");
		return "redirect:"+Global.getAdminPath()+"/reserve/reserveCommoditySellDetail/?repage";
	}

}