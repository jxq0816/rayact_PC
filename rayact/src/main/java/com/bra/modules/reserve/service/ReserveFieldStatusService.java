package com.bra.modules.reserve.service;

import com.bra.common.utils.StringUtils;
import com.bra.modules.reserve.dao.ReserveFieldPriceSetDao;
import com.bra.modules.reserve.dao.ReserveVenueConsItemDao;
import com.bra.modules.reserve.dao.ReserveVenueOrderDao;
import com.bra.modules.reserve.entity.*;
import com.bra.modules.reserve.entity.form.FieldPrice;
import com.bra.modules.reserve.entity.form.TimePrice;
import com.bra.modules.reserve.utils.TimeUtils;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by jiangxingqi on 16/1/11.
 */
@Service
@Transactional(readOnly = true)
public class ReserveFieldStatusService {

    @Autowired
    private ReserveTimeIntervalService reserveTimeIntervalService;

    @Autowired
    private ReserveFieldService reserveFieldService;
    @Autowired
    private ReserveFieldPriceSetDao reserveFieldPriceSetDao;
    //预定信息
    @Autowired
    private ReserveVenueConsItemDao reserveVenueConsItemDao;
    @Autowired
    private ReserveVenueEmptyCheckService reserveVenueEmptyCheckService;
    @Autowired
    private ReserveVenueOrderDao reserveVenueOrderDao;

    /**
     * 根据场馆Id和时间获取场地不同时间段的价格,并查询当前时间是否已经通过空场审核
     *
     * @param venueId 场馆Id
     * @param date    时间
     * @return
     */
    public List<FieldPrice> emptyCheck(String venueId,Date date, List<String> times) {

        //查询场馆中所有场地
        ReserveField field = new ReserveField();
        ReserveVenue venue = new ReserveVenue();
        venue.setId(venueId);
        field.setReserveVenue(venue);
        List<ReserveField> fieldList = reserveFieldService.findList(field);

        //查询已预定的信息
        ReserveVenueConsItem reserveVenueCons = new ReserveVenueConsItem();
        reserveVenueCons.setReserveVenue(new ReserveVenue(venueId));
        reserveVenueCons.setConsDate(date);
        List<ReserveVenueConsItem> venueConsList = reserveVenueConsItemDao.findListByDate(reserveVenueCons);

        //查询场次票售卖状况
        ReserveVenueOrder order = new ReserveVenueOrder();
        order.setReserveVenue(new ReserveVenue(venueId));
        order.setOrderDate(date);
        List<ReserveVenueOrder> venueOrderList = reserveVenueOrderDao.findList(order);

        //查询已审核的信息
        ReserveVenueEmptyCheck reserveVenueEmptyCheck = new ReserveVenueEmptyCheck();
        reserveVenueEmptyCheck.setVenue(new ReserveVenue(venueId));
        reserveVenueEmptyCheck.setCheckDate(date);
        List<ReserveVenueEmptyCheck> emptyChecks = reserveVenueEmptyCheckService.findList(reserveVenueEmptyCheck);
        //获取场地的时间价格
        List<ReserveFieldPriceSet> reserveFieldPriceSetList=this.fieldTimePriceList(fieldList,date,venue);
        //获得场地的状态
        List<FieldPrice> fieldPriceList=fieldStatus(reserveFieldPriceSetList, venueConsList,venueOrderList,emptyChecks, times);
        return fieldPriceList;
    }
    //获取场地的时间价格
    private  List<ReserveFieldPriceSet> fieldTimePriceList(List<ReserveField> fieldList,Date date,ReserveVenue venue){
        //设置场地的价格的查询条件
        ReserveFieldPriceSet reserveFieldPriceSet = new ReserveFieldPriceSet();
        reserveFieldPriceSet.setReserveVenue(venue);
        reserveFieldPriceSet.setConsType("1"); //会员类型(1:散客,2:会员)
        String weekType = TimeUtils.getWeekType(date);//获得当天属于周几
        reserveFieldPriceSet.setWeek(weekType);
        List<ReserveFieldPriceSet> reserveFieldPriceSetList = new ArrayList<>();
        for (ReserveField i : fieldList) {
            reserveFieldPriceSet.setReserveField(i);
            //设置场地的时令
            if ("1".equals(i.getIsTimeInterval())) {//该场地分时令
                Calendar cal = Calendar.getInstance();
                int day = cal.get(Calendar.DATE);
                int month = cal.get(Calendar.MONTH) + 1;
                ReserveTimeInterval reserveTimeInterval = reserveTimeIntervalService.findTimeInterval(month, day);//查询系统时间属于哪个时令
                reserveFieldPriceSet.setReserveTimeInterval(reserveTimeInterval);//设置时令
            }
            List<ReserveFieldPriceSet> list = reserveFieldPriceSetDao.findList(reserveFieldPriceSet);
            reserveFieldPriceSet.setReserveTimeInterval(null);//将时令制空
            reserveFieldPriceSetList.addAll(list);
        }
        return reserveFieldPriceSetList;
    }
    /**
     * 查询该time时间点的场地状态
     * @param items
     * @param fieldPriceSet
     * @param time
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

    /**
     *  查询该time时间点的场地 是否有场次票
     * @param ticketList
     * @param fieldPriceSet
     * @param time
     * @return
     */
    private ReserveVenueOrder  haveTicket(List<ReserveVenueOrder> ticketList,ReserveFieldPriceSet fieldPriceSet,String time){
        for (ReserveVenueOrder  ticket: ticketList) {
            if(ticket!=null&& StringUtils.isNoneEmpty(ticket.getId())){
                if (fieldPriceSet.getReserveField().getId().equals(ticket.getReserveField().getId())) {
                    String startTime = ticket.getStartTime();
                    String endTime = ticket.getEndTime();
                    List<String> times = TimeUtils.getTimeSpacListValue(startTime + ":00", endTime + ":00", 30);
                    for (String t : times) {
                        if (time.equals(t)) {
                            return ticket;
                        }
                    }
                }
            }
        }
        return null;
    }

