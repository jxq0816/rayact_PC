package com.bra.modules.reserve.web;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bra.common.utils.Collections3;
import com.bra.common.utils.DateUtils;
import com.bra.common.utils.StringUtils;
import com.bra.common.web.BaseController;
import com.bra.common.web.annotation.Token;
import com.bra.modules.reserve.entity.*;
import com.bra.modules.reserve.entity.form.FieldPrice;
import com.bra.modules.reserve.entity.form.VenueGiftForm;
import com.bra.modules.reserve.service.*;
import com.bra.modules.reserve.utils.TimeUtils;
import com.bra.modules.sys.entity.User;
import com.bra.modules.sys.service.SystemService;
import com.bra.modules.sys.utils.UserUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 场地预定管理
 * Created by xiaobin on 16/1/5.
 */
@Controller
@RequestMapping(value = "${adminPath}/reserve/field")
public class ReserveController extends BaseController {

    //场馆
    @Autowired
    private ReserveVenueService reserveVenueService;
    //场地
    @Autowired
    private ReserveFieldService reserveFieldService;
    //会员信息
    @Autowired
    private ReserveMemberService reserveMemberService;
    @Autowired
    private ReserveFieldPriceService reserveFieldPriceService;
    @Autowired
    private ReserveMobileFieldPriceService reserveMobileFieldPriceService;
    @Autowired
    private ReserveFieldStatusService reserveFieldStatusService;
    @Autowired
    private ReserveVenueConsService reserveVenueConsService;
    @Autowired
    private ReserveVenueConsItemService reserveVenueConsItemService;
    //教练
    @Autowired
    private ReserveTutorService reserveTutorService;
    @Autowired
    private ReserveTutorOrderService reserveTutorOrderService;
    @Autowired
    private ReserveVenueOrderService reserveVenueOrderService;
    @Autowired
    private ReserveCommodityService reserveCommodityService;
    @Autowired
    private ReserveVenueGiftService reserveVenueGiftService;
    @Autowired
    private SystemService systemService;
    @Autowired
    private ReserveUserService userService;
    @Autowired
    private ReserveVenueApplyCutService reserveVenueApplyCutService;
    @Autowired
    private ReserveProjectService reserveProjectService;
    @Autowired
    private ReserveVenueEmptyCheckService reserveVenueEmptyCheckService;

    /**
     * 场地预订 界面
     *
     * @param t        超链接选择的日期
     * @param consDate 日期控件选择的日期
     * @param venueId  场馆编号
     * @param model
     * @return
     * @throws ParseException
     */
    @RequestMapping(value = "main")
    public String main(Long t, Date consDate, String venueId, Model model) throws ParseException {
        Calendar ago = Calendar.getInstance();
        //如果是通过超链接 点击选择的时间
        if (t != null) {
            consDate = new Date(t);
        }
        //如果请求参数没有日期
        if (t == null && consDate == null) {
            consDate = DateUtils.parseDate(DateFormatUtils.format(Calendar.getInstance().getTime(), "yyyy-MM-dd"), "yyyy-MM-dd");//获得今天的日期
        }
        ago.setTime(consDate);
        ago.add(Calendar.DAY_OF_MONTH, -3);//获得预订日期的上一天
        Date defaultDate = DateUtils.parseDate(DateFormatUtils.format(ago.getTime(), "yyyy-MM-dd"), "yyyy-MM-dd");//获得前3天的日期
        //获取可预定时间段:一周
        Map<String, Long> timeSlot = TimeUtils.getNextDaysMap(defaultDate, 7);
        model.addAttribute("timeSlot", timeSlot);

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String consDateFormat = format.format(consDate);//预订日期的格式化
        model.addAttribute("consDateFormat", consDateFormat);
        model.addAttribute("consDate", consDate);//预订日期回传

        //获得所有场馆信息
        List<ReserveVenue> reserveVenueList = reserveVenueService.findList(new ReserveVenue());
        model.addAttribute("reserveVenueList", reserveVenueList);
        //默认场馆的场地
        if (!Collections3.isEmpty(reserveVenueList)) {
            ReserveVenue reserveVenue;
            if (StringUtils.isNoneBlank(venueId)) {
                reserveVenue = reserveVenueService.get(venueId);
            } else {
                reserveVenue = reserveVenueList.get(0);
            }
            //默认场馆
            model.addAttribute("reserveVenue", reserveVenue);
            ReserveField reserveField = new ReserveField();
            reserveField.setReserveVenue(reserveVenue);
            //上午场地价格
            List<String> timesAM = TimeUtils.getTimeSpacListValue("06:00:00", "12:30:00", 30);
            List<FieldPrice> venueFieldPriceListAM = reserveFieldPriceService.findByDate(reserveVenue.getId(), "1", consDate, timesAM);
            //下午场地价格
            List<String> timesPM = TimeUtils.getTimeSpacListValue("12:30:00", "18:30:00", 30);
            List<FieldPrice> venueFieldPriceListPM = reserveFieldPriceService.findByDate(reserveVenue.getId(), "1", consDate, timesPM);
            //晚上场地价格
            List<String> timesEvening = TimeUtils.getTimeSpacListValue("18:30:00", "00:30:00", 30);
            List<FieldPrice> venueFieldPriceListEvening = reserveFieldPriceService.findByDate(reserveVenue.getId(), "1", consDate, timesEvening);

            List list=new ArrayList<>();
            Map mapAM=new HashMap<>();
            mapAM.put("fieldPriceList",venueFieldPriceListAM);
            mapAM.put("timeName","上午");
            mapAM.put("times",timesAM);
            list.add(mapAM);
            Map mapPM=new HashMap<>();
            mapPM.put("fieldPriceList",venueFieldPriceListPM);
            mapPM.put("timeName","下午");
            mapPM.put("times",timesPM);
            list.add(mapPM);
            Map mapEvening=new HashMap<>();
            mapEvening.put("fieldPriceList",venueFieldPriceListEvening);
            mapEvening.put("timeName","晚上");
            mapEvening.put("times",timesEvening);
            list.add(mapEvening);
            model.addAttribute("list", list);
        }
        return "reserve/saleField/reserveField";
    }

