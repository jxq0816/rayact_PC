package com.bra.modules.reserve.service;

import com.bra.common.utils.Collections3;
import com.bra.common.utils.StringUtils;
import com.bra.modules.reserve.dao.*;
import com.bra.modules.reserve.entity.*;
import com.bra.modules.reserve.entity.form.FieldPrice;
import com.bra.modules.reserve.entity.form.TimePrice;
import com.bra.modules.reserve.utils.TimeUtils;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by xiaobin on 16/1/11.
 */
@Service
@Transactional(readOnly = true)
public class ReserveFieldPriceService {

    @Autowired
    private ReserveFieldService reserveFieldService;
    @Autowired
    private ReserveFieldPriceSetDao reserveFieldPriceSetDao;
    @Autowired
    private ReserveFieldHolidayPriceSetDao reserveFieldHolidayPriceSetDao;
    //预定信息
    @Autowired
    private ReserveVenueConsItemDao reserveVenueConsItemDao;
    @Autowired
    private ReserveMemberDao reserveMemberDao;
    @Autowired
    private ReserveStoredcardMemberSetDao reserveStoredcardMemberSetDao;
    @Autowired
    private ReserveFieldRelationService relationService;

    public Double getMemberDiscountRate(ReserveMember member){
        ReserveMember reserveMember = reserveMemberDao.get(member);
        ReserveStoredcardMemberSet set = reserveMember.getStoredcardSet();
        Double rate=null;
        if(set!=null){
            ReserveStoredcardMemberSet storedcardMemberSet = reserveStoredcardMemberSetDao.get(set);
            rate=storedcardMemberSet.getDiscountRate();
        }
        return rate;
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

    /**
     * 退款
     */
    public void refund(String itemId){
       /* ReserveVenueConsItem consItem = reserveVenueConsItemService.get(itemId);
        ReserveVenueConsItem search = new ReserveVenueConsItem();
        consItem.getConsData().setReserveType("3");

        search.setConsData(consItem.getConsData());
        ReserveVenueCons cons = reserveVenueConsService.get(consItem.getConsData().getId());*/
    }

    public Double getPrice(ReserveField field, String consType, Date date, String startTime, String endTime) {
        List<String> timeList = TimeUtils.getTimeSpace(startTime, endTime);
        String weekType = TimeUtils.getWeekType(date);
        ReserveFieldHolidayPriceSet holidayPriceSet = new ReserveFieldHolidayPriceSet();
        holidayPriceSet.setDate(date);
        holidayPriceSet.setConsType(consType);
        holidayPriceSet.setReserveField(field);
        List<ReserveFieldHolidayPriceSet> holidayPriceSetList = reserveFieldHolidayPriceSetDao.findList(holidayPriceSet);

        ReserveFieldPriceSet priceSet = new ReserveFieldPriceSet();
        priceSet.setConsType(consType);
        priceSet.setWeek(weekType);
        priceSet.setReserveField(field);

        List<ReserveFieldPriceSet> priceSets = reserveFieldPriceSetDao.findList(priceSet);
        Double price = 0d;

        List<String> times = Lists.newArrayList();
        if (!Collections3.isEmpty(holidayPriceSetList)) {
            for (String time : timeList) {
                ReserveFieldHolidayPriceSet ps = timeHasHoliday(time, holidayPriceSetList);
                if (ps != null) {
                    price += ps.getConsPrice();
                } else {
                    times.add(time);
                }
            }
            if (!Collections3.isEmpty(times)) {
                for (String time : times) {
                    TimePrice tp = timeHasPriceSet(time, priceSets);
                    if (tp != null) {
                        price += tp.getPrice();
                    }
                }
            }
        } else {
            for (String time : timeList) {
                TimePrice tp = timeHasPriceSet(time, priceSets);
                if (tp != null) {
                    price += tp.getPrice();
                }
            }
        }
        return price;
    }

    private TimePrice timeHasPriceSet(String time, List<ReserveFieldPriceSet> priceSetList) {
        for (ReserveFieldPriceSet priceSet : priceSetList) {
            List<TimePrice> tpList = priceSet.getTimePriceList();
            for (TimePrice tp : tpList) {
                if (tp.getTime().substring(0, tp.getTime().indexOf(":")).equals(time.substring(0, time.indexOf(":")))) {
                    return tp;
                }
            }
        }
        return null;
    }

    private ReserveFieldHolidayPriceSet timeHasHoliday(String time, List<ReserveFieldHolidayPriceSet> holidayPriceSetList) {
        for (ReserveFieldHolidayPriceSet ps : holidayPriceSetList) {
            if (TimeUtils.compare(time, ps.getFieldStartTime()) >= 0 && TimeUtils.compare(time, ps.getFieldEndTime()) < 0) {
                return ps;
            }
        }
        return null;
    }

    /**
     * 根据场馆Id和时间获取场地不同时间段的价格,并查询当前时间是否预定,并标记位已定
     *
     * @param venueId 场馆Id
     * @param date    时间
     * @return
     */
    public List<FieldPrice> findByDate(String venueId, String consType, Date date, List<String> times) {
        List<FieldPrice> fieldPriceList = Lists.newLinkedList();
        //查询场馆中所有的主场
        ReserveField field=new ReserveField();
        ReserveVenue venue=new ReserveVenue();
        venue.setId(venueId);
        field.setReserveVenue(venue);
        List<ReserveField> fullFieldList=reserveFieldService.findFullList(field);

        //查询已预定的信息
        ReserveVenueConsItem reserveVenueCons = new ReserveVenueConsItem();
        reserveVenueCons.setReserveVenue(new ReserveVenue(venueId));
        reserveVenueCons.setConsDate(date);
        reserveVenueCons.setFrequency("all");//所有频率的数据
        reserveVenueCons.setConsWeek(TimeUtils.getWeekOfDate(date));

        ReserveVenueCons venueCons = new ReserveVenueCons();
        venueCons.setReserveType("3");//排除已经取消的
        reserveVenueCons.setConsData(venueCons);
        //查询所有预定的信息(作为本场地的预定标记)
        List<ReserveVenueConsItem> venueConsList = reserveVenueConsItemDao.findListByDate(reserveVenueCons);

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
        List<ReserveFieldPriceSet> reserveFieldPriceSetList =new ArrayList<>();
        for(ReserveField i:fullFieldList){//只处理全场
            reserveFieldPriceSet.setReserveField(i);
            List<ReserveFieldPriceSet> list = reserveFieldPriceSetDao.findList(reserveFieldPriceSet);
            reserveFieldPriceSetList.addAll(list);
        }
        buildTimePrice(fieldPriceList, reserveFieldPriceSetList, venueConsList, times);//获取场地的价格列表

        return fieldPriceList;
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
    //获取场地的时间，价格，状态
    /*
        fieldPriceList:场地的时间价格列表
        reserveFieldPriceSetList:场地价格设置
        venueConsList：场馆订单
        times：时间表
     */
    private void buildTimePrice(List<FieldPrice> fieldPriceList, List<ReserveFieldPriceSet> reserveFieldPriceSetList,
                                List<ReserveVenueConsItem> venueConsList, List<String> times) {

        FieldPrice fieldPrice;
        //遍历场地的时间价格列表
        for (ReserveFieldPriceSet fieldPriceSet : reserveFieldPriceSetList) {
            fieldPrice = new FieldPrice();
            ReserveField field=fieldPriceSet.getReserveField();//获取场地
            fieldPrice.setVenueId(fieldPriceSet.getReserveVenue().getId());//设置场馆编号
            fieldPrice.setFieldId(fieldPriceSet.getReserveField().getId());//设置场地编号
            fieldPrice.setFieldName(fieldPriceSet.getReserveField().getName());//设置场地名称
            List<TimePrice> timePriceList = fieldPriceSet.getTimePriceList();//获取一个场地 时间和价格 组成的Jason

            TimePrice timePrice;
            //遍历所有时间
            for (String time : times) {
                timePrice = new TimePrice();
                timePrice.setTime(time);
                //遍历时间价格组成的Jason
                for(TimePrice tp:timePriceList){
                    String t=tp.getTime();//获取时间
                    Double price=tp.getPrice();//获取价格
                    String hour=time.substring(0,2);
                    t=t.substring(0,2);
                    if(hour.equals(t)){//时间一致
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
                //查询该场地是否有半场
                ReserveFieldRelation relation=new ReserveFieldRelation();
                relation.setParentField(field);
                List<ReserveFieldRelation> list = relationService.findList(relation);
                if(list==null||list.size()==0){
                    fieldPrice.setHaveHalfCourt("0");
                }else{
                    fieldPrice.setHaveHalfCourt("1");//有半场
                    ReserveFieldRelation relationLeft=list.get(0);

                    ReserveField fieldLeft=relationLeft.getChildField();
                    fieldLeft=reserveFieldService.get(fieldLeft);//获取左侧半场的详细信息
                    String fieldLeftId=fieldLeft.getId();
                    String fieldLeftName=fieldLeft.getName();
                    ReserveFieldPriceSet leftFieldSet = new ReserveFieldPriceSet();
                    leftFieldSet.setReserveField(fieldLeft);
                    leftFieldSet=reserveFieldPriceSetDao.findList(leftFieldSet).get(0);
                    List<TimePrice> leftTimePriceList = leftFieldSet.getTimePriceList();//获取左侧半场 时间和价格 组成的Jason
                    FieldPrice leftFieldPrice=buildFieldPrice(leftTimePriceList,venueConsList,leftFieldSet,times);
                    leftFieldPrice.setFieldId(fieldLeftId);//设置场地编号
                    leftFieldPrice.setFieldName(fieldLeftName);//设置场地名称
                    leftFieldPrice.setHaveHalfCourt("0");//无半场
                    fieldPrice.setFieldPriceLeft(leftFieldPrice);//设置左半场

                    ReserveFieldRelation relationRight=list.get(1);
                    ReserveField fieldRight=relationRight.getChildField();
                    fieldRight=reserveFieldService.get(fieldRight);//获取右侧半场的详细信息
                    String fieldRightId=fieldRight.getId();
                    String fieldRightName=fieldRight.getName();
                    ReserveFieldPriceSet rightFieldSet = new ReserveFieldPriceSet();
                    rightFieldSet.setReserveField(fieldRight);
                    rightFieldSet=reserveFieldPriceSetDao.findList(rightFieldSet).get(0);
                    List<TimePrice> rightTimePriceList = rightFieldSet.getTimePriceList();//获取右侧半场 时间和价格 组成的Jason
                    FieldPrice rightFieldPrice=buildFieldPrice(rightTimePriceList,venueConsList,rightFieldSet,times);
                    rightFieldPrice.setFieldId(fieldRightId);//设置场地编号
                    rightFieldPrice.setFieldName(fieldRightName);//设置场地名称
                    rightFieldPrice.setHaveHalfCourt("0");//无半场
                    fieldPrice.setFieldPriceRight(rightFieldPrice);//设置右半场

                }
                fieldPrice.getTimePriceList().add(timePrice);
            }
            fieldPriceList.add(fieldPrice);
        }
    }
    /*
    查询半场的 时间 价格 状态
     */
    private FieldPrice buildFieldPrice(List<TimePrice> LeftTimePriceList, List<ReserveVenueConsItem> venueConsList,ReserveFieldPriceSet setLeft, List<String> times) {

        FieldPrice leftFieldPrice=new FieldPrice();
        for (String i : times) {
            TimePrice LeftTimePrice = new TimePrice();
            LeftTimePrice.setTime(i);
            //遍历左半场 时间价格组成的Jason
            for (TimePrice j : LeftTimePriceList) {
                String t = j.getTime();
                Double price = j.getPrice();
                String hour = i.substring(0, 2);
                t = t.substring(0, 2);
                if (hour.equals(t)) {
                    LeftTimePrice.setPrice(price);
                    break;
                }
            }
            //查询左半场在时间i是否已经预定
            ReserveVenueConsItem cons = hasReserve(venueConsList, setLeft, i);
            if (cons != null) {//已经预定
                LeftTimePrice.setConsItem(cons);
                LeftTimePrice.setConsType(cons.getConsData().getConsType());
                LeftTimePrice.setUserName(cons.getConsData().getUserName());
                LeftTimePrice.setStatus(cons.getConsData().getReserveType());
            } else {
                LeftTimePrice.setStatus("0");
            }
            leftFieldPrice.getTimePriceList().add(LeftTimePrice);
        }
        return leftFieldPrice;
    }
}
