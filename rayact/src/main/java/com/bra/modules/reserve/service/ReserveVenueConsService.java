package com.bra.modules.reserve.service;

import com.bra.common.persistence.Page;
import com.bra.common.service.CrudService;
import com.bra.common.utils.DateUtils;
import com.bra.modules.reserve.dao.ReserveVenueConsDao;
import com.bra.modules.reserve.dao.ReserveVenueConsItemDao;
import com.bra.modules.reserve.entity.*;
import com.bra.modules.reserve.event.venue.VenueCancelEvent;
import com.bra.modules.reserve.event.venue.VenueCheckoutEvent;
import com.bra.modules.reserve.event.venue.VenueReserveEvent;
import com.bra.modules.reserve.utils.AuthorityUtils;
import com.bra.modules.reserve.utils.TimeUtils;
import com.bra.modules.reserve.web.form.SaleVenueLog;
import com.bra.modules.sys.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
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
public class ReserveVenueConsService extends CrudService<ReserveVenueConsDao, ReserveVenueCons> {
    @Autowired
    private ReserveMemberService reserveMemberService;
    @Autowired
    private StoredCardMemberService storedCardMemberService;
    @Autowired
    private ReserveVenueConsItemDao reserveVenueConsItemDao;
    @Autowired
    private ReserveFieldPriceService reserveFieldPriceService;
    @Autowired
    private ReserveTutorService reserveTutorService;
    @Autowired
    private ReserveVenueConsItemService reserveVenueConsItemService;
    @Autowired
    private ReserveVenueApplyCutService reserveVenueApplyCutService;
    @Autowired
    private ReserveTutorOrderService reserveTutorOrderService;
    @Autowired
    private  ReserveCardStatementsService reserveCardStatementsService;

    public Page<SaleVenueLog> findOrderLog(Page<SaleVenueLog> page, SaleVenueLog venueLog) {
        if (venueLog != null) {
            if (venueLog.getSqlMap().get("dsf") == null)
                venueLog.getSqlMap().put("dsf", AuthorityUtils.getDsf("a.venue_id"));
        }
        venueLog.setPage(page);
        List<SaleVenueLog> list = dao.findOrderLog(venueLog);
        page.setList(list);
        return page;
    }
    public List<SaleVenueLog> findOrderLogList(SaleVenueLog venueLog) {
        if (venueLog != null) {
            if (venueLog.getSqlMap().get("dsf") == null)
                venueLog.getSqlMap().put("dsf", AuthorityUtils.getDsf("a.venue_id"));
        }
        List<SaleVenueLog> list = dao.findOrderLog(venueLog);
        return list;
    }

    public List<Map<String, Object>> findPriceGroupProject(ReserveVenueCons venueCons) {
        List<Map<String, Object>> list = dao.findPriceGroupProject(venueCons);
        return list;
    }

    public List<Map<String, Object>> findPriceGroupProjectReport(ReserveVenueCons venueCons) {
        venueCons.getSqlMap().put("dsf", AuthorityUtils.getDsf("a.venue_id"));
        return dao.findPriceGroupProjectReport(venueCons);
    }

    /**
     * 结账
     * id:订单编号
     * payType:支付类型
     * authUserId:授权人编号
     * discountPrice:优惠金额
     * consPrice：实收金额
     * @param
     */
    @Transactional(readOnly = false)
    public ReserveVenueCons saveSettlement(String id, String payType, String authUserId, Double discountPrice, Double consPrice,
                                          Double memberCardInput,
                                          Double cashInput,
                                          Double bankCardInput,
                                          Double weiXinInput,
                                           Double weiXinPersonalInput,
                                          Double aliPayInput,
                                           Double aliPayPersonalInput,
                                          Double couponInput,
                                          String remarks) {

        ReserveVenueCons reserveVenueCons = this.get(id);
        reserveVenueCons.setPayType(payType);
        reserveVenueCons.setMemberCardInput(memberCardInput);
        reserveVenueCons.setCashInput(cashInput);
        reserveVenueCons.setBankCardInput(bankCardInput);
        reserveVenueCons.setWeiXinInput(weiXinInput);
        reserveVenueCons.setWeiXinPersonalInput(weiXinPersonalInput);
        reserveVenueCons.setAliPayInput(aliPayInput);
        reserveVenueCons.setAliPayPersonalInput(aliPayPersonalInput);
        reserveVenueCons.setCouponInput(couponInput);
        reserveVenueCons.setRemarks(remarks);
        User checkOutUser = new User();
        checkOutUser.setId(authUserId);
        reserveVenueCons.setCheckOutUser(checkOutUser);//授权人
        reserveVenueCons.setDiscountPrice(discountPrice);//优惠
        reserveVenueCons.setConsPrice(consPrice);//结算价格
        //修改item价格
        ReserveVenueConsItem search = new ReserveVenueConsItem();
        search.setConsData(reserveVenueCons);
        List<ReserveVenueConsItem> itemList = reserveVenueConsItemService.findList(search);
        if(itemList!=null&&itemList.size()>0){
            ReserveVenueConsItem item = itemList.get(0);
            item.setConsPrice(item.getConsPrice()-discountPrice);
            reserveVenueConsItemService.save(item);
        }
        //reserveType:1:已预定;payType:1:会员卡;
        if ("1".equals(reserveVenueCons.getReserveType())) {
            reserveVenueCons.setReserveType("4");
            reserveVenueCons.preUpdate();
            dao.update(reserveVenueCons);
            //会员扣款;结算教练(事件通知)
            VenueCheckoutEvent venueCheckoutEvent = new VenueCheckoutEvent(reserveVenueCons);
            applicationContext.publishEvent(venueCheckoutEvent);
            //清空优惠申请
            ReserveVenueApplyCut cut = new ReserveVenueApplyCut();
            cut.getSqlMap().put("dsf"," and c.id = '"+ reserveVenueCons.getId()+"' ");
            List<ReserveVenueApplyCut> list = reserveVenueApplyCutService.findList(cut);
            if(list!=null){
                for(ReserveVenueApplyCut cc:list){
                    cc.setDelFlag("1");
                    cc.setDone("1");
                    reserveVenueApplyCutService.save(cc);
                }
            }
            return reserveVenueCons;
        }
        return null;
    }

