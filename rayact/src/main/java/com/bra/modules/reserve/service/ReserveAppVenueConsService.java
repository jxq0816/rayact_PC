package com.bra.modules.reserve.service;

import com.bra.common.service.CrudService;
import com.bra.common.utils.StringUtils;
import com.bra.modules.reserve.dao.ReserveVenueConsDao;
import com.bra.modules.reserve.dao.ReserveVenueConsItemDao;
import com.bra.modules.reserve.entity.ReserveMember;
import com.bra.modules.reserve.entity.ReserveStoredcardMemberSet;
import com.bra.modules.reserve.entity.ReserveVenueCons;
import com.bra.modules.reserve.entity.ReserveVenueConsItem;
import com.bra.modules.reserve.event.venue.VenueCheckoutEvent;
import com.bra.modules.reserve.utils.TimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 场地预定主表Service
 *
 * @author 肖斌
 * @version 2016-01-11
 */
@Service
@Transactional(readOnly = true)
public class ReserveAppVenueConsService extends CrudService<ReserveVenueConsDao, ReserveVenueCons> {


    @Autowired
    private ReserveVenueConsItemDao reserveVenueConsItemDao;
    @Autowired
    private ReserveVenueConsDao reserveVenueConsDao;
    @Autowired
    private ReserveFieldPriceService reserveFieldPriceService;
    @Autowired
    private ReserveMemberService reserveMemberService;
    @Autowired
    private ReserveVenueConsItemService reserveVenueConsItemService;

    /**
     * 订单详情
     * @param orderId 订单编号
     * @return
     */
    public Map detail(String orderId) {
        Map map=new HashMap<>();
        map.put("orderId",orderId);
        Map order=reserveVenueConsDao.detail(map);
        if(order!=null){
            List<Map> itemList=reserveVenueConsItemDao.orderItemList(map);
            order.put("itemList",itemList);
        }
        return order;
    }

    /**
     * 订单列表
     * @param reserveType 订单状态
     * @return
     */
    public  List<Map>  orderList(String reserveType,String phone) {
        Map map=new HashMap<>();
        map.put("reserveType",reserveType);
        map.put("phone",phone);
        List<Map> orderList=reserveVenueConsDao.orderList(map);
        for(Map i:orderList){
            String orderId=(String)i.get("orderId");
            Map m=new HashMap<>();
            m.put("orderId",orderId);
            List<Map> itemList=reserveVenueConsItemDao.orderItemList(m);
            String startTime=null;
            for(Map j:itemList){
                String start=(String) j.get("startTime");
                start=TimeUtils.earlyMorningFormat(start);
                if(startTime==null){
                    startTime=start;
                }else if(start.compareTo(startTime)<0){
                    startTime=start;
                }
            }
            i.put("startTime",startTime);
        }
        return orderList;
    }

