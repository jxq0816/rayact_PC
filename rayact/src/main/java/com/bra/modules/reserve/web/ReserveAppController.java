package com.bra.modules.reserve.web;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bra.common.utils.StringUtils;
import com.bra.common.web.BaseController;
import com.bra.modules.reserve.entity.*;
import com.bra.modules.reserve.entity.form.FieldPrice;
import com.bra.modules.reserve.entity.form.TimePrice;
import com.bra.modules.reserve.service.ReserveAppFieldPriceService;
import com.bra.modules.reserve.service.ReserveAppVenueConsService;
import com.bra.modules.reserve.service.ReserveVenueConsItemService;
import com.bra.modules.reserve.service.ReserveVenueService;
import com.bra.modules.reserve.utils.TimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 场地预定管理
 * Created by jiangxingqi on 16/1/5.
 */
@Controller
@RequestMapping(value = "${adminPath}/app/reserve/field")
public class ReserveAppController extends BaseController {

    @Autowired
    private ReserveVenueService reserveVenueService;
    //APP场地价格service
    @Autowired
    private ReserveAppFieldPriceService reserveAppFieldPriceService;
    //订单详情service
    @Autowired
    private ReserveVenueConsItemService reserveVenueConsItemService;
    //订单service
    @Autowired
    private ReserveAppVenueConsService reserveAppVenueConsService;

    //场地状态界面
    @RequestMapping(value = "main")
    public String main(Date consDate, String venueId, String projectId, String phone, Model model) {
        if (consDate == null) {
            consDate = new Date();
        }
        if (StringUtils.isNoneEmpty(venueId)) {
            List<String> times = new ArrayList<>();
            ReserveVenue venue = reserveVenueService.get(venueId);
            String startTime = venue.getStartTime();
            String endTime = venue.getEndTime();
            if (StringUtils.isEmpty(startTime)) {
                startTime = "06:00:00";
            }
            if (StringUtils.isEmpty(endTime)) {
                endTime = "00:30:00";
            }
            times.addAll(TimeUtils.getTimeSpacListValue(startTime, endTime, 30));
            if (StringUtils.isNoneEmpty(venueId)) {
                //场地价格
                List<FieldPrice> venueFieldPriceList = reserveAppFieldPriceService.findByDate(venueId, projectId, "1", consDate, times);
                for (FieldPrice i : venueFieldPriceList) {
                    i.setHaveFullCourt(null);
                    i.setHaveHalfCourt(null);
                    FieldPrice full = i.getFieldPriceFull();//全场的状态
                    FieldPrice left = i.getFieldPriceLeft();
                    FieldPrice right = i.getFieldPriceRight();
                    for (TimePrice j : i.getTimePriceList()) {
                        Date systemTime = new Date();
                        String time = j.getTime();//当前场地的时间
                        String startTimeSub = time.substring(0, 5);
                        if ("00:00".endsWith(startTimeSub)) {
                            Calendar c = Calendar.getInstance();
                            c.setTime(systemTime);
                            int day = c.get(Calendar.DATE);
                            c.set(Calendar.DATE, day - 1);
                            systemTime = c.getTime();
                        }
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        String date = dateFormat.format(consDate);
                        startTimeSub = TimeUtils.earlyMorningFormat(startTimeSub);
                        startTimeSub = date + " " + startTimeSub;//场地时间


                        SimpleDateFormat myFmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        String sysTime = myFmt.format(systemTime);//系统时间
                        if (startTimeSub.compareTo(sysTime) < 0) {
                            j.setStatus("1");
                        } else if ("0".equals(j.getStatus())) {
                            if (StringUtils.isNoneEmpty(time)) {
                                if (full != null) {
                                    for (TimePrice k : full.getTimePriceList()) {
                                        String fullTime = k.getTime();
                                        if (time.endsWith(fullTime) && "1".equals(k.getStatus())) {
                                            j.setStatus("1");//全场已占用，半场不可用
                                            break;
                                        }
                                    }
                                }
                                if (left != null) {
                                    if ("0".equals(j.getStatus())) {
                                        for (TimePrice k : left.getTimePriceList()) {
                                            String leftTime = k.getTime();
                                            if (time.endsWith(leftTime) && "1".equals(k.getStatus())) {
                                                j.setStatus("1");//半场已占用，全场不可用
                                                break;
                                            }
                                        }
                                    }
                                }
                                if (right != null) {
                                    if ("0".equals(j.getStatus())) {
                                        for (TimePrice k : right.getTimePriceList()) {
                                            String rightTime = k.getTime();
                                            if (time.endsWith(rightTime) && "1".equals(k.getStatus())) {
                                                j.setStatus("1");//半场已占用，全场不可用
                                                break;
                                            }
                                        }
                                    }
                                }
                            }//该时间段的验证结束
                        }//状态 更新结束
                    }
                }
                model.addAttribute("venueFieldPriceList", venueFieldPriceList);
                model.addAttribute("times", times);
                SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
                model.addAttribute("consDate", fmt.format(consDate));
                model.addAttribute("venueId", venueId);
                model.addAttribute("phone", phone);
            }
        }
        return "reserve/saleField/reserveAppField";
    }