    public ReserveVenueCons get(String id) {
        ReserveVenueCons reserveVenueCons=super.get(id);
        ReserveMember member=reserveMemberService.get(reserveVenueCons.getMember());
        reserveVenueCons.setMember(member);//获得会员余额
        //教练订单
        List<ReserveTutorOrder> tutorOrderList = reserveTutorOrderService.findNotCancel(reserveVenueCons.getId(), ReserveVenueCons.MODEL_KEY);
        reserveVenueCons.setTutorOrderList(tutorOrderList);
        //订单详情
        ReserveVenueConsItem item=new ReserveVenueConsItem();
        item.setConsData(reserveVenueCons);
        reserveVenueCons.setVenueConsList(reserveVenueConsItemService.findList(item));
        return reserveVenueCons;
    }

    public List<Map<String, Object>> sellOfHistogram(ReserveVenueCons venueCons) {
        return dao.sellOfHistogram(venueCons);
    }

    public List<Map<String, Object>> sellOfChart(ReserveVenueCons venueCons) {
        return dao.sellOfChart(venueCons);
    }

    public BigDecimal sellMonthOfChart(ReserveVenueCons venueCons) {
        return dao.sellMonthOfChart(venueCons);
    }

    public List<ReserveVenueCons> findList(ReserveVenueCons reserveVenueCons) {
        List<ReserveVenueCons> list=super.findList(reserveVenueCons);
        for(ReserveVenueCons i:list){
            //教练订单
            List<ReserveTutorOrder> tutorOrderList = reserveTutorOrderService.findNotCancel(reserveVenueCons.getId(), ReserveVenueCons.MODEL_KEY);
            i.setTutorOrderList(tutorOrderList);
            //订单详情
            ReserveVenueConsItem item=new ReserveVenueConsItem();
            item.setConsData(reserveVenueCons);
            i.setVenueConsList(reserveVenueConsItemService.findList(item));
        }
        return list;
    }

    public Page<ReserveVenueCons> findPage(Page<ReserveVenueCons> page, ReserveVenueCons reserveVenueCons) {
        return super.findPage(page, reserveVenueCons);
    }

    public List<ReserveVenueCons> findListOrder(ReserveVenueCons venueCons) {
        return dao.findListOrder(venueCons);
    }

    /**
     * 查询空场率
     *
     * @param venueCons
     * @return
     */
    public List<Map<String, Object>> findOpenRateReport(ReserveVenueCons venueCons) {
        return dao.findOpenRateReport(venueCons);
    }