    /**
     * 存储预定单据
     *
     * @param reserveVenueCons
     */
    @Transactional(readOnly = false)
    public String saveOrder(ReserveVenueCons reserveVenueCons) {
        //获取会员
        String mobile=reserveVenueCons.getConsMobile();//预订人手机号
        ReserveMember appMember=new ReserveMember();
        appMember.setMobile(mobile);
        ReserveMember consumer = reserveMemberService.get(appMember);//通过APP用户的手机号，判断该用户是否为场馆的会员
        ReserveStoredcardMemberSet card = null;
        //如果预订人是会员
        if (consumer != null) {
            //该场馆的会员
            if(reserveVenueCons.getReserveVenue().equals(consumer.getReserveVenue().getId())){
                //获取消费者的全部信息
                reserveVenueCons.setConsType("2");//会员
                //获取折扣卡
                card = consumer.getStoredcardSet();
            }
        }else{
            reserveVenueCons.setConsType("1");//散客
        }
        String halfCourt = reserveVenueCons.getHalfCourt();//半场
        String frequency = reserveVenueCons.getFrequency();//频率

        reserveVenueCons.preInsert();//生成主键
        List<ReserveVenueConsItem> itemList = reserveVenueCons.getVenueConsList();//订单的所有明细
        Double sum = 0D;//订单价格
        Double filedSum = 0D;//场地应收
        Date consDate = reserveVenueCons.getConsDate();//预订日期
        String consWeek = TimeUtils.getWeekOfDate(consDate);//周次
        for (ReserveVenueConsItem item : itemList) {
            item.setConsDate(consDate);//预订时间
            item.setReserveVenue(reserveVenueCons.getReserveVenue());//订单详情保存场馆
            item.setConsData(reserveVenueCons);//订单
            item.setConsWeek(consWeek);//设置周次
            item.setHalfCourt(halfCourt);//设置半场
            item.setFrequency(frequency);//设置频率
            Double price = null;//订单明细的价格
            //会员无打折卡
            if (card == null) {
                //门市价
                price = reserveFieldPriceService.getPrice(item.getReserveField(), reserveVenueCons.getConsType(),
                        reserveVenueCons.getConsDate(), item.getStartTime(), item.getEndTime());
            } else {
                // "1"代表门市价 在门市价的基础上进行打折
                price = reserveFieldPriceService.getPrice(item.getReserveField(), "1", reserveVenueCons.getConsDate(), item.getStartTime(), item.getEndTime());
                //获取折扣比率
                Double rate = reserveFieldPriceService.getMemberDiscountRate(reserveVenueCons.getMember());
                if (rate != null && rate != 0) {
                    price = price * rate * 0.01;
                }
            }
            item.setOrderPrice(price);//订单明细 场地应收
            filedSum+=price;
            item.setConsPrice(price);//订单明细 应收金额=场地应收+教练费
            item.preInsert();
            reserveVenueConsItemDao.insert(item);//保存预订信息
            sum += price;
        }
        reserveVenueCons.setByPC("0");//通过APP预订的
        reserveVenueCons.setOrderPrice(filedSum);//场地应收金额
        reserveVenueCons.setShouldPrice(sum);//订单应收：没有优惠券，应收等于订单金额+教练费用
        reserveVenueConsDao.insert(reserveVenueCons);//订单价格更改
        return reserveVenueCons.getId();
    }

    @Transactional(readOnly = false)
    public void delete(ReserveVenueCons reserveVenueCons) {
        super.delete(reserveVenueCons);
    }

    /**
     * 结账
     * id:订单编号
     * payType:支付类型
     * consPrice：实收金额
     * @param
     */
    @Transactional(readOnly = false)
    public boolean saveSettlement(ReserveVenueCons reserveVenueCons, String payType, Double consPrice,
                                           Double memberCardInput,
                                           Double bankCardInput,
                                           Double weiXinInput,
                                           Double aliPayInput,
                                           Double couponInput) {
        reserveVenueCons.setPayType(payType);
        reserveVenueCons.setMemberCardInput(memberCardInput);
        reserveVenueCons.setBankCardInput(bankCardInput);
        reserveVenueCons.setWeiXinInput(weiXinInput);
        reserveVenueCons.setAliPayInput(aliPayInput);
        reserveVenueCons.setCouponInput(couponInput);
        reserveVenueCons.setConsPrice(consPrice);//结算价格

        //reserveType:1:已预定,未付款;payType:1:会员卡;
        if ("1".equals(reserveVenueCons.getReserveType())) {
            reserveVenueCons.setReserveType("4");//已结账
            reserveVenueCons.preUpdate();
            dao.update(reserveVenueCons);
            //会员扣款;结算教练(事件通知)
            String phone=reserveVenueCons.getConsMobile();
            if(StringUtils.isNoneEmpty(phone)){
                ReserveMember member=new ReserveMember();
                member.setMobile(phone);
                List<ReserveMember> list = reserveMemberService.findExactList(member);
                for(ReserveMember i: list){
                    member=i;
                }
                reserveVenueCons.setMember(member);
            }
            VenueCheckoutEvent venueCheckoutEvent = new VenueCheckoutEvent(reserveVenueCons);
            applicationContext.publishEvent(venueCheckoutEvent);
            return true;
        }
        return false;//已结算，不可重复结算
    }

    /**
     * 取消预定
     *
     * @param venueCons 订单
     * @return
     */
    @Transactional(readOnly = false)
    public void cancelOrder(ReserveVenueCons venueCons) {
        dao.delete(venueCons);//删除订单
        ReserveVenueConsItem item=new ReserveVenueConsItem();
        item.setConsData(venueCons);
        List<ReserveVenueConsItem> itemList = reserveVenueConsItemDao.findList(item);
        for(ReserveVenueConsItem i:itemList){
            reserveVenueConsItemDao.delete(i);//删除订单明细
        }
    }
}