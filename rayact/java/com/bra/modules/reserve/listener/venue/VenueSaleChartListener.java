package com.bra.modules.reserve.listener.venue;

import com.bra.common.utils.Collections3;
import com.bra.common.utils.DateUtils;
import com.bra.common.utils.StringUtils;
import com.bra.modules.reserve.entity.ReserveVenueCons;
import com.bra.modules.reserve.entity.ReserveVenueOrder;
import com.bra.modules.reserve.event.main.MainControlerEvent;
import com.bra.modules.reserve.service.ReserveVenueConsService;
import com.bra.modules.reserve.service.ReserveVenueOrderService;
import com.bra.modules.reserve.utils.AuthorityUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by xiaobin on 16/1/25.
 */
@Component
public class VenueSaleChartListener {

    @Autowired
    private ReserveVenueConsService reserveVenueConsService;
    @Autowired
    private ReserveVenueOrderService reserveVenueOrderService;

    protected int getOrder() {
        return 0;
    }

    private void chartData(Map<String, Object> data, List<String> venueIds) {
        ReserveVenueCons venueCons = new ReserveVenueCons();
        venueCons.getSqlMap().put("dsf", AuthorityUtils.getVenueIds(venueIds));

        //时间
        Map<String,String> search = Maps.newConcurrentMap();
        String startDate = DateUtils.formatDate(DateUtils.addWeeks(new Date(), -10), "yyyy-MM-dd");
        String endDate = DateUtils.formatDate(new Date(), "yyyy-MM-dd");
        search.put("startDate", startDate + " 00:00:00");
        search.put("endDate", endDate + " 23:59:59");
        venueCons.setSqlMap(search);
        venueCons.setReserveType("4");//查询已经结算得信息
        List<ReserveVenueCons> venueConsList = reserveVenueConsService.findList(venueCons);

        Map<String, Double> memberChartMap = Maps.newConcurrentMap();

        for (ReserveVenueCons cons : venueConsList) {
            String date = DateUtils.formatDate(cons.getUpdateDate(), "yyyy-MM-dd");
            if (memberChartMap.get(date) != null) {
                Double price = memberChartMap.get(date);
                memberChartMap.put(date, price + cons.getOrderPrice());
            } else {
                memberChartMap.put(date, cons.getOrderPrice());
            }
        }

        ReserveVenueOrder venueOrder = new ReserveVenueOrder();
        venueOrder.setSqlMap(search);
        List<ReserveVenueOrder> venueOrderList = reserveVenueOrderService.findList(venueOrder);
        for (ReserveVenueOrder cons : venueOrderList) {
            String date = DateUtils.formatDate(cons.getUpdateDate(), "yyyy-MM-dd");
            if (memberChartMap.get(date) != null) {
                Double price = memberChartMap.get(date);
                memberChartMap.put(date, price + cons.getOrderPrice());
            } else {
                memberChartMap.put(date, cons.getOrderPrice());
            }
        }

        List<String> dateList = Lists.newArrayList();
        for (String date : memberChartMap.keySet()) {
            dateList.add("'" + date + "'");
        }
        data.put("fieldChartMapX", StringUtils.join(dateList, ","));
        data.put("fieldChartMapY", StringUtils.join(memberChartMap.values(), ","));
    }

    @EventListener
    protected void onEvent(MainControlerEvent event) {
        Map<String, Object> data = event.getMainBean().getMap();
        List<String> venueIds = (List<String>) data.get("venueIds");
        if (Collections3.isEmpty(venueIds)) {
            return;
        }
        chartData(data, venueIds);
        loadData(data, venueIds);
    }

    private void loadData(Map<String, Object> data, List<String> venueIds) {
        //当天得交易额
        ReserveVenueCons venueCons = new ReserveVenueCons();
        Map<String,String> search = Maps.newConcurrentMap();
        search.put("dsf", AuthorityUtils.getVenueIds(venueIds));
        String endDate = DateUtils.formatDate(new Date(), "yyyy-MM-dd");
        search.put("startDate", endDate + " 00:00:00");
        search.put("endDate", endDate + " 23:59:59");
        venueCons.setSqlMap(search);
        venueCons.setReserveType("4");//查询已经结算得信息
        List<ReserveVenueCons> venueConsList = reserveVenueConsService.findList(venueCons);
        Double todayPrice = 0D;
        for (ReserveVenueCons cons : venueConsList) {
            todayPrice += cons.getOrderPrice();
        }
        //人次费用
        ReserveVenueOrder venueOrder = new ReserveVenueOrder();
        venueOrder.setSqlMap(search);
        List<ReserveVenueOrder> venueOrderList = reserveVenueOrderService.findList(venueOrder);
        for (ReserveVenueOrder cons : venueOrderList) {
            todayPrice += cons.getOrderPrice();
        }
        data.put("fieldTodayPrice", todayPrice);

        //当月的
        String startDate = DateUtils.getFirstDayOfMonth();
        venueCons.getSqlMap().put("startDate", startDate + " 00:00:00");
        venueConsList = reserveVenueConsService.findList(venueCons);
        Double MonthPrice = 0D;
        for (ReserveVenueCons cons : venueConsList) {
            MonthPrice += cons.getOrderPrice();
        }

        //人次费用
        venueOrder.getSqlMap().put("startDate", startDate + " 00:00:00");
        venueOrderList = reserveVenueOrderService.findList(venueOrder);
        for (ReserveVenueOrder cons : venueOrderList) {
            MonthPrice += cons.getOrderPrice();
        }
        data.put("fieldMonthPrice", MonthPrice);

    }
}