    /**
     * 存储预定单据
     *
     * @param reserveVenueCons
     */
    @Transactional(readOnly = false)
    public void save(ReserveVenueCons reserveVenueCons) {
        //获取会员
        ReserveMember consumer = reserveVenueCons.getMember();
        ReserveStoredcardMemberSet card = null;
        //如果预订人是会员
        if (consumer != null) {
            consumer = storedCardMemberService.get(consumer);
            if (consumer != null) {
                //获取折扣卡
                card = consumer.getStoredcardSet();
            }
        }
        String halfCourt = reserveVenueCons.getHalfCourt();//半场
        String frequency = reserveVenueCons.getFrequency();//频率
        reserveVenueCons.preInsert();
        reserveVenueCons.setByPC("1");
        dao.insert(reserveVenueCons);//保存订单
        List<ReserveVenueConsItem> itemList = reserveVenueCons.getVenueConsList();//订单的所有明细
        Double sum = 0D;//订单价格
        Double filedSum = 0D;//场地应收
        Date consDate = reserveVenueCons.getConsDate();//预订日期
        String consWeek = TimeUtils.getWeekOfDate(consDate);//周次
        for (ReserveVenueConsItem item : itemList) {
            item.setConsDate(consDate);//预订时间
            item.setConsData(reserveVenueCons);//订单
            item.setConsWeek(consWeek);
            item.setHalfCourt(halfCourt);//设置半场
            item.setFrequency(frequency);//设置频率
            Double price = null;//订单明细的价格
            //会员无打折卡
            if (card == null) {
                price = reserveFieldPriceService.getPrice(item.getReserveField(), reserveVenueCons.getConsType(),
                        reserveVenueCons.getConsDate(), item.getStartTime(), item.getEndTime());
            } else {
                // "1"代表门市价 在门市价的基础上进行打折
                price = reserveFieldPriceService.getPrice(item.getReserveField(), "1", reserveVenueCons.getConsDate(), item.getStartTime(), item.getEndTime());
                Double rate = reserveFieldPriceService.getMemberDiscountRate(reserveVenueCons.getMember());
                if (rate != null && rate != 0) {
                    price = price * rate * 0.01;
                }
            }
            filedSum+=price;
            //教练费不打折
            ReserveTutor tutor = reserveVenueCons.getTutor();
            tutor = reserveTutorService.get(tutor);
            int halfHourNum = 0;
            if (tutor != null) {
                double hourPrice = tutor.getPrice();
                String startTime = item.getStartTime() + ":00";
                String endTime = item.getEndTime() + ":00";
                List<String> timeIntervalList = TimeUtils.getTimeSpacListValue(startTime, endTime, 30);
                halfHourNum = timeIntervalList.size();
                double tutorConsume = halfHourNum * hourPrice / 2;
                price += tutorConsume;//订单明细价格增加教练费
            }
            item.setOrderPrice(filedSum);//订单明细的场地费用
            item.setConsPrice(price);//订单明细 应收金额=场地应收+教练费
            item.preInsert();
            reserveVenueConsItemDao.insert(item);//保存预订信息
            sum += price;
        }
        reserveVenueCons.setOrderPrice(filedSum);//场地应收金额
        reserveVenueCons.setShouldPrice(sum);//订单应收：没有优惠券，应收等于订单金额+教练费用
        dao.update(reserveVenueCons);//订单价格更改
        //预定教练
        List<String> timeList = TimeUtils.getTimeSpace(itemList.get(0).getStartTime(), itemList.get(0).getEndTime());
        applicationContext.publishEvent(new VenueReserveEvent(reserveVenueCons, timeList));//?????
    }

    /**
     * 取消预定
     *
     * @param itemId
     * @param tutorOrderId
     * @return
     */
    @Transactional(readOnly = false)
    public List<ReserveVenueConsItem> cancelReserve(String itemId, String
            tutorOrderId) {
        ReserveVenueConsItem item = reserveVenueConsItemDao.get(itemId);
        ReserveVenueCons venueCons = this.get(item.getConsData().getId());
        dao.delete(venueCons);//删除订单
        reserveVenueConsItemDao.delete(item);//删除订单明细
        ReserveVenueApplyCut cut = new ReserveVenueApplyCut();//删除优惠申请
        cut.getSqlMap().put("dsf"," and c.id = '"+venueCons.getId()+"' ");
        List<ReserveVenueApplyCut> list = reserveVenueApplyCutService.findList(cut);
        if(list!=null){
            for(ReserveVenueApplyCut c:list){
                c.setDone("1");
                reserveVenueApplyCutService.save(c);
            }
        }
        applicationContext.publishEvent(new VenueCancelEvent(venueCons, tutorOrderId));
        List<ReserveVenueConsItem> itemList = venueCons.getVenueConsList();//查询预订的订单详情
        return itemList;
    }

    @Transactional(readOnly = false)
    public void delete(ReserveVenueCons reserveVenueCons) {
        ReserveVenueConsItem item =new ReserveVenueConsItem();
        item.setConsData(reserveVenueCons);
        List<ReserveVenueConsItem> itemList=reserveVenueConsItemDao.findList(item);
        for(ReserveVenueConsItem i:itemList){
            reserveVenueConsItemDao.delete(i);
        }
        super.delete(reserveVenueCons);
        ReserveCardStatements statements = new ReserveCardStatements();
        statements.setOrderId(reserveVenueCons.getId());
        List<ReserveCardStatements> list = reserveCardStatementsService.findList(statements);
        for(ReserveCardStatements i:list){
            if("1".equals(i.getPayType())){//储值卡支付，应该把金额还给顾客
                ReserveMember reserveMember=i.getReserveMember();
                reserveMember=storedCardMemberService.get(reserveMember);
                reserveMember.setRemainder(reserveMember.getRemainder()+i.getTransactionVolume());
                storedCardMemberService.save(reserveMember);
            }
            reserveCardStatementsService.delete(i);
        }
        //记录 冲回金额
        if("1".equals(reserveVenueCons.getPayType())){
            ReserveCardStatements log = new ReserveCardStatements();
            log.setTransactionType("4");
            ReserveMember member=reserveVenueCons.getMember();
            member=reserveMemberService.get(member);
            log.setReserveMember(member);
            log.setRemarks(DateUtils.formatDate(reserveVenueCons.getConsDate())+"场次票 充回");
            reserveCardStatementsService.save(log);
        }
    }

}