    /**
     * 结算订单
     *
     * @param
     * @return
     */
    @RequestMapping(value = "saveSettlement")
    @ResponseBody
    public Map saveSettlement(ReserveVenueCons order, String payType,
                              Double consPrice,
                              Double memberCardInput,
                              Double bankCardInput,
                              Double weiXinInput,
                              Double aliPayInput,
                              Double couponInput) {
        System.out.println("开始支付" + order.getId());
        Map map = new HashMap<>();
        Double inputSum = 0.0;
        if (memberCardInput != null) {
            inputSum += bankCardInput;
        }
        if (weiXinInput != null) {
            inputSum += weiXinInput;
        }
        if (aliPayInput != null) {
            inputSum += aliPayInput;
        }
        if (couponInput != null) {
            inputSum += couponInput;
        }
        if (consPrice.equals(inputSum) == false) {
            map.put("result", 4);
            map.put("msg", "多方式付款总和不等于实付金额");
            return map;
        }

        Boolean bool = reserveAppVenueConsService.saveSettlement(order, payType, consPrice,
                memberCardInput, bankCardInput, weiXinInput, aliPayInput, couponInput);
        if (bool) {
            map.put("result", 1);
            map.put("msg", "订单结算成功");
        } else {
            map.put("result", 2);
            map.put("msg", "订单已结算，不可重复结算");
        }
        return map;
    }