    @RequestMapping(value = "status")
    public String status(Long t, Date consDate, String venueId, Model model) throws ParseException {
        Calendar ago = Calendar.getInstance();
        //如果是通过超链接 点击选择的时间
        if (t != null) {
            consDate = new Date(t);
        }
        //如果请求参数没有日期
        if (t == null && consDate == null) {
            consDate = DateUtils.parseDate(DateFormatUtils.format(Calendar.getInstance().getTime(), "yyyy-MM-dd"), "yyyy-MM-dd");//获得今天的日期
        }
        ago.setTime(consDate);
        ago.add(Calendar.DAY_OF_MONTH, -3);//获得预订日期的上一天
        Date defaultDate = DateUtils.parseDate(DateFormatUtils.format(ago.getTime(), "yyyy-MM-dd"), "yyyy-MM-dd");//获得前3天的日期
        //获取可预定时间段:一周
        Map<String, Long> timeSlot = TimeUtils.getNextDaysMap(defaultDate, 7);
        model.addAttribute("timeSlot", timeSlot);

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String consDateFormat = format.format(consDate);//预订日期的格式化
        model.addAttribute("consDateFormat", consDateFormat);
        model.addAttribute("consDate", consDate);//预订日期回传

        //获得所有场馆信息
        List<ReserveVenue> reserveVenueList = reserveVenueService.findList(new ReserveVenue());
        model.addAttribute("reserveVenueList", reserveVenueList);
        //默认场馆的场地
        if (!Collections3.isEmpty(reserveVenueList)) {
            ReserveVenue reserveVenue;
            if (StringUtils.isNoneBlank(venueId)) {
                reserveVenue = reserveVenueService.get(venueId);
            } else {
                reserveVenue = reserveVenueList.get(0);
            }
            //默认场馆
            model.addAttribute("reserveVenue", reserveVenue);
            ReserveField reserveField = new ReserveField();
            reserveField.setReserveVenue(reserveVenue);
            //上午场地价格
            List<String> timesAM = TimeUtils.getTimeSpacListValue("06:00:00", "12:30:00", 30);
            List<FieldPrice> venueFieldPriceListAM = reserveFieldStatusService.emptyCheck(reserveVenue.getId(), consDate, timesAM);
            //下午场地价格
            List<String> timesPM = TimeUtils.getTimeSpacListValue("12:30:00", "18:30:00", 30);
            List<FieldPrice> venueFieldPriceListPM = reserveFieldStatusService.emptyCheck(reserveVenue.getId(), consDate, timesPM);
            //晚上场地价格
            List<String> timesEvening = TimeUtils.getTimeSpacListValue("18:30:00", "00:30:00", 30);
            List<FieldPrice> venueFieldPriceListEvening = reserveFieldStatusService.emptyCheck(reserveVenue.getId(), consDate, timesEvening);

            List list=new ArrayList<>();
            Map mapAM=new HashMap<>();
            mapAM.put("fieldPriceList",venueFieldPriceListAM);
            mapAM.put("times",timesAM);
            list.add(mapAM);
            Map mapPM=new HashMap<>();
            mapPM.put("fieldPriceList",venueFieldPriceListPM);
            mapPM.put("times",timesPM);
            list.add(mapPM);
            Map mapEvening=new HashMap<>();
            mapEvening.put("fieldPriceList",venueFieldPriceListEvening);
            mapEvening.put("times",timesEvening);
            list.add(mapEvening);
            model.addAttribute("list", list);
        }
        return "reserve/saleField/reserveFieldStatus";
    }
    //预定表单
    @RequestMapping(value = "reserveForm")
    @Token(save = true)
    public String reserveForm(Model model, String fieldId, Long date, String time, String venueId) throws ParseException {
        ReserveField reserveField = reserveFieldService.get(fieldId);
        model.addAttribute("reserveField", reserveField);
        String isHalfCourt = reserveFieldService.getFiledType(reserveField);
        model.addAttribute("isHalfCourt", isHalfCourt);//是否半场

        //获取预定开始时间
        String start = time.substring(0, 5) + ":00";
        List<String> times = TimeUtils.getTimeSpacList(start, "00:30:00", TimeUtils.BENCHMARK);
        String consDate = DateUtils.formatDate(new Date(date), "yyyy-MM-dd");
        model.addAttribute("consDate", consDate);
        model.addAttribute("times", times);
        //会员
        ReserveMember reserveMember = new ReserveMember();
        reserveMember.setReserveVenue(new ReserveVenue(venueId));
        reserveMember.setCardType("1");//卡号类型(会员类型1:储值卡,2:次卡)
        model.addAttribute("memberList", reserveMemberService.findList(reserveMember));

        //查询场地所对应项目的教练
        ReserveTutor tutor = new ReserveTutor();
        ReserveProject project = reserveField.getReserveProject();
        tutor.setProject(project);
        model.addAttribute("tutors", reserveTutorService.findList(tutor));

        if (StringUtils.isNotBlank(time)) {
            String[] timeStr = time.split("-");
            model.addAttribute("startTime", timeStr[0]);
            model.addAttribute("endTime", timeStr[1]);
        }
        model.addAttribute("venueId", venueId);
        return "reserve/saleField/reserveForm";
       /* return "reserve/visitorsSetOrder/reserveFormSelect2";*/
    }