    /**
     * 场地空场审核
     * @param checks 已经审核的
     * @param fieldPriceSet 时间价格设置
     * @param time 时间点
     * @return
     */
    private ReserveVenueEmptyCheck hasCheck(List<ReserveVenueEmptyCheck> checks, ReserveFieldPriceSet fieldPriceSet, String time) {
        for (ReserveVenueEmptyCheck check : checks) {
            if (fieldPriceSet.getReserveField().getId().equals(check.getField().getId())) {
                String startTime = check.getStartTime();
                String endTime = check.getEndTime();
                List<String> times = TimeUtils.getTimeSpacListValue(startTime + ":00", endTime + ":00", 30);
                for (String t : times) {
                    if (time.equals(t)) {
                        return check;
                    }
                }
            }
        }
        return null;
    }
    /**
     * 场地的状态
     * @param reserveFieldPriceSetList 场地的时间价格列表
     * @param venueConsList 场馆订单
     * @param reserveVenueEmptyChecks 空场审核
     * @param times 时间表
     * @return
     */
    private List<FieldPrice> fieldStatus(List<ReserveFieldPriceSet> reserveFieldPriceSetList, List<ReserveVenueConsItem> venueConsList,List<ReserveVenueOrder> venueOrderList,List<ReserveVenueEmptyCheck> reserveVenueEmptyChecks, List<String> times) {
        List<FieldPrice> fieldPriceList = Lists.newLinkedList();
        FieldPrice fieldPrice;
        //遍历场地的时间价格列表
        for (ReserveFieldPriceSet fieldPriceSet : reserveFieldPriceSetList) {
            fieldPrice = new FieldPrice();
            fieldPrice.setVenueId(fieldPriceSet.getReserveVenue().getId());//设置场馆编号
            fieldPrice.setFieldId(fieldPriceSet.getReserveField().getId());//设置场地编号
            fieldPrice.setFieldName(fieldPriceSet.getReserveField().getName());//设置场地名称
            List<TimePrice> timePriceList = fieldPriceSet.getTimePriceList();//获取一个场地 时间和价格 组成的Jason

            List<TimePrice> priceStatusList=fieldPrice.getTimePriceList();
            //遍历所有时间
            for (String time : times) {
                TimePrice timePrice = new TimePrice();
                timePrice.setTime(time);
                //遍历时间价格组成的Jason
                for (TimePrice tp : timePriceList) {
                    String t = tp.getTime();//获取时间
                    Double price = tp.getPrice();//获取价格
                    String hour = time.substring(0, 2);
                    t = t.substring(0, 2);
                    if (hour.equals(t)) {//时间一致
                        timePrice.setPrice(price);//设置价格
                        break;
                    }
                }

                //查询时间time是否已经预定
                ReserveVenueConsItem item = hasReserve(venueConsList, fieldPriceSet, time);
                if (item != null) {//已经预定
                    timePrice.setConsItem(item);//设置订单
                    timePrice.setConsType(item.getConsData().getConsType());//预定人的类型
                    timePrice.setUserName(item.getConsData().getUserName());//预订人
                    timePrice.setStatus(item.getConsData().getReserveType());//订单状态
                } else {
                    timePrice.setStatus("0");//没有预订
                }
                //查询时间time是否场次票
                ReserveVenueOrder ticket=haveTicket(venueOrderList,fieldPriceSet,time);
                if(ticket!=null){
                    timePrice.setTicket(ticket);
                    timePrice.setStatus("11");//场次票已经预订
                }
                //查询时间time是否已经通过空场审核
                ReserveVenueEmptyCheck check = hasCheck(reserveVenueEmptyChecks, fieldPriceSet, time);
                if (check != null) {//已经审核
                    timePrice.setCheck(check);//设置审核
                    timePrice.setUserName(check.getCreateBy().getName());//审核人
                    timePrice.setStatus("0"+check.getCheckStatus());//审核状态
                }
                priceStatusList.add(timePrice);
            }
            fieldPriceList.add(fieldPrice);//添加某个场地的所有价格和状态列表
        }
        return fieldPriceList;
    }
}
