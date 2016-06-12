package com.bra.modules.reserve.service;

import com.bra.common.utils.StringUtils;
import com.bra.modules.reserve.dao.ReserveFieldPriceSetDao;
import com.bra.modules.reserve.dao.ReserveVenueConsItemDao;
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
 * Created by xiaobin on 16/1/11.
 */
@Service
@Transactional(readOnly = true)
public class ReserveAppFieldPriceService {

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
     * 根据场馆Id和时间获取场地不同时间段的价格,并查询当前时间是否预定,并标记位已定
     *
     * @param venueId 场馆Id
     * @param date    时间
     * @param consType 会员类型(1:散客,2:会员)
     * @return
     */
    public List<FieldPrice> findByDate(String venueId,String projectId, String consType, Date date, List<String> times) {
        List<FieldPrice> fieldPriceList = Lists.newLinkedList();
        //查询场馆中所有场地
        ReserveField field = new ReserveField();
        ReserveVenue venue = new ReserveVenue();
        venue.setId(venueId);
        ReserveProject project = new ReserveProject();
        project.setId(projectId);
        field.setReserveProject(project);
        field.setReserveVenue(venue);
        List<ReserveField> fieldList = reserveFieldService.findList(field);

        //查询已预定的信息
        ReserveVenueConsItem reserveVenueConsItem = new ReserveVenueConsItem();
        reserveVenueConsItem.setReserveVenue(venue);
        reserveVenueConsItem.setConsDate(date);

        //查询价格
        ReserveFieldPriceSet reserveFieldPriceSet = new ReserveFieldPriceSet();
        reserveFieldPriceSet.setReserveVenue(new ReserveVenue(venueId));
        //会员类型(1:散客,2:会员)
        if (StringUtils.isNotBlank(consType)) {
            reserveFieldPriceSet.setConsType(consType);
        }
        String weekType = TimeUtils.getWeekType(date);//获得当天属于周几
        reserveFieldPriceSet.setWeek(weekType);
        List<ReserveFieldPriceSet> reserveFieldPriceSetList = new ArrayList<ReserveFieldPriceSet>();
        //设置场地的时令
        for (ReserveField i : fieldList) {
            reserveFieldPriceSet.setReserveField(i);
            if ("1".equals(i.getIsTimeInterval())) {//该场地分时令
                Calendar cal = Calendar.getInstance();
                int day = cal.get(Calendar.DATE);
                int month = cal.get(Calendar.MONTH) + 1;
                ReserveTimeInterval reserveTimeInterval = reserveTimeIntervalService.findTimeInterval(month, day);//查询系统时间属于哪个时令
                reserveFieldPriceSet.setReserveTimeInterval(reserveTimeInterval);
            }
            List<ReserveFieldPriceSet> list = reserveFieldPriceSetDao.findList(reserveFieldPriceSet);
            reserveFieldPriceSet.setReserveTimeInterval(null);//将时令制空
            reserveFieldPriceSetList.addAll(list);
        }
        //查询所有预定的信息(作为本场地的预定标记)
        reserveVenueConsItem.setTenantId(null);//APP 预订的没有集团标识
        List<ReserveVenueConsItem> venueConsList = reserveVenueConsItemDao.findListByDate(reserveVenueConsItem);
        buildTimePrice(fieldPriceList, reserveFieldPriceSetList, venueConsList, times);//获取场地的价格列表，并查询当前时间是否预定,并标记位已定

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
            ReserveField field = fieldPriceSet.getReserveField();//获取场地
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
                    Double price = tp.getPrice()/2;//获取后端是按照一小时设定的价格，前端是半个小时
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
                    timePrice.setStatus(item.getConsData().getReserveType());//订单状态
                } else {
                    timePrice.setStatus("0");//没有预订
                }
                //查询该场地是否有半场,有半场必然是全场
                ReserveFieldRelation relation = new ReserveFieldRelation();
                relation.setParentField(field);
                List<ReserveFieldRelation> list = relationService.findList(relation);
                //全场 半场之间的关系
                if (list == null || list.size() == 0) { //没有半场，查找是否有全场
                    ReserveFieldRelation r = new ReserveFieldRelation();
                    r.setChildField(field);
                    List<ReserveFieldRelation> fullRelationList = relationService.findList(r);
                    if (fullRelationList != null && fullRelationList.size() != 0) {
                        //有全场
                        for (ReserveFieldRelation i : fullRelationList) {
                            ReserveField parentFiled = i.getParentField();
                            String fieldParentId = parentFiled.getId();
                            ReserveFieldPriceSet fullFieldSet = new ReserveFieldPriceSet();
                            fullFieldSet.setReserveField(parentFiled);
                            List<ReserveFieldPriceSet> parentList = reserveFieldPriceSetDao.findList(fullFieldSet);
                            if (parentList != null && parentList.size() != 0) {
                                ReserveFieldPriceSet j = parentList.get(0);//这里会有6个时间价格的设置，只需要取一个，目的是获取时间段，然后根据时间段和场地，来查询是否可预订
                                List<TimePrice> fullTimePriceList = j.getTimePriceList();//获取全场 时间和价格 组成的Jason
                                FieldPrice fullFieldPrice = buildFieldPrice(fullTimePriceList, venueConsList, fullFieldSet, times);// 查询全场的 时间 价格 状态
                                fieldPrice.setFieldPriceFull(fullFieldPrice);//设置全场
                            }
                        }
                    }
                } else {
                    //左半场
                    if (list.size() >= 1) {
                        ReserveFieldRelation relationLeft = list.get(0);
                        ReserveField fieldLeft = relationLeft.getChildField();
                        fieldLeft = reserveFieldService.get(fieldLeft);//获取左侧半场的详细信息
                        String fieldLeftId = fieldLeft.getId();
                        String fieldLeftName = fieldLeft.getName();
                        ReserveFieldPriceSet leftFieldSet = new ReserveFieldPriceSet();
                        leftFieldSet.setReserveField(fieldLeft);
                        List<ReserveFieldPriceSet> leftList = reserveFieldPriceSetDao.findList(leftFieldSet);
                        if (leftList != null && leftList.size() != 0) {
                            leftFieldSet = reserveFieldPriceSetDao.findList(leftFieldSet).get(0);
                            List<TimePrice> leftTimePriceList = leftFieldSet.getTimePriceList();//获取左侧半场 时间和价格 组成的Jason
                            FieldPrice leftFieldPrice = buildFieldPrice(leftTimePriceList, venueConsList, leftFieldSet, times);// 查询半场的 时间 价格 状态
                            leftFieldPrice.setFieldId(fieldLeftId);//设置场地编号
                            fieldPrice.setFieldPriceLeft(leftFieldPrice);//设置左半场
                        }
                    }
                    //右半场
                    if (list.size() >= 2) {
                        ReserveFieldRelation relationRight = list.get(1);
                        ReserveField fieldRight = relationRight.getChildField();
                        fieldRight = reserveFieldService.get(fieldRight);//获取右侧半场的详细信息
                        String fieldRightId = fieldRight.getId();
                        String fieldRightName = fieldRight.getName();
                        ReserveFieldPriceSet rightFieldSet = new ReserveFieldPriceSet();
                        rightFieldSet.setReserveField(fieldRight);
                        List<ReserveFieldPriceSet> rightList = reserveFieldPriceSetDao.findList(rightFieldSet);
                        if (rightList != null && rightList.size() != 0) {//如果设置价格不为空
                            rightFieldSet = reserveFieldPriceSetDao.findList(rightFieldSet).get(0);
                            List<TimePrice> rightTimePriceList = rightFieldSet.getTimePriceList();//获取右侧半场 时间和价格 组成的Jason
                            FieldPrice rightFieldPrice = buildFieldPrice(rightTimePriceList, venueConsList, rightFieldSet, times);// 查询半场的 时间 价格 状态
                            rightFieldPrice.setFieldId(fieldRightId);//设置场地编号
                            fieldPrice.setFieldPriceRight(rightFieldPrice);//设置右半场
                        }
                    }
                }//全场半场关系处理完毕
                priceStatusList.add(timePrice);
            }
            fieldPriceList.add(fieldPrice);//添加某个场地的所有价格和状态列表
        }
    }
    /*
    查询半场的 时间 价格 状态
     */
    private FieldPrice buildFieldPrice(List<TimePrice> timePriceList, List<ReserveVenueConsItem> venueConsList, ReserveFieldPriceSet setLeft, List<String> times) {

        FieldPrice fieldPrice = new FieldPrice();
        for (String i : times) {
            TimePrice timePrice = new TimePrice();
            timePrice.setTime(i);
            //遍历半场 时间价格组成的Jason
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
            //查询左半场在时间i是否已经预定
            ReserveVenueConsItem cons = hasReserve(venueConsList, setLeft, i);
            if (cons != null) {//已经预定
                timePrice.setStatus(cons.getConsData().getReserveType());
            } else {
                timePrice.setStatus("0");
            }
            fieldPrice.getTimePriceList().add(timePrice);
        }
        return fieldPrice;
    }
}