    //取消预定表单
    @RequestMapping(value = "cancelForm")
    @Token(save = true)
    public String cancelForm(Model model, String itemId) {
        ReserveVenueConsItem item = reserveVenueConsItemService.get(itemId);
        ReserveVenueCons cons = reserveVenueConsService.get(item.getConsData().getId());
        model.addAttribute("cos", cons);
        model.addAttribute("startTime", item.getStartTime());
        model.addAttribute("endTime", item.getEndTime());
        model.addAttribute("item", item);
        List<String> times = TimeUtils.getTimeSpacList("06:00:00", "00:30:00", TimeUtils.BENCHMARK);
        model.addAttribute("times", times);
        //教练订单
        List<ReserveTutorOrder> tutorOrderList = reserveTutorOrderService.findNotCancel(cons.getId(), ReserveVenueCons.MODEL_KEY);
        if (!Collections3.isEmpty(tutorOrderList)) {
            model.addAttribute("tutorOrder", tutorOrderList.get(0));
        }
        return "reserve/saleField/cancelForm";
    }

    /**
     * 取消预定
     *
     * @param itemId
     * @return
     */
    @RequestMapping(value = "cancelReservation")
    @ResponseBody
    @Token(remove = true)
    public List<Map<String, String>> cancelReservation(String itemId, String tutorOrderId) {
        List<ReserveVenueConsItem> itemList = reserveVenueConsService.cancelReserve(itemId, tutorOrderId);
        return getReserveMap(itemList);
    }

    private List<Map<String, String>> getReserveMap(List<ReserveVenueConsItem> itemList) {
        List<Map<String, String>> list = Lists.newArrayList();
        if (itemList == null)
            return list;
        Map<String, String> data;
        for (ReserveVenueConsItem item : itemList) {
            String startTime = item.getStartTime();
            String endTime = item.getEndTime();
            List<String> times = TimeUtils.getTimeSpacListValue(startTime + ":00", endTime + ":00", 30);
            for (String time : times) {
                data = Maps.newConcurrentMap();
                data.put("fieldId", item.getReserveField().getId());
                data.put("time", time);
                data.put("itemId", item.getId());
                list.add(data);
            }
        }
        return list;
    }

