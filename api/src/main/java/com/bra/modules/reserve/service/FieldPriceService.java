package com.bra.modules.reserve.service;

import com.bra.common.utils.StringUtils;
import com.bra.modules.reserve.dao.FieldPriceSetDao;
import com.bra.modules.reserve.dao.VenueConsItemDao;
import com.bra.modules.reserve.entity.ReserveFieldPriceSet;
import com.bra.modules.reserve.entity.ReserveVenue;
import com.bra.modules.reserve.entity.ReserveVenueCons;
import com.bra.modules.reserve.entity.ReserveVenueConsItem;
import com.bra.modules.reserve.entity.form.FieldPrice;
import com.bra.modules.reserve.entity.form.TimePrice;
import com.bra.modules.reserve.utils.TimeUtils;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by dell on 2016/2/25.
 */
@Service
@Transactional(readOnly = true)
public class FieldPriceService {

    @Autowired
    private VenueConsItemDao venueConsItemDao;
    @Autowired
    private FieldPriceSetDao fieldPriceSetDao;

    /**
     * 根据场馆Id和时间获取场地不同时间段的价格,并查询当前时间是否预定,并标记位已定
     *
     * @param venueId 场馆Id
     * @param date    时间
     * @return
     */
    public List<FieldPrice> findByDate(String venueId,String projectId, String consType, Date date, List<String> times) {
        List<FieldPrice> fieldPriceList = Lists.newLinkedList();

        //查询已预定的信息
        ReserveVenueConsItem reserveVenueCons = new ReserveVenueConsItem();
        reserveVenueCons.setReserveVenue(new ReserveVenue(venueId));
        reserveVenueCons.setConsDate(date);
        reserveVenueCons.getSqlMap().put("projectId",projectId);//所属项目
        reserveVenueCons.setFrequency("all");//所有频率的数据
        reserveVenueCons.setConsWeek(TimeUtils.getWeekOfDate(date));

        ReserveVenueCons venueCons = new ReserveVenueCons();
        venueCons.setReserveType("3");//排除已经取消的
        reserveVenueCons.setConsData(venueCons);
        //查询所有预定的信息(作为本场地的预定标记)
        List<ReserveVenueConsItem> venueConsList = venueConsItemDao.findListByDate(reserveVenueCons);

        //查询价格
        ReserveFieldPriceSet reserveFieldPriceSet = new ReserveFieldPriceSet();
        reserveFieldPriceSet.setReserveVenue(new ReserveVenue(venueId));

        setWeek(reserveFieldPriceSet, date);
        //会员类型(1:散客,2:会员)
        if (StringUtils.isNotBlank(consType)) {
            reserveFieldPriceSet.setConsType(consType);
        }
        String weekType = TimeUtils.getWeekType(date);
        reserveFieldPriceSet.setWeek(weekType);

        List<ReserveFieldPriceSet> reserveFieldPriceSetList = fieldPriceSetDao.findList(reserveFieldPriceSet);//获取场馆下所有的场地
        for(ReserveFieldPriceSet ps:reserveFieldPriceSetList) {//遍历 每个场地
            List<TimePrice> timePriceList = ps.getTimePriceList();
            ps.setTimePriceList(timePriceList);
        }

        buildTimePrice(fieldPriceList, reserveFieldPriceSetList, venueConsList, times);

        return fieldPriceList;
    }


    private void buildTimePrice(List<FieldPrice> fieldPriceList, List<ReserveFieldPriceSet> reserveFieldPriceSetList,
                                List<ReserveVenueConsItem> venueConsList, List<String> times) {

        FieldPrice fieldPrice;

        for (ReserveFieldPriceSet fieldPriceSet : reserveFieldPriceSetList) {
            fieldPrice = new FieldPrice();
            fieldPrice.setVenueId(fieldPriceSet.getReserveVenue().getId());
            fieldPrice.setFieldId(fieldPriceSet.getReserveField().getId());
            fieldPrice.setFieldName(fieldPriceSet.getReserveField().getName());
            List<TimePrice> timePriceList = fieldPriceSet.getTimePriceList();


            TimePrice timePrice;
            for (String time : times) {
                timePrice = new TimePrice();
                timePrice.setTime(time);
                for(TimePrice tp:timePriceList){
                    String t=tp.getTime();
                    Double price=tp.getPrice();
                    String hour=time.substring(0,2);
                    t=t.substring(0,2);
                    if(hour.equals(t)){
                        timePrice.setPrice(price);
                        break;
                    }
                }
                ReserveVenueConsItem item = hasReserve(venueConsList, fieldPriceSet, time);
                if (item != null) {//已经预定
                    timePrice.setConsItem(item);
                    timePrice.setConsType(item.getConsData().getConsType());
                    timePrice.setUserName(item.getConsData().getUserName());
                    timePrice.setStatus(item.getConsData().getReserveType());
                } else {
                    timePrice.setStatus("0");
                }
                fieldPrice.getTimePriceList().add(timePrice);
            }
            fieldPriceList.add(fieldPrice);
        }
    }

    /**
     * @param items
     * @param time  网格时间
     * @return
     */
    private ReserveVenueConsItem hasReserve(List<ReserveVenueConsItem> items, ReserveFieldPriceSet fieldPriceSet, String time) {
        for (ReserveVenueConsItem item : items) {
            if (fieldPriceSet.getReserveField().getId().equals(item.getReserveField().getId())) {
                String startTime = item.getStartTime();
                String endTime = item.getEndTime();
                List<String> times = TimeUtils.getTimeSpacListValue(startTime + ":00", endTime + ":00", 30);
                for (String t : times) {
                    if (time.equals(t)) {
                        return item;
                    }
                }
            }
        }
        return null;
    }

    private void setWeek(ReserveFieldPriceSet reserveFieldPriceSet, Date date) {
        String week = TimeUtils.getWeekOfDate(date);
        if ("周六".equals(week)) {//2
            reserveFieldPriceSet.setWeek("2");
        } else if ("周日".equals(week)) {//3
            reserveFieldPriceSet.setWeek("3");
        } else {//1
            reserveFieldPriceSet.setWeek("1");
        }
    }
}
