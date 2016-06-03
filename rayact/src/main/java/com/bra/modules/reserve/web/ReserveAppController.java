package com.bra.modules.reserve.web;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bra.common.utils.StringUtils;
import com.bra.common.web.BaseController;
import com.bra.modules.reserve.entity.ReserveField;
import com.bra.modules.reserve.entity.ReserveVenue;
import com.bra.modules.reserve.entity.ReserveVenueCons;
import com.bra.modules.reserve.entity.ReserveVenueConsItem;
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
    public String main(Date consDate, String venueId,String projectId,Model model) throws ParseException {
        if (consDate == null) {
            consDate = new Date();
        }
        List<String> times=new ArrayList<>();
        ReserveVenue venue=reserveVenueService.get(venueId);
        String startTime=venue.getStartTime();
        String endTime=venue.getEndTime();
        if(StringUtils.isEmpty(startTime)){
            startTime="06:00:00";
        }
        if(StringUtils.isEmpty(endTime)){
            endTime="00:30:00";
        }
        times.addAll(TimeUtils.getTimeSpacListValue(startTime, endTime, 30));
        if (StringUtils.isNoneEmpty(venueId)) {
            //场地价格
            List<FieldPrice> venueFieldPriceList = reserveAppFieldPriceService.findByDate(venueId,projectId, "1", consDate, times);
            for (FieldPrice i : venueFieldPriceList) {
                i.setHaveFullCourt(null);
                i.setHaveHalfCourt(null);
                FieldPrice full = i.getFieldPriceFull();//全场的状态
                FieldPrice left = i.getFieldPriceLeft();
                FieldPrice right = i.getFieldPriceRight();
                for (TimePrice j : i.getTimePriceList()) {

                    String time=j.getTime();//当前场地的时间
                    if("0".equals(j.getStatus())){
                        if(StringUtils.isNoneEmpty(time)){
                            if(full!=null){
                                for (TimePrice k : full.getTimePriceList()) {
                                    String fullTime=k.getTime();
                                    if(time.endsWith(fullTime)&&"1".equals(k.getStatus())){
                                        j.setStatus("1");//全场已占用，半场不可用
                                        break;
                                    }
                                }
                            }
                            if(left!=null) {
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
                            if(right!=null){
                                if("0".equals(j.getStatus())) {
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
            SimpleDateFormat fmt=new SimpleDateFormat("yyyy-MM-dd");
            model.addAttribute("consDate", fmt.format(consDate));
            model.addAttribute("venueId", venueId);
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
    public Map saveSettlement(String orderId, String payType,
                                 Double consPrice,
                                 Double memberCardInput,
                                 Double bankCardInput,
                                 Double weiXinInput,
                                 Double aliPayInput,
                                 Double couponInput) {
        Map map=new HashMap<>();
        Double inputSum=memberCardInput+bankCardInput+weiXinInput+aliPayInput+couponInput;
        if(consPrice==null){
            map.put("result",3);
            map.put("msg","实付金额不能为空");
            return map;
        }
        if(consPrice.equals(inputSum)==false){
            map.put("result",4);
            map.put("msg","多方式付款总和不等于实付金额");
            return map;
        }
        ReserveVenueCons reserveVenueCons = reserveAppVenueConsService.get(orderId);
        if(reserveVenueCons==null){
            map.put("result",0);
            map.put("msg","该订单不存在");
        }else{
            Boolean bool = reserveAppVenueConsService.saveSettlement(reserveVenueCons,payType,consPrice,
                    memberCardInput,bankCardInput,weiXinInput,aliPayInput,couponInput);
            if(bool){
                map.put("result",1);
                map.put("msg","订单结算成功");
            }else{
                map.put("result",2);
                map.put("msg","订单已结算，不可重复结算");
            }
        }
        return map;
    }
    @RequestMapping(value = "reservation")
    @ResponseBody
    public Map reservation(String reserveJson,String username,String phone) {
        String reserve=reserveJson.replaceAll("&quot;","\"");
        JSONObject object=JSON.parseObject(reserve);
        String date = (String)object.get("consDate");
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date consDate = null;
        try {
            consDate = format.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        List<Map> list= (List<Map>) object.get("venueConsList");
         List<ReserveVenueConsItem> items=new ArrayList<>();
        for(Map i:list){
            ReserveVenueConsItem item=new ReserveVenueConsItem();
            ReserveField field=new ReserveField();
            String filedId=(String) i.get("reserveFieldId");
            field.setId(filedId);
            String filedName=(String) i.get("reserveFieldName");
            field.setName(filedName);
            item.setReserveField(field);

            String price=(String)i.get("orderPrice");
            Double orderPrice=Double.valueOf(price);
            item.setOrderPrice(orderPrice);

            String startTime=(String)i.get("startTime");
            item.setStartTime(startTime);
            String endTime=(String)i.get("endTime");
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
            if(bool==false){
                break;//该时间段不能使用，跳出循环
            }
        }
        String orderId=null;
        if (bool==true) {
            ReserveVenueCons reserveVenueCons=new ReserveVenueCons();
            reserveVenueCons.setUserName(username);
            reserveVenueCons.setConsMobile(phone);
            String reserveVenueId=(String)object.get("reserveVenueId");
            ReserveVenue venue=new ReserveVenue(reserveVenueId);
            reserveVenueCons.setReserveVenue(venue);
            reserveVenueCons.setReserveType(ReserveVenueCons.RESERVATION);//已预定
            reserveVenueCons.setConsDate(consDate);
            reserveVenueCons.setVenueConsList(items);
            orderId=reserveAppVenueConsService.saveOrder(reserveVenueCons);//保存预订信息
        }
        Map map=new HashMap<>();
        map.put("orderId", orderId);
        map.put("bool", bool);
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
        Map order = reserveAppVenueConsService.detail(orderId);
        return JSON.toJSONString(order);
    }

    @RequestMapping(value = "orderList")
    @ResponseBody
    /**
     *订单详情
     * @param orderId
     * @return
     */
    public String orderList(String reserveType,String phone) {
        List<Map> orderList = reserveAppVenueConsService.orderList(reserveType,phone);
        return JSON.toJSONString(orderList);
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
        Map map=new HashMap<>();
        if(venueCons==null){
            map.put("rs",0);
            map.put("msg","该订单不存在");
            return map;
        }
        String reserveType=venueCons.getReserveType();
        if("4".equals(reserveType)){
            map.put("rs",4);
            map.put("msg","该订单已结算，不可取消");
            return map;
        }
        if(venueCons!=null){
            reserveAppVenueConsService.cancelOrder(venueCons);
            map.put("rs",1);
            map.put("msg","订单取消成功");
        }
        return map;
    }
}