    /**
     * 场地预定(保存)
     *
     * @param reserveVenueCons
     * @return
     */
    @RequestMapping(value = "reservation")
    @ResponseBody
    @Token(remove = true)
    public List<Map<String, String>> reservation(ReserveVenueCons reserveVenueCons) {
        boolean bool = true;//时间段是否可用
        String frequency = reserveVenueCons.getFrequency();//频率
        Date consDate = reserveVenueCons.getConsDate();
        List<ReserveVenueConsItem> itemList = reserveVenueCons.getVenueConsList();//查询预订的订单详情
        for (ReserveVenueConsItem i : itemList) {//订单详情
            String startTime = i.getStartTime();
            String endTime = i.getEndTime();
            ReserveField field = i.getReserveField();//场地

            Date startDate = reserveVenueCons.getStartDate();//预订开始日期
            Date endDate = reserveVenueCons.getEndDate();//预订结束日期
            //遍历该日期区间 的场地是否有预订
            //单次
            if ("1".equals(frequency)) {
                bool = reserveVenueConsItemService.checkReserveTime(consDate, field, startTime, endTime);
            } else if ("2".equals(frequency)) {//每天
                List<Date> list = TimeUtils.getIntervalDayList(startDate, endDate);
                for (Date date : list) {
                    bool = reserveVenueConsItemService.checkReserveTime(date, field, startTime, endTime);
                    if (bool == false) {
                        break;
                    }
                }
            } else if ("3".equals(frequency)) {//每周
                String week = TimeUtils.getWeekOfDate(consDate);
                List<Date> list = TimeUtils.getIntervalWeekDayList(startDate, endDate, week);
                for (Date date : list) {
                    bool = reserveVenueConsItemService.checkReserveTime(date, field, startTime, endTime);
                    if (bool == false) {
                        break;
                    }
                }
            }
        }
        Map<String, String> map = Maps.newConcurrentMap();
        List<Map<String, String>> list = new ArrayList<>();
        if (bool) {
            reserveVenueCons.setReserveType(ReserveVenueCons.RESERVATION);//预定
            Date startDate = reserveVenueCons.getStartDate();//预订开始日期
            Date endDate = reserveVenueCons.getEndDate();//预订结束日期
            if ("1".equals(frequency)) {
                reserveVenueCons.preInsert();
                reserveVenueCons.setConsDate(consDate);
                reserveVenueConsService.save(reserveVenueCons);//保存预订信息
            } else if ("2".equals(frequency)) {//每天
                List<Date> dayList = TimeUtils.getIntervalDayList(startDate, endDate);
                for (Date d : dayList) {
                    reserveVenueCons.preInsert();
                    reserveVenueCons.setConsDate(d);
                    reserveVenueConsService.save(reserveVenueCons);//保存预订信息
                }
            } else if ("3".equals(frequency)) {//每周
                String week = TimeUtils.getWeekOfDate(consDate);
                List<Date> weekDayList = TimeUtils.getIntervalWeekDayList(startDate, endDate, week);
                for (Date date : weekDayList) {
                    reserveVenueCons.preInsert();
                    reserveVenueCons.setConsDate(date);
                    reserveVenueConsService.save(reserveVenueCons);//保存预订信息
                }
            }
            for (ReserveVenueConsItem item : itemList) {
                String startTime = item.getStartTime();
                String endTime = item.getEndTime();
                List<String> times = TimeUtils.getTimeSpacListValue(startTime + ":00", endTime + ":00", 30);
                for (String time : times) {
                    Map<String, String> data = Maps.newConcurrentMap();
                    data.put("fieldId", item.getReserveField().getId());
                    data.put("time", time);
                    data.put("itemId", item.getId());
                    data.put("bool", "1");
                    list.add(data);
                }
            }
        } else {
            map.put("bool", "0");
            list.add(map);
        }
        return list;
    }

    /**
     * 结算订单的表单
     *
     * @param itemId
     * @return
     */
    @RequestMapping(value = "settlementForm")
    @Token(save = true)
    public String settlementForm(String itemId, Model model) {
        //操作类型(1:已预定,3:已取消,4:已结算)
        ReserveVenueConsItem consItem = reserveVenueConsItemService.get(itemId);//获得预订详情
        ReserveVenueCons order = reserveVenueConsService.get(consItem.getConsData().getId());//获得订单
        ReserveMember member = null;
        if(order.getMember()!=null){
            member= reserveMemberService.get(order.getMember().getId());
        }
        //申请优惠
        ReserveVenueApplyCut applycut = new ReserveVenueApplyCut();
        applycut.getSqlMap().put("dsf", " and c.id = '" + order.getId() + "' ");
        List<ReserveVenueApplyCut> cuts = reserveVenueApplyCutService.findList(applycut);
        if (cuts != null && cuts.size() > 0) {
            applycut = cuts.get(0);
            order.setDiscountPrice(applycut.getCutPrice());
        }
        order.setConsPrice(order.getShouldPrice() - applycut.getCutPrice());
        //教练订单
        List<ReserveTutorOrder> tutorOrderList = reserveTutorOrderService.findNotCancel(order.getId(), ReserveVenueCons.MODEL_KEY);
        if (!Collections3.isEmpty(tutorOrderList)) {
            model.addAttribute("tutorOrder", tutorOrderList.get(0));
        }
        ReserveVenueConsItem search = new ReserveVenueConsItem();
        search.setConsData(consItem.getConsData());
        List<ReserveVenueConsItem> itemList = reserveVenueConsItemService.findList(search);
        model.addAttribute("itemList", itemList);
        //有变更权限的高管
        User user = new User();
        user.setUserType("2");//用户类型(1:超级管理员；2:场馆管理员；3：高管；4：收银；5：财务)
        List<User> authUserList = userService.findList(user);
        model.addAttribute("authUserList", authUserList);
        model.addAttribute("order", order);
        model.addAttribute("member", member);
        //赠品
        model.addAttribute("giftList", reserveVenueGiftService.findList(new ReserveVenueGift(order.getId(), ReserveVenueCons.MODEL_KEY)));
        return "reserve/saleField/settlementForm";
    }

