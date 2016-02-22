package com.bra.modules.reserve.web;

import com.bra.common.utils.StringUtils;
import com.bra.common.web.BaseController;
import com.bra.modules.reserve.entity.ReserveCardStatements;
import com.bra.modules.reserve.entity.ReserveCommoditySell;
import com.bra.modules.reserve.entity.ReserveCommodityType;
import com.bra.modules.reserve.entity.ReserveVenue;
import com.bra.modules.reserve.entity.form.ReserveCommodityIntervalReport;
import com.bra.modules.reserve.entity.form.ReserveCommoditySellReport;
import com.bra.modules.reserve.service.ReserveCommoditySellService;
import com.bra.modules.reserve.service.ReserveCommodityTypeService;
import com.bra.modules.reserve.service.ReserveVenueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
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
	private ReserveVenueService reserveVenueService;

	@Autowired
	private ReserveCommodityTypeService reserveCommodityTypeService;

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

	/*商品收入统计*/
	@RequestMapping(value = {"commodityIncomeIntervalReport", ""})
	public String reserveCommodityIncomeIntervalReport(ReserveCommodityIntervalReport reserveCommodityIntervalReport,Model model){
		Date startDate=reserveCommodityIntervalReport.getStartDate();//月份
		Date endDate=reserveCommodityIntervalReport.getEndDate();//月份
		reserveCommodityIntervalReport.setReserveCommodityType( reserveCommodityTypeService.get(reserveCommodityIntervalReport.getReserveCommodityType()));
		if(startDate==null){
			startDate=new Date();//默认当天
			reserveCommodityIntervalReport.setStartDate(startDate);
		}
		if(endDate==null){
			endDate=new Date();//默认当天
			reserveCommodityIntervalReport.setEndDate(endDate);
		}
		ReserveVenue venue=reserveCommodityIntervalReport.getReserveVenue();//场馆
		if(venue==null){
			venue=new ReserveVenue();
		}else if(StringUtils.isNoneEmpty(venue.getId())){
			venue=reserveVenueService.get(venue);
		}
		reserveCommodityIntervalReport.setReserveVenue(venue);

		List<ReserveVenue> reserveVenueList=reserveVenueService.findList(new ReserveVenue());//场馆列表
		List<ReserveCommodityType> reserveCommodityTypeList=reserveCommodityTypeService.findList(new ReserveCommodityType());
		List<ReserveCommodityIntervalReport> intervalReports=reserveCommoditySellService.reserveCommodityIncomeIntervalReport(reserveCommodityIntervalReport);
		List<Map<String,Object>> incomeRatioReports=reserveCommoditySellService.commodityIncomeRatioReport(reserveCommodityIntervalReport);//收入比例
		model.addAttribute("intervalReports", intervalReports);
		model.addAttribute("incomeRatioReports", incomeRatioReports);
		model.addAttribute("reserveVenueList", reserveVenueList);
		model.addAttribute("reserveCommodityTypeList", reserveCommodityTypeList);
		model.addAttribute("reserveCommodityIntervalReport", reserveCommodityIntervalReport);
		return "reserve/report/commodityIncomeReport";
	}

}