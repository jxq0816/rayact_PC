package com.bra.modules.reserve.service;

import com.bra.common.utils.StringUtils;
import com.bra.modules.reserve.dao.ReserveFieldPriceSetDao;
import com.bra.modules.reserve.dao.ReserveVenueConsItemDao;
import com.bra.modules.reserve.entity.*;
import com.bra.modules.reserve.entity.form.FieldPrice;
import com.bra.modules.reserve.entity.form.TimePrice;
import com.bra.modules.reserve.utils.TimeUtils;
import com.bra.modules.sys.entity.User;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by xiaobin on 16/1/11.
 */
@Service
@Transactional(readOnly = true)
public class ReserveMobileFieldPriceService {

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
    private ReserveFieldRelationService relationService;
    @Autowired
    private ReserveVenueEmptyCheckService reserveVenueEmptyCheckService;

    private void setWeek(ReserveFieldPriceSet reserveFieldPriceSet, Date date) {
        String week = TimeUtils.getWeekOfDate(date);
        if ("周六".equals(week)) {
            reserveFieldPriceSet.setWeek("2");
        } else if ("周日".equals(week)) {
            reserveFieldPriceSet.setWeek("3");
        } else {
            reserveFieldPriceSet.setWeek("1");
        }
    }

    /**
     *
     * @param venueId
     * @param consType
     * @param date
     * @param times
     * @param user 手机端 登陆的用户
     * @return
     */
    public List<FieldPrice> findByDateForMobile(String venueId, String consType, Date date, List<String> times,User user) {
        List<FieldPrice> fieldPriceList = Lists.newLinkedList();
        //查询场馆中所有场地
        ReserveField field = new ReserveField();
        ReserveVenue venue = new ReserveVenue();
        venue.setId(venueId);
        field.setReserveVenue(venue);
        field.setTenantId(user.getCompany().getId());
        List<ReserveField> fieldList = reserveFieldService.findList(field);

        //查询已预定的信息
        ReserveVenueConsItem reserveVenueCons = new ReserveVenueConsItem();
        reserveVenueCons.setReserveVenue(new ReserveVenue(venueId));
        reserveVenueCons.setConsDate(date);
        reserveVenueCons.setFrequency("all");//所有频率的数据
        reserveVenueCons.setConsWeek(TimeUtils.getWeekOfDate(date));

        ReserveVenueCons venueCons = new ReserveVenueCons();
        reserveVenueCons.setConsData(venueCons);
        reserveVenueCons.setTenantId(user.getCompany().getId());
        //查询所有预定的信息(作为本场地的预定标记)
        List<ReserveVenueConsItem> venueConsList = reserveVenueConsItemDao.findListByDate(reserveVenueCons);
        //查询已审核的信息
        ReserveVenueEmptyCheck reserveVenueEmptyCheck = new ReserveVenueEmptyCheck();
        reserveVenueEmptyCheck.setVenue(new ReserveVenue(venueId));
        reserveVenueEmptyCheck.setCheckDate(date);
        List<ReserveVenueEmptyCheck> emptyChecks = reserveVenueEmptyCheckService.findList(reserveVenueEmptyCheck);
        //查询价格
        ReserveFieldPriceSet reserveFieldPriceSet = new ReserveFieldPriceSet();
        reserveFieldPriceSet.setReserveVenue(new ReserveVenue(venueId));

        setWeek(reserveFieldPriceSet, date);
        //会员类型(1:散客,2:会员)
        if (StringUtils.isNotBlank(consType)) {
            reserveFieldPriceSet.setConsType(consType);
        }
        String weekType = TimeUtils.getWeekType(date);//获得当天属于周几
        reserveFieldPriceSet.setWeek(weekType);
        List<ReserveFieldPriceSet> reserveFieldPriceSetList = new ArrayList<>();
        for (ReserveField i : fieldList) {//处理场地
            reserveFieldPriceSet.setReserveField(i);
            if ("1".equals(i.getIsTimeInterval())) {//该场地分时令
                Calendar cal = Calendar.getInstance();
                int day = cal.get(Calendar.DATE);
                int month = cal.get(Calendar.MONTH) + 1;
                ReserveTimeInterval reserveTimeInterval = reserveTimeIntervalService.findTimeIntervalMobile(month, day,user);//查询系统时间属于哪个时令
                reserveFieldPriceSet.setReserveTimeInterval(reserveTimeInterval);
            }
            reserveFieldPriceSet.setTenantId(user.getCompany().getId());
            List<ReserveFieldPriceSet> list = reserveFieldPriceSetDao.findList(reserveFieldPriceSet);
            reserveFieldPriceSet.setReserveTimeInterval(null);//将时令制空
            reserveFieldPriceSetList.addAll(list);
        }
        buildTimePriceMobile(fieldPriceList, reserveFieldPriceSetList, venueConsList,emptyChecks, times,user);//获取场地的价格列表

        return fieldPriceList;
    }