    /**
     * 结算订单
     *
     * @param
     * @return
     */
    @RequestMapping(value = "saveSettlement")
    @Token(remove = true)
    @ResponseBody
    public Map saveSettlement(String id, String payType, String authUserId,
                              @RequestParam(required = false, defaultValue = "0") Double discountPrice,
                              Double consPrice,
                              Double memberCardInput,
                              Double cashInput,
                              Double bankCardInput,
                              Double weiXinInput,
                              Double weiXinPersonalInput,
                              Double aliPayInput,
                              Double aliPayPersonalInput,
                              Double couponInput,
                              String remarks) {

        ReserveVenueCons venueCons = reserveVenueConsService.saveSettlement(id, payType, authUserId, discountPrice, consPrice,
                memberCardInput, cashInput, bankCardInput, weiXinInput, weiXinPersonalInput, aliPayInput, aliPayPersonalInput, couponInput,remarks);
        List<Map<String, String>> mapList = getReserveMap(venueCons.getVenueConsList());
        Map map = new HashMap<>();
        map.put("mapList", mapList);
        map.put("orderId", venueCons.getId());
        return map;
    }

    /**
     * 结算订单 结果展示
     *
     * @param
     * @return
     */
    @RequestMapping(value = "settlementResult")
    public String saveSettlement(String orderId, Model model) {
        ReserveVenueCons venueCons = reserveVenueConsService.get(orderId);
        model.addAttribute("venueCons", venueCons);
        model.addAttribute("companyName",UserUtils.getOfficeList().get(0).getName());
        return "reserve/saleField/settlementResult";
    }

    //订单详情
    @RequestMapping(value = "details")
    public String details(String itemId, String fieldId,String time,String date,Model model) {
        if(StringUtils.isNoneEmpty(itemId)) {
            ReserveVenueConsItem consItem = reserveVenueConsItemService.get(itemId);
            ReserveVenueConsItem search = new ReserveVenueConsItem();
            //consItem.getConsData().setReserveType("3");
            search.setConsData(consItem.getConsData());
            ReserveVenueCons cons = reserveVenueConsService.get(consItem.getConsData().getId());
            model.addAttribute("cos", cons);
            List<ReserveVenueConsItem> itemList = reserveVenueConsItemService.findList(search);
            model.addAttribute("itemList", itemList);
            model.addAttribute("tutorOrderList", reserveTutorOrderService.findNotCancel(cons.getId(), ReserveVenueCons.MODEL_KEY));
            //赠品
            model.addAttribute("giftList", reserveVenueGiftService.findList(new ReserveVenueGift(cons.getId(), ReserveVenueCons.MODEL_KEY)));
            return "reserve/saleField/details";
        }else{
            ReserveVenueOrder order=new ReserveVenueOrder();
            order.setReserveField(new ReserveField(fieldId));
            order.setOrderDate(new Date(date));
            String startTime=time.substring(0,5);
            String endTime=time.substring(6,11);
            order.setStartTime(startTime);
            order.setEndTime(endTime);
            List<ReserveVenueOrder> ticketList = reserveVenueOrderService.findList(order);
            model.addAttribute("ticketList", ticketList);
            return "reserve/saleField/ticketDetails";
        }
    }

    //退款
    @RequestMapping(value = "refund")
    public String refund(String itemId, Model model) {
       /* ReserveVenueConsItem consItem = reserveVenueConsItemService.get(itemId);
        ReserveVenueConsItem search = new ReserveVenueConsItem();
        consItem.getConsData().setReserveType("3");

        search.setConsData(consItem.getConsData());
        ReserveVenueCons cons = reserveVenueConsService.get(consItem.getConsData().getId());
        model.addAttribute("cos", cons);
        List<ReserveVenueConsItem> itemList = reserveVenueConsItemService.findList(search);
        model.addAttribute("itemList", itemList);
        List<String> times = TimeUtils.getTimeSpacList("09:00:00", "23:00:00", TimeUtils.BENCHMARK);
        model.addAttribute("tutorOrderList", reserveTutorOrderService.findNotCancel(cons.getId(), ReserveVenueCons.MODEL_KEY));
        model.addAttribute("times", times);*/
        return "reserve/saleField/details";
    }

    //赠品
    @RequestMapping(value = "gift")
    @Token(save = true)
    public String gift(String itemId, Model model) {
        ReserveVenueConsItem consItem = reserveVenueConsItemService.get(itemId);
        ReserveVenueConsItem search = new ReserveVenueConsItem();
        consItem.getConsData().setReserveType("3");

        search.setConsData(consItem.getConsData());
        ReserveVenueCons cons = reserveVenueConsService.get(consItem.getConsData().getId());
        model.addAttribute("cos", cons);
        //商品
        model.addAttribute("giftList", reserveVenueGiftService.findList(new ReserveVenueGift(cons.getId(), ReserveVenueCons.MODEL_KEY)));
        model.addAttribute("commodityList", reserveCommodityService.findList(new ReserveCommodity()));
        return "reserve/saleField/giftForm";
    }

