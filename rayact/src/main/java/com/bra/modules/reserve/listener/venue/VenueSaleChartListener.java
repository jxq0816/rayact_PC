package com.bra.modules.reserve.listener.venue;

import com.bra.common.utils.Collections3;
import com.bra.common.utils.StringUtils;
import com.bra.modules.reserve.entity.ReserveVenue;
import com.bra.modules.reserve.entity.ReserveVenueCons;
import com.bra.modules.reserve.event.main.MainControlerEvent;
import com.bra.modules.reserve.service.ReserveVenueConsService;
import com.bra.modules.reserve.service.ReserveVenueService;
import com.bra.modules.reserve.utils.AuthorityUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
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
    private ReserveVenueService reserveVenueService;

    protected int getOrder() {
        return 0;
    }

    private void chartData(Map<String, Object> data, List<String> venueIds) {

        ReserveVenueCons venueCons = new ReserveVenueCons();
        Map<String, Object> search = Maps.newConcurrentMap();
        search.put("dsf", AuthorityUtils.getVenueIds(venueIds));//分库
        venueCons.setSqlMap(search);
        List<Map<String, Object>> venueConsList = reserveVenueConsService.sellOfHistogram(venueCons);

        List<ReserveVenue> reserveVenueList = reserveVenueService.findList(new ReserveVenue());
        List<String> venueListJson = Lists.newArrayList();
        List<String> series = Lists.newArrayList();
       /* List<String> volumeList;*/
        for (ReserveVenue venue : reserveVenueList) {
            //遍历场馆
            String venueName = venue.getName();
            String id=venue.getId();
            venueListJson.add("'" + venueName.toString() + "'");
            String jason="{name:'"+venueName+"', type:'bar',data:[";
           /* volumeList= Lists.newArrayList();//每遍历一个场馆，建立一个统计月份销售额list*/
            for (int i = 1; i <= 12; i++) {
                //遍历每个月份
                int j;
                for (j=0;j<venueConsList.size();j++) {
                    //遍历后台查询的jason
                    Map<String, Object> map=venueConsList.get(j);
                    Integer month = (Integer) map.get("month");
                    String venueId=(String)map.get("venueId");
                    if(StringUtils.isNoneEmpty(venueId)&&venueId.equals(id)){
                        if (month == i) {
                            //月份一致
                            Object transactionVolume = map.get("transactionVolume");
                            if (transactionVolume != null) {
                                jason+=transactionVolume.toString();
                                if(i!=12){
                                    jason+=',';
                                }

                            }
                            break;
                        }
                    }
                }
                if(j==venueConsList.size()){
                    jason+="0";
                    if(i!=12){
                        jason+=',';
                    }
                }
            }
            //当前场馆的销售额 统计完毕
            jason+="],}";
            series.add(jason);
        }
        data.put("venueListJson", StringUtils.join(venueListJson, ","));
        data.put("seriesJson",  StringUtils.join(series, ","));

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
        Map<String, Object> search = Maps.newConcurrentMap();
        search.put("dsf", AuthorityUtils.getVenueIds(venueIds));
        search.put("day", "1");
        venueCons.setSqlMap(search);
        List<Map<String, Object>> venueConsList = reserveVenueConsService.sellOfChart(venueCons);
        Double todayPrice = 0D;
        for (Map<String, Object> cons : venueConsList) {
            Object orderPrice = cons.get("orderPrice");
            if (orderPrice != null) {
                todayPrice += NumberUtils.toDouble(orderPrice.toString());
            }
        }
        data.put("fieldTodayPrice", todayPrice);

        //当月的
        BigDecimal monthPrice = reserveVenueConsService.sellMonthOfChart(venueCons);
        if (monthPrice == null) {
            monthPrice = BigDecimal.ZERO;
        }
        monthPrice = monthPrice.setScale(1, BigDecimal.ROUND_HALF_UP);
        data.put("fieldMonthPrice", monthPrice);

    }
}
