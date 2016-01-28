package com.bra.modules.reserve.web;

import com.bra.common.utils.DateUtils;
import com.bra.common.web.BaseController;
import com.bra.modules.reserve.entity.ReserveVenue;
import com.bra.modules.reserve.entity.ReserveVenueCons;
import com.bra.modules.reserve.entity.ReserveVenueOrder;
import com.bra.modules.reserve.service.ReserveVenueConsService;
import com.bra.modules.reserve.service.ReserveVenueOrderService;
import com.bra.modules.reserve.service.ReserveVenueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 场地售卖报表
 * Created by xiaobin on 16/1/28.
 */
@Controller
@RequestMapping(value = "${adminPath}/reserve/saleVenueReport")
public class SaleVenueReportController extends BaseController {

    //场地售卖
    @Autowired
    private ReserveVenueConsService reserveVenueConsService;
    //人次售卖
    @Autowired
    private ReserveVenueOrderService reserveVenueOrderService;
    @Autowired
    private ReserveVenueService reserveVenueService;

    @RequestMapping(value = "list")
    public String list(Model model, String search, Date startDate, Date endDate) {

        model.addAttribute("search", search);
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);
        if (startDate != null || endDate != null) {
            model.addAttribute("search", "4");
        }

        model.addAttribute("venueList", reserveVenueService.findList(new ReserveVenue()));

        ReserveVenueCons venueCons = new ReserveVenueCons();
        venueCons.getSqlMap().put("search", search);
        venueCons.getSqlMap().put("startDate", DateUtils.formatDate(startDate));
        venueCons.getSqlMap().put("endDate", DateUtils.formatDate(endDate));
        List<Map<String, Object>> saleVenueList = reserveVenueConsService.findPriceGroupProjectReport(venueCons);
        for (Map<String, Object> map : saleVenueList) {
            map.put("saleType", "场地费");
        }

        ReserveVenueOrder venueOrder = new ReserveVenueOrder();
        venueOrder.getSqlMap().put("search", search);
        venueOrder.getSqlMap().put("startDate", DateUtils.formatDate(startDate));
        venueOrder.getSqlMap().put("endDate", DateUtils.formatDate(endDate));
        List<Map<String, Object>> saleOrderList = reserveVenueOrderService.findPriceGroupProjectReport(venueOrder);
        for (Map<String, Object> map : saleOrderList) {
            map.put("saleType", "人次票");
        }
        saleVenueList.addAll(saleOrderList);
        model.addAttribute("orderList", saleVenueList);
        return "/reserve/saleField/saleFileReport";
    }
}