    //保存赠品
    @RequestMapping(value = "saveGift")
    @ResponseBody
    @Token(remove = true)
    public String saveGift(VenueGiftForm giftForm) {
        List<ReserveVenueGift> giftList = giftForm.getGiftList();
        for (ReserveVenueGift gift : giftList) {
            int giftNum = gift.getNum();
            ReserveCommodity commodity = gift.getGift();
            commodity = reserveCommodityService.get(commodity);
            int storeNum = commodity.getRepertoryNum();
            if (giftNum > storeNum) {
                return "fail";
            }
        }

        reserveVenueGiftService.saveVenueList(giftForm, ReserveVenueCons.MODEL_KEY);
        return "success";
    }

    //检测授权码
    @RequestMapping(value = "checkUserAuth")
    @ResponseBody
    public User checkUserAuth(String userId, String authPassword) {
        User user = systemService.checkUserAuth(userId, authPassword);
        return user;
    }

    @RequestMapping(value = "checkTime")
    @ResponseBody
    public String checkTime(String startTime, String endTime) {
        startTime = TimeUtils.earlyMorningFormat(startTime);
        endTime = TimeUtils.earlyMorningFormat(endTime);
        if (startTime.compareTo(endTime) < 0) {
            return "1";
        } else {
            return "0";
        }
    }

    //
    @RequestMapping(value = "applyCut")
    @Token(save = true)
    public String applyCut(String itemId, Model model) {
        ReserveVenueConsItem consItem = reserveVenueConsItemService.get(itemId);
        ReserveVenueConsItem search = new ReserveVenueConsItem();
        consItem.getConsData().setReserveType("3");
        search.setConsData(consItem.getConsData());
        ReserveVenueCons cons = reserveVenueConsService.get(consItem.getConsData().getId());
        User u = new User();
        u.setUserType("3");
        List<User> list = userService.findList(u);
        model.addAttribute("cons", cons);
        model.addAttribute("users", list);
        ReserveVenueApplyCut applycut = new ReserveVenueApplyCut();
        if (cons != null && cons.getId() != null) {
            applycut.getSqlMap().put("dsf", " and c.id = '" + cons.getId() + "' ");
        }
        List<ReserveVenueApplyCut> cuts = reserveVenueApplyCutService.findList(applycut);
        if (cuts != null && cuts.size() > 0) {
            model.addAttribute("applyCut", cuts.get(0));
            return "reserve/saleField/applyCutFormDone";
        } else {
            return "reserve/saleField/applyCutForm";
        }
    }

    //保存赠品
    @RequestMapping(value = "saveApplyCut")
    @ResponseBody
    public String saveApplyCut(HttpServletRequest request) {
        String consId = request.getParameter("consId");
        if (consId != null && !"".equals(consId)) {
            String userId = request.getParameter("userId");
            String remarks = request.getParameter("remarks");
            ReserveVenueApplyCut reserveVenueApplyCut = new ReserveVenueApplyCut();
            User user = userService.getUser(userId);
            ReserveVenueCons cons = reserveVenueConsService.get(consId);
            reserveVenueApplyCut.setVenue(cons.getReserveVenue());
            reserveVenueApplyCut.setApplyer(user);
            reserveVenueApplyCut.setMember(cons.getMember());
            reserveVenueApplyCut.setCons(cons);
            reserveVenueApplyCut.setPrice(cons.getOrderPrice());
            reserveVenueApplyCut.setConsDate(cons.getConsDate());
            reserveVenueApplyCut.setDone("0");
            reserveVenueApplyCut.setRemarks(remarks);
            reserveVenueApplyCut.setName(cons.getUserName());
            reserveVenueApplyCut.setTel(cons.getConsMobile());
            reserveVenueApplyCutService.save(reserveVenueApplyCut);
            return "success";
        }
        return "fail";
    }

