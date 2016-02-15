package com.bra.modules.reserve.web;

import com.bra.common.utils.StringUtils;
import com.bra.common.web.BaseController;
import com.bra.modules.reserve.entity.ReserveCommoditySell;
import com.bra.modules.reserve.entity.form.ReserveCommoditySellReport;
import com.bra.modules.reserve.service.ReserveCommoditySellService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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


}