    /**
     * 查询场地的状态
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
     * 查询场地是否已经通过审核
     * @param checks
     * @param fieldPriceSet
     * @param time
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
     * 获取场地的时间，价格，状态 for mobile
     * @param fieldPriceList
     * @param reserveFieldPriceSetList
     * @param venueConsList
     * @param reserveVenueEmptyChecks
     * @param times
     * @param user 手机端登陆的用户
     */
    private void buildTimePriceMobile(List<FieldPrice> fieldPriceList, List<ReserveFieldPriceSet> reserveFieldPriceSetList,
                                List<ReserveVenueConsItem> venueConsList,List<ReserveVenueEmptyCheck> reserveVenueEmptyChecks, List<String> times,User user) {

        FieldPrice fieldPrice;
        //遍历场地的时间价格列表
        for (ReserveFieldPriceSet fieldPriceSet : reserveFieldPriceSetList) {
            fieldPrice = new FieldPrice();
            ReserveField field = fieldPriceSet.getReserveField();//获取场地
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
                //查询时间time是否已经通过空场审核
                ReserveVenueEmptyCheck check = hasCheck(reserveVenueEmptyChecks, fieldPriceSet, time);
                if (check != null) {//已经审核
                    timePrice.setCheck(check);//设置审核
                    timePrice.setUserName(check.getCreateBy().getName());//审核人
                    timePrice.setStatus("0"+check.getCheckStatus());//审核状态
                }
                //查询该场地是否有半场
                ReserveFieldRelation relation = new ReserveFieldRelation();
                relation.setParentField(field);
                relation.setTenantId(user.getCompany().getId());
                List<ReserveFieldRelation> list = relationService.findList(relation);
                if (list == null || list.size() == 0) {
                    fieldPrice.setHaveHalfCourt("0");//没有半场
                    //没有半场，查找是否有全场
                    ReserveFieldRelation r = new ReserveFieldRelation();
                    r.setChildField(field);
                    r.setTenantId(user.getCompany().getId());
                    List<ReserveFieldRelation> fullRelationList = relationService.findList(r);
                    if (fullRelationList == null || fullRelationList.size() == 0) {
                        fieldPrice.setHaveFullCourt("0");//没有全场
                    } else {
                        fieldPrice.setHaveFullCourt("0");//有全场
                        for (ReserveFieldRelation i : fullRelationList) {
                            ReserveField parentFiled = i.getParentField();
                            parentFiled = reserveFieldService.get(parentFiled);//获取全场的详细信息
                            String fieldParentId = parentFiled.getId();
                            String fieldParentName = parentFiled.getName();
                            ReserveFieldPriceSet fullFieldSet = new ReserveFieldPriceSet();
                            fullFieldSet.setReserveField(parentFiled);
                            fullFieldSet.setTenantId(user.getCompany().getId());
                            List<ReserveFieldPriceSet> parentList = reserveFieldPriceSetDao.findList(fullFieldSet);
                            if (parentList != null && parentList.size() != 0) {
                                ReserveFieldPriceSet j = parentList.get(0);//这里会有6个时间价格的设置，只需要取一个，目的是获取时间段，然后根据时间段和场地，来查询是否可预订
                                List<TimePrice> fullTimePriceList = j.getTimePriceList();//获取全场 时间和价格 组成的Jason
                                FieldPrice fullFieldPrice = buildFieldPrice(fullTimePriceList, venueConsList,reserveVenueEmptyChecks, fullFieldSet, times);// 查询全场的 时间 价格 状态
                                fullFieldPrice.setFieldId(fieldParentId);//设置场地编号
                                fullFieldPrice.setFieldName(fieldParentName);//设置场地名称
                                fullFieldPrice.setHaveHalfCourt("0");//无半场
                                fieldPrice.setFieldPriceFull(fullFieldPrice);//设置全场
                            }
                        }
                    }
                } else {
                    fieldPrice.setHaveHalfCourt("1");//有半场，即是全场
                    if (list.size() >= 1) {
                        ReserveFieldRelation relationLeft = list.get(0);
                        ReserveField fieldLeft = relationLeft.getChildField();
                        fieldLeft = reserveFieldService.get(fieldLeft);//获取左侧半场的详细信息
                        String fieldLeftId = fieldLeft.getId();
                        String fieldLeftName = fieldLeft.getName();
                        ReserveFieldPriceSet leftFieldSet = new ReserveFieldPriceSet();
                        leftFieldSet.setReserveField(fieldLeft);
                        leftFieldSet.setTenantId(user.getCompany().getId());
                        List<ReserveFieldPriceSet> leftList = reserveFieldPriceSetDao.findList(leftFieldSet);
                        if (leftList != null && leftList.size() != 0) {
                            leftFieldSet = reserveFieldPriceSetDao.findList(leftFieldSet).get(0);
                            List<TimePrice> leftTimePriceList = leftFieldSet.getTimePriceList();//获取左侧半场 时间和价格 组成的Jason
                            FieldPrice leftFieldPrice = buildFieldPrice(leftTimePriceList, venueConsList,reserveVenueEmptyChecks, leftFieldSet, times);// 查询半场的 时间 价格 状态
                            leftFieldPrice.setFieldId(fieldLeftId);//设置场地编号
                            leftFieldPrice.setFieldName(fieldLeftName);//设置场地名称
                            leftFieldPrice.setHaveHalfCourt("0");//无半场
                            fieldPrice.setFieldPriceLeft(leftFieldPrice);//设置左半场
                        }
                    }
                    if (list.size() >= 2) {
                        ReserveFieldRelation relationRight = list.get(1);
                        ReserveField fieldRight = relationRight.getChildField();
                        fieldRight = reserveFieldService.get(fieldRight);//获取右侧半场的详细信息
                        String fieldRightId = fieldRight.getId();
                        String fieldRightName = fieldRight.getName();
                        ReserveFieldPriceSet rightFieldSet = new ReserveFieldPriceSet();
                        rightFieldSet.setReserveField(fieldRight);
                        rightFieldSet.setTenantId(user.getCompany().getId());
                        List<ReserveFieldPriceSet> rightList = reserveFieldPriceSetDao.findList(rightFieldSet);
                        if (rightList != null && rightList.size() != 0) {//如果设置价格不为空
                            rightFieldSet = reserveFieldPriceSetDao.findList(rightFieldSet).get(0);
                            List<TimePrice> rightTimePriceList = rightFieldSet.getTimePriceList();//获取右侧半场 时间和价格 组成的Jason
                            FieldPrice rightFieldPrice = buildFieldPrice(rightTimePriceList, venueConsList,reserveVenueEmptyChecks, rightFieldSet, times);// 查询半场的 时间 价格 状态
                            rightFieldPrice.setFieldId(fieldRightId);//设置场地编号
                            rightFieldPrice.setFieldName(fieldRightName);//设置场地名称
                            rightFieldPrice.setHaveHalfCourt("0");//无半场
                            fieldPrice.setFieldPriceRight(rightFieldPrice);//设置右半场
                        }
                    }
                }
                fieldPrice.getTimePriceList().add(timePrice);
            }
            fieldPriceList.add(fieldPrice);
        }
    }

    /**
     * 查询场地的状态和价格
     * @param timePriceList
     * @param venueConsList
     * @param checks
     * @param setLeft
     * @param times
     * @return
     */
    private FieldPrice buildFieldPrice(List<TimePrice> timePriceList, List<ReserveVenueConsItem> venueConsList,List<ReserveVenueEmptyCheck> checks, ReserveFieldPriceSet setLeft, List<String> times) {

        FieldPrice fieldPrice = new FieldPrice();
        for (String i : times) {
            TimePrice timePrice = new TimePrice();
            timePrice.setTime(i);
            //遍历半场 时间价格组成的Json
            for (TimePrice j : timePriceList) {
                String t = j.getTime();
                Double price = j.getPrice();
                String hour = i.substring(0, 2);
                t = t.substring(0, 2);
                if (hour.equals(t)) {
                    timePrice.setPrice(price);
                    break;
                }
            }
            //查询半场在时间i是否已经预定
            ReserveVenueConsItem item = hasReserve(venueConsList, setLeft, i);
            if (item != null) {//已经预定
                timePrice.setConsItem(item);
                timePrice.setConsType(item.getConsData().getConsType());
                timePrice.setUserName(item.getConsData().getUserName());
                timePrice.setStatus(item.getConsData().getReserveType());
            } else {
                timePrice.setStatus("0");
            }
            //查询时间time是否已经通过空场审核
            ReserveVenueEmptyCheck check = hasCheck(checks, setLeft, i);
            if (check != null) {//已经审核
                timePrice.setCheck(check);//设置审核
                timePrice.setUserName(check.getCreateBy().getName());//审核人
                timePrice.setStatus("0"+check.getCheckStatus());//审核状态
            }
            fieldPrice.getTimePriceList().add(timePrice);
        }
        return fieldPrice;
    }
}