    //移动端优惠通知列表
    @RequestMapping(value = "mobile/cutlist")
    @ResponseBody
    public String cutList(HttpServletRequest request) {
        List<Map<String, String>> rtn = new ArrayList<>();
        String userId = request.getParameter("userId");
        String status = request.getParameter("status");
        if (userId != null && !"".equals(userId)) {
            User user = systemService.getUser(userId);
            ReserveVenueApplyCut reserveVenueApplyCut = new ReserveVenueApplyCut();
            reserveVenueApplyCut.setTenantId(user.getCompany().getId());
            reserveVenueApplyCut.getSqlMap().put("dsf", " and a.apply_user_id = '" + userId + "' and a.done = '" + status + "' ");
            List<ReserveVenueApplyCut> list = reserveVenueApplyCutService.findList(reserveVenueApplyCut);
            if (list != null) {
                for (ReserveVenueApplyCut cut : list) {
                    Map<String, String> node = new HashMap<>();
                    node.put("venueId", cut.getVenue().getId());
                    node.put("venueName", reserveVenueService.get(cut.getVenue().getId()).getName());//null
                    String cid = cut.getCreateBy().getId();
                    node.put("creator", systemService.getUser(cid).getName());//null
                    String consId = cut.getCons().getId();
                    ReserveVenueCons reserveVenueCons = reserveVenueConsService.get(consId);
                    node.put("orderId", consId);
                    node.put("projectId", reserveVenueCons.getProject().getId());
                    node.put("projectName", reserveProjectService.get(reserveVenueCons.getProject().getId()).getName());//null
                    ReserveVenueConsItem item = new ReserveVenueConsItem();
                    item.setTenantId(user.getCompany().getId());
                    item.setConsData(reserveVenueCons);
                    List<ReserveVenueConsItem> items = reserveVenueConsItemService.findList(item);
                    if (items != null && items.size() > 0) {
                        node.put("timeArea", items.get(0).getStartTime() + "~" + items.get(0).getEndTime());
                    } else {
                        node.put("timeArea", "");
                    }
                    node.put("memberName", reserveVenueCons.getUserName());
                    node.put("orderDate", DateUtils.formatDate(reserveVenueCons.getConsDate()));
                    node.put("orderPrice", String.valueOf(reserveVenueCons.getShouldPrice()));
                    node.put("tel", reserveVenueCons.getConsMobile());
                    node.put("remarks", cut.getRemarks());
                    node.put("isNew", cut.getIsNew());
                    node.put("creatorImg", "0");
                    rtn.add(node);
                    //请求后置为非新
                    cut.setIsNew("0");
                    reserveVenueApplyCutService.save(cut);
                }
            }
            return JSONArray.toJSONString(rtn);
        }
        return "[]";
    }

    //移动端优惠通知列表
    @RequestMapping(value = "mobile/cutlist/hasNew")
    @ResponseBody
    public String hasNew(HttpServletRequest request) {
        Map<String, String> rtn = new HashMap<>();
        String userId = request.getParameter("userId");
        if (userId != null && !"".equals(userId)) {
            User user = systemService.getUser(userId);
            ReserveVenueApplyCut reserveVenueApplyCut = new ReserveVenueApplyCut();
            reserveVenueApplyCut.setTenantId(user.getCompany().getId());
            reserveVenueApplyCut.getSqlMap().put("dsf", " and a.apply_user_id = '" + userId + "' and a.done = '0' and a.is_new = '1' ");
            List<ReserveVenueApplyCut> list = reserveVenueApplyCutService.findList(reserveVenueApplyCut);
            if (list != null && list.size() > 0) {
                rtn.put("hasNew", String.valueOf(list.size()));
                return JSONObject.toJSONString(rtn);
            } else {
                rtn.put("hasNew", "0");
                return JSONObject.toJSONString(rtn);
            }

        }
        rtn.put("hasNew", "0");
        return JSONObject.toJSONString(rtn);
    }

    //移动端优惠反馈
    @RequestMapping(value = "mobile/cutprice")
    @ResponseBody
    public String cutPrice(HttpServletRequest request) {
        String userId = request.getParameter("userId");
        String orderId = request.getParameter("orderId");
        String cutPrice = request.getParameter("cutPrice");
        if (orderId != null && !"".equals(orderId)) {
            ReserveVenueApplyCut reserveVenueApplyCut = new ReserveVenueApplyCut();
            User user = systemService.getUser(userId);
            reserveVenueApplyCut.getSqlMap().put("dsf", " and a.cons_id = '" + orderId + "' ");
            reserveVenueApplyCut.setTenantId(user.getCompany().getId());
            List<ReserveVenueApplyCut> list = reserveVenueApplyCutService.findList(reserveVenueApplyCut);
            if (list != null && list.size() > 0) {
                ReserveVenueApplyCut cut = list.get(0);
                if (cut.getApplyer().getId().equals(userId)) {
                    cut.setCutPrice(Double.valueOf(cutPrice));
                    cut.setDone("1");
                    reserveVenueApplyCutService.save(cut);
                    Map<String, String> node = new HashMap<>();
                    node.put("status", "success");
                    return JSONObject.toJSONString(node);
                } else {
                    Map<String, String> node = new HashMap<>();
                    node.put("status", "fail");
                    return JSONObject.toJSONString(node);
                }
            } else {
                Map<String, String> node = new HashMap<>();
                node.put("status", "fail");
                return JSONObject.toJSONString(node);
            }
        }
        Map<String, String> node = new HashMap<>();
        node.put("status", "fail");
        return JSONObject.toJSONString(node);
    }