    /**
     * 订单保存
     *
     * @param reserveJson
     * @param projectId
     * @param username
     * @param phone
     * @return
     */
    @RequestMapping(value = "reservation")
    @ResponseBody
    public Map reservation(String reserveJson, String projectId, String username, String phone) {
        String reserve = reserveJson.replaceAll("&quot;", "\"");
        JSONObject object = JSON.parseObject(reserve);
        String date = (String) object.get("consDate");
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date consDate = null;
        try {
            consDate = format.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        List<Map> list = (List<Map>) object.get("venueConsList");
        List<ReserveVenueConsItem> items = new ArrayList<>();
        for (Map i : list) {
            ReserveVenueConsItem item = new ReserveVenueConsItem();
            ReserveField field = new ReserveField();
            String filedId = (String) i.get("reserveFieldId");
            field.setId(filedId);
            String filedName = (String) i.get("reserveFieldName");
            field.setName(filedName);
            item.setReserveField(field);

            String price = (String) i.get("orderPrice");
            Double orderPrice = Double.valueOf(price);
            item.setOrderPrice(orderPrice);

            String startTime = (String) i.get("startTime");
            item.setStartTime(startTime);
            String endTime = (String) i.get("endTime");
            item.setEndTime(endTime);
            items.add(item);
        }
        boolean bool = true;//时间段是否可用
        for (ReserveVenueConsItem i : items) {//订单详情
            String startTime = i.getStartTime();
            String endTime = i.getEndTime();
            ReserveField field = i.getReserveField();//场地
            //遍历该日期区间 的场地是否有预订
            bool = reserveVenueConsItemService.checkReserveTime(consDate, field, startTime, endTime);
            if (bool == false) {
                break;//该时间段不能使用，跳出循环
            }
        }
        Map map = new HashMap<>();
        map.put("bool", String.valueOf(bool));
        if (bool == true) {
            ReserveVenueCons reserveVenueCons = new ReserveVenueCons();
            reserveVenueCons.setUserName(username);
            reserveVenueCons.setConsMobile(phone);
            reserveVenueCons.setProject(new ReserveProject(projectId));//该字段用于PC统计项目收入
            String reserveVenueId = (String) object.get("reserveVenueId");
            ReserveVenue venue = new ReserveVenue(reserveVenueId);
            reserveVenueCons.setReserveVenue(venue);
            reserveVenueCons.setReserveType(ReserveVenueCons.RESERVATION);//已预定
            reserveVenueCons.setConsDate(consDate);
            reserveVenueCons.setVenueConsList(items);
            map.putAll(reserveAppVenueConsService.saveOrder(reserveVenueCons));//保存预订信息

        }
        return map;
    }

    @RequestMapping(value = "orderDetail")
    @ResponseBody
    /**
     *订单详情
     * @param orderId
     * @return
     */
    public String orderDetail(String orderId) {
        String rs = null;
        if (StringUtils.isNoneEmpty(orderId)) {
            Map order = reserveAppVenueConsService.detail(orderId);
            rs = JSON.toJSONString(order);
        }
        return rs;
    }

    @RequestMapping(value = "orderList")
    @ResponseBody
    /**
     *订单详情
     * @param orderId
     * @return
     */
    public String orderList(String reserveType, String phone) {
        List<Map> orderList = null;
        if (StringUtils.isNoneEmpty(phone)) {
            orderList = reserveAppVenueConsService.orderList(reserveType, phone);
        }
        return JSON.toJSONString(orderList);
    }

    @RequestMapping(value = "checkUnPayOrder")
    @ResponseBody
    /**
     *订单详情
     * @param orderId
     * @return
     */
    public String checkUnPayOrder(String phone) {
        List<Map> orderList = null;
        String orderId = null;
        if (StringUtils.isNoneEmpty(phone)) {
            orderList = reserveAppVenueConsService.orderList("1", phone);
        }
        if (orderList != null && orderList.size() != 0) {
            Map map = orderList.get(0);
            orderId = (String) map.get("orderId");
        }
        return orderId;
    }

    @RequestMapping(value = "cancelOrder")
    @ResponseBody
    /**
     *订单详情
     * @param orderId
     * @return
     */
    public Map cancelOrder(String orderId) {
        ReserveVenueCons venueCons = reserveAppVenueConsService.get(orderId);
        Map map = new HashMap<>();
        if (venueCons == null) {
            map.put("rs", 0);
            map.put("msg", "该订单不存在");
            return map;
        }
        String reserveType = venueCons.getReserveType();
        if ("4".equals(reserveType)) {
            map.put("rs", 4);
            map.put("msg", "该订单已结算，不可取消");
            return map;
        }
        if (venueCons != null) {
            reserveAppVenueConsService.cancelOrder(venueCons);
            map.put("rs", 1);
            map.put("msg", "订单取消成功");
        }
        return map;
    }

    @RequestMapping(value = "checkUserOrder")
    @ResponseBody
    /**
     *检测用户是否有未付款的订单
     * @param orderId
     * @return
     */
    public String checkUserOrder(String phone) {
        List<Map> mapList = reserveAppVenueConsService.checkUserUnpaidOrder(phone);
        return JSON.toJSONString(mapList);
    }
}
