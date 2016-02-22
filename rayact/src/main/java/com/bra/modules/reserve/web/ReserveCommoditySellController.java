package com.bra.modules.reserve.web;

import com.bra.common.utils.StringUtils;
import com.bra.common.web.BaseController;
import com.bra.modules.reserve.entity.ReserveCardStatements;
import com.bra.modules.reserve.entity.ReserveCommoditySell;
import com.bra.modules.reserve.entity.form.ReserveCommoditySellReport;
import com.bra.modules.reserve.service.ReserveCommoditySellService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * 商品销售主表Controller
 * @author jiangxingqi
 * @version 2016-01-12
 */
@Controller
@RequestMapping(value = "${adminPath}/reserve/reserveCommoditySell")
public class ReserveCommoditySellController extends BaseController {

	@Autowired
	private ReserveCommoditySellService reserveCommoditySellService;

	@ModelAttribute
	public ReserveCommoditySell get(@RequestParam(required=false) String id) {
		ReserveCommoditySell entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = reserveCommoditySellService.get(id);
		}
		if (entity == null){
			entity = new ReserveCommoditySell();
		}
		return entity;
	}
	//销售报表
	@RequestMapping(value = {"sellReport", ""})
	public String list(ReserveCommoditySell reserveCommoditySell, Model model) {
		ReserveCommoditySellReport sellReport = reserveCommoditySellService.sellReport(reserveCommoditySell);
		model.addAttribute("sellReport",sellReport);
		return "reserve/commodity/reserveCommoditySellReport";
	}

	/*商品收入比例统计*/
	@RequestMapping(value = {"commodityIncomeReport", ""})
	public String commodityIncomeReport(ReserveCardStatements reserveCardStatements, HttpServletRequest request, HttpServletResponse response, Model model) {
		List<Map<String,Object>> page=reserveCommoditySellService.commodityIncomeRatioReport(reserveCardStatements);
		model.addAttribute("page", page);
		return "reserve/report/commodityIncomeReport";
	}



}