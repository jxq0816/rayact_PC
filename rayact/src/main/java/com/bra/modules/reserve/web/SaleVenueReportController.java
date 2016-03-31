package com.bra.modules.reserve.web;

import com.bra.common.utils.DateUtils;
import com.bra.common.utils.Numbers;
import com.bra.common.web.BaseController;
import com.bra.modules.reserve.entity.ReserveVenue;
import com.bra.modules.reserve.entity.ReserveVenueCons;
import com.bra.modules.reserve.entity.ReserveVenueOrder;
import com.bra.modules.reserve.service.ReserveVenueConsService;
import com.bra.modules.reserve.service.ReserveVenueOrderService;
import com.bra.modules.reserve.service.ReserveVenueService;
import com.bra.modules.reserve.utils.ExcelInfo;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
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

    @RequestMapping(value = "export")
    public void export(String search, Date startDate, Date endDate,HttpServletResponse response)throws Exception {
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
        String[] titles = {"项目类型","应收金额","订单金额","会员优惠","订单数量","会员卡","现金","银行卡","微信","支付宝","其它"};
        List<String[]> contentList = new ArrayList<>();
        for(Map<String,Object> map :saleVenueList){
            String[] o = new String[11];
            o[0] = map.get("projectName")+"("+map.get("saleType")+")";
            o[1] = map.get("shouldPrice")!=null?String.valueOf(map.get("shouldPrice")):"";
            o[2] = map.get("orderPrice")!=null?String.valueOf( map.get("orderPrice")):"";
            o[3] = map.get("discountPrice")!=null?String.valueOf(map.get("discountPrice")):"";
            o[4] =  map.get("total")!=null?String.valueOf(map.get("total")):"";
            o[5] =  map.get("memberPrice")!=null?String.valueOf(map.get("memberPrice")):"";
            o[6] =  map.get("moneyPrice")!=null?String.valueOf(map.get("moneyPrice")):"";
            o[7] =  map.get("cardPrice")!=null?String.valueOf(map.get("cardPrice")):"";
            o[8] =  map.get("weixinPrice")!=null?String.valueOf(map.get("weixinPrice")):"";
            o[9] =  map.get("zfbPrice")!=null?String.valueOf(map.get("zfbPrice")):"";
            o[10] =  map.get("otherPrice")!=null?String.valueOf(map.get("otherPrice")):"";
            contentList.add(o);
        }
        Date now = new Date();
        ExcelInfo info = new ExcelInfo(response,"场地售卖报表"+DateUtils.formatDate(now),titles,contentList);
        info.export();
    }

    /**
     * 查询空场率
     *
     * @return
     */
    @RequestMapping(value = "listOpenRate")
    public String listOpenRate(Model model, String search, Date startDate, Date endDate) {
        model.addAttribute("search", search);
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);
        if (startDate != null || endDate != null) {
            model.addAttribute("search", "4");
        }
        ReserveVenueCons venueCons = new ReserveVenueCons();
        venueCons.getSqlMap().put("search", search);
        venueCons.getSqlMap().put("startDate", DateUtils.formatDate(startDate));
        venueCons.getSqlMap().put("endDate", DateUtils.formatDate(endDate));
        List<Map<String, Object>> openRateList = reserveVenueConsService.findOpenRateReport(venueCons);
        for (Map<String, Object> map : openRateList) {
            Double utilization_time = NumberUtils.toDouble(map.get("utilization_time").toString());//利用时间
            Double business_time = NumberUtils.toDouble(map.get("business_time").toString());//营业时间
            String percent = Numbers.percent(utilization_time, business_time);
            map.put("percent", percent);
            map.put("free_time", business_time - utilization_time);
        }
        model.addAttribute("openRateList", openRateList);
        return "/reserve/saleField/listOpenRate";
    }

    @RequestMapping(value = "listOpenRateExport")
    public void listOpenRateExport(String search, Date startDate, Date endDate,HttpServletResponse response)throws Exception{
        ReserveVenueCons venueCons = new ReserveVenueCons();
        venueCons.getSqlMap().put("search", search);
        venueCons.getSqlMap().put("startDate", DateUtils.formatDate(startDate));
        venueCons.getSqlMap().put("endDate", DateUtils.formatDate(endDate));
        List<Map<String, Object>> openRateList = reserveVenueConsService.findOpenRateReport(venueCons);
        for (Map<String, Object> map : openRateList) {
            Double utilization_time = NumberUtils.toDouble(map.get("utilization_time").toString());//利用时间
            Double business_time = NumberUtils.toDouble(map.get("business_time").toString());//营业时间
            String percent = Numbers.percent(utilization_time, business_time);
            map.put("percent", percent);
            map.put("free_time", business_time - utilization_time);
        }
        String[] titles = {"项目类型","场地名称","营业时间","占用时间","空闲时间","场地利用率"};
        List<String[]> contentList = new ArrayList<>();
        for(Map<String,Object> map :openRateList){
            String[] o = new String[6];
            o[0] = String.valueOf(map.get("project_name"));
            o[1] = map.get("venue_name")!=null?String.valueOf(map.get("venue_name")):""+map.get("field_name")!=null?String.valueOf(map.get("field_name")):"";
            o[2] = map.get("business_time")!=null?String.valueOf( map.get("business_time")):"";
            o[3] = map.get("utilization_time")!=null?String.valueOf(map.get("utilization_time")):"";
            o[4] =  map.get("free_time")!=null?String.valueOf(map.get("free_time")):"";
            o[5] =  map.get("percent")!=null?String.valueOf(map.get("percent")):"";
            contentList.add(o);
        }
        Date now = new Date();
        ExcelInfo info = new ExcelInfo(response,"场地利用率报表"+DateUtils.formatDate(now),titles,contentList);
        info.export();
    }
}