    //空场审核
    @RequestMapping(value = "checkEmpty")
    @Token(save = true)
    public String checkEmpty(Model model, String fieldId, Long date, String time, String venueId) throws ParseException {
        ReserveField reserveField = reserveFieldService.get(fieldId);
        model.addAttribute("reserveField", reserveField);
        String isHalfCourt = reserveFieldService.getFiledType(reserveField);
        model.addAttribute("isHalfCourt", isHalfCourt);//是否半场
        //获取预定开始时间
        String start = time.substring(0, 5) + ":00";
        List<String> times = TimeUtils.getTimeSpacList(start, "00:30:00", TimeUtils.BENCHMARK);
        String checkDate = DateUtils.formatDate(new Date(date), "yyyy-MM-dd");
        model.addAttribute("checkDate", checkDate);
        model.addAttribute("times", times);
        if (StringUtils.isNotBlank(time)) {
            String[] timeStr = time.split("-");
            model.addAttribute("startTime", timeStr[0]);
            model.addAttribute("endTime", timeStr[1]);
        }
        model.addAttribute("venueId", venueId);
        return "modules/reserve/reserveVenueEmptyCheckForm";
    }

    //空场审核
    @RequestMapping(value = "checkEmptyUpdate")
    @Token(save = true)
    public String checkEmptyUpdate(Model model, String checkId) throws ParseException {
        ReserveVenueEmptyCheck check = reserveVenueEmptyCheckService.get(checkId);
        ReserveField reserveField = reserveFieldService.get(check.getField().getId());
        model.addAttribute("check", check);
        model.addAttribute("reserveField", reserveField);
        model.addAttribute("isHalfCourt", check.getHalfCourt());//是否半场
        //获取预定开始时间
        String start = check.getStartTime() + ":00";
        List<String> times = TimeUtils.getTimeSpacList(start, "00:30:00", TimeUtils.BENCHMARK);
        model.addAttribute("checkDate", DateUtils.formatDate(check.getCheckDate()));
        model.addAttribute("times", times);
        model.addAttribute("startTime", check.getStartTime());
        model.addAttribute("endTime", check.getEndTime());
        model.addAttribute("venueId", check.getVenue().getId());
        return "modules/reserve/reserveVenueEmptyCheckFormUpdate";
    }

    //空场审核
    @RequestMapping(value = "saveCheckEmpty")
    @Token(save = true)
    @ResponseBody
    public String saveCheckEmpty(ReserveVenueEmptyCheck reserveVenueEmptyCheck) throws ParseException {
        boolean bool = true;//时间段是否可用
        Date checkDate = reserveVenueEmptyCheck.getCheckDate();
        String startTime = reserveVenueEmptyCheck.getStartTime();
        String endTime = reserveVenueEmptyCheck.getEndTime();
        ReserveField field = reserveVenueEmptyCheck.getField();//场地
        bool = reserveVenueConsItemService.checkReserveTime(checkDate, field, startTime, endTime);
        if (bool) {
            reserveVenueEmptyCheckService.save(reserveVenueEmptyCheck);//保存预订信息
            return "success";
        } else {
            return "fail";
        }
    }

    //场地状态界面
    @RequestMapping(value = "mobile/main")
    public String mobileMain(Model model, HttpServletRequest request) throws ParseException {
        String userId = request.getParameter("userId");
        String venueId = request.getParameter("venueId");
        String date = request.getParameter("date");
        Date consDate = DateUtils.parseDate(date);
        User user = systemService.getUser(userId);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String consDateFormat = format.format(consDate);//预订日期的格式化
        model.addAttribute("consDateFormat", consDateFormat);
        model.addAttribute("consDate", consDate);//预订日期回传
        //默认场馆
        ReserveVenue reserveVenue = reserveVenueService.get(venueId);
        //上午场地价格
        List<String> timesAM = new ArrayList<>();
        timesAM.addAll(TimeUtils.getTimeSpacListValue("06:00:00", "12:30:00", 30));
        model.addAttribute("timesAM", timesAM);
        List<FieldPrice> venueFieldPriceListAM = reserveMobileFieldPriceService.findByDateForMobile(reserveVenue.getId(), "1", consDate, timesAM, user);
        model.addAttribute("venueFieldPriceListAM", venueFieldPriceListAM);
        //下午场地价格
        List<String> timesPM = TimeUtils.getTimeSpacListValue("12:30:00", "18:30:00", 30);
        model.addAttribute("timesPM", timesPM);
        List<FieldPrice> venueFieldPriceListPM = reserveMobileFieldPriceService.findByDateForMobile(reserveVenue.getId(), "1", consDate, timesPM, user);
        model.addAttribute("venueFieldPriceListPM", venueFieldPriceListPM);
        //晚上场地价格
        List<String> timesEvening = TimeUtils.getTimeSpacListValue("18:30:00", "00:30:00", 30);
        model.addAttribute("timesEvening", timesEvening);
        List<FieldPrice> venueFieldPriceListEvening = reserveMobileFieldPriceService.findByDateForMobile(reserveVenue.getId(), "1", consDate, timesEvening, user);
        model.addAttribute("venueFieldPriceListEvening", venueFieldPriceListEvening);
        return "reserve/saleField/reserveFieldStatusMobile";
    }
}
