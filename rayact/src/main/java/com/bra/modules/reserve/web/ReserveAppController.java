package com.bra.modules.reserve.web;

import com.bra.common.utils.StringUtils;
import com.bra.common.web.BaseController;
import com.bra.modules.reserve.entity.ReserveField;
import com.bra.modules.reserve.entity.ReserveVenueCons;
import com.bra.modules.reserve.entity.ReserveVenueConsItem;
import com.bra.modules.reserve.entity.form.FieldPrice;
import com.bra.modules.reserve.entity.form.TimePrice;
import com.bra.modules.reserve.service.ReserveAppFieldPriceService;
import com.bra.modules.reserve.service.ReserveAppVenueConsService;
import com.bra.modules.reserve.service.ReserveVenueConsItemService;
import com.bra.modules.reserve.utils.TimeUtils;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 场地预定管理
 * Created by xiaobin on 16/1/5.
 */
@Controller
@RequestMapping(value = "${adminPath}/app/reserve/field")
public class ReserveAppController extends BaseController {

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
    public String main(Date consDate, String venueId,String projectId,String consMobile,String userName,Model model) throws ParseException {
        if (consDate == null) {
            consDate = new Date();
        }
        List<String> times=new ArrayList<>();
        times.addAll(TimeUtils.getTimeSpacListValue("06:00:00", "00:30:00", 30));
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
                    j.setConsItem(null);
                    j.setConsType(null);
                    j.setUserName(null);
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

                if (full != null) {
                    full.setHaveFullCourt(null);
                    full.setHaveHalfCourt(null);
                    for (TimePrice k : full.getTimePriceList()) {
                        k.setConsItem(null);
                        k.setPrice(null);
                        k.setConsType(null);
                        k.setUserName(null);
                    }
                }

                if (left != null) {
                    left.setHaveFullCourt(null);
                    left.setHaveHalfCourt(null);
                    for (TimePrice k : left.getTimePriceList()) {
                        k.setConsItem(null);
                        k.setPrice(null);
                        k.setConsType(null);
                        k.setUserName(null);
                    }
                }

                if (right != null) {
                    right.setHaveFullCourt(null);
                    right.setHaveHalfCourt(null);
                    for (TimePrice k : right.getTimePriceList()) {
                        k.setConsItem(null);
                        k.setPrice(null);
                        k.setConsType(null);
                        k.setUserName(null);
                    }
                }
            }
            model.addAttribute("venueFieldPriceList", venueFieldPriceList);
            model.addAttribute("times", times);
            SimpleDateFormat fmt=new SimpleDateFormat("yyyy-MM-dd");
            model.addAttribute("consDate", fmt.format(consDate));
            model.addAttribute("consMobile", consMobile);
            model.addAttribute("userName", userName);
            model.addAttribute("venueId", venueId);
        }
        return "reserve/saleField/reserveAppField";
    }

    @RequestMapping(value = "reservation")
    @ResponseBody
    public Map<String, Boolean> reservation(ReserveVenueCons reserveVenueCons) {
        boolean bool = true;//时间段是否可用
        Date consDate = reserveVenueCons.getConsDate();
        List<ReserveVenueConsItem> itemList = reserveVenueCons.getVenueConsList();//查询预订的订单详情
        for (ReserveVenueConsItem i : itemList) {//订单详情
            String startTime = i.getStartTime();
            String endTime = i.getEndTime();
            ReserveField field = i.getReserveField();//场地
            //遍历该日期区间 的场地是否有预订
            bool = reserveVenueConsItemService.checkReserveTime(consDate, field, startTime, endTime);
            if(bool==false){
                break;//该时间段不能使用，跳出循环
            }
        }
        Map<String, Boolean> map = Maps.newConcurrentMap();
        if (bool==true) {
            reserveVenueCons.setReserveType(ReserveVenueCons.RESERVATION);//已预定
            reserveVenueCons.setConsDate(consDate);
            reserveAppVenueConsService.save(reserveVenueCons);//保存预订信息
        }
        map.put("bool", bool);
        return map;
    }
    /**
     * 结算订单
     *
     * @param
     * @return
     */
    @RequestMapping(value = "saveSettlement")
    @ResponseBody
    public String saveSettlement(String orderId, String payType,
                                 Double consPrice,
                                 Double memberCardInput,
                                 Double bankCardInput,
                                 Double weiXinInput,
                                 Double aliPayInput,
                                 Double couponInput) {
        ReserveVenueCons venueCons = reserveAppVenueConsService.saveSettlement(orderId,payType,consPrice,
                memberCardInput,bankCardInput,weiXinInput,aliPayInput,couponInput);
        if(venueCons==null){
            return "false";
        }
        return "true";
    }
}
