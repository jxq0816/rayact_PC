package com.bra.modules.reserve.service;

import com.bra.common.persistence.Page;
import com.bra.common.service.CrudService;
import com.bra.modules.reserve.dao.ReserveVenueConsDao;
import com.bra.modules.reserve.dao.ReserveVenueConsItemDao;
import com.bra.modules.reserve.entity.*;
import com.bra.modules.reserve.utils.TimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

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
    private ReserveUserService reserveUserService;
    @Autowired
    private StoredCardMemberService storedCardMemberService;
    @Autowired
    private ReserveVenueConsItemDao reserveVenueConsItemDao;
    @Autowired
    private ReserveFieldPriceService reserveFieldPriceService;
    @Autowired
    private ReserveTutorService reserveTutorService;
    @Autowired
    private ReserveCardStatementsService reserveCardStatementsService;
    @Autowired
    private ReserveMemberService reserveMemberService;
    @Autowired
    private ReserveVenueConsItemService reserveVenueConsItemService;
    @Autowired
    private ReserveVenueApplyCutService reserveVenueApplyCutService;
    @Autowired
    private ReserveTutorOrderService reserveTutorOrderService;


    public ReserveVenueCons get(String id) {
        ReserveVenueCons reserveVenueCons=super.get(id);
        //教练订单
        List<ReserveTutorOrder> tutorOrderList = reserveTutorOrderService.findNotCancel(reserveVenueCons.getId(), ReserveVenueCons.MODEL_KEY);
        reserveVenueCons.setTutorOrderList(tutorOrderList);
        //订单详情
        ReserveVenueConsItem item=new ReserveVenueConsItem();
        item.setConsData(reserveVenueCons);
        reserveVenueCons.setVenueConsList(reserveVenueConsItemService.findList(item));
        return reserveVenueCons;
    }



    public Page<ReserveVenueCons> findPage(Page<ReserveVenueCons> page, ReserveVenueCons reserveVenueCons) {
        return super.findPage(page, reserveVenueCons);
    }

    public List<ReserveVenueCons> findListOrder(ReserveVenueCons venueCons) {
        return dao.findListOrder(venueCons);
    }


    /**
     * 存储预定单据
     *
     * @param reserveVenueCons
     */
    @Transactional(readOnly = false)
    public void save(ReserveVenueCons reserveVenueCons) {
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

        reserveVenueCons.preInsert();
        dao.insert(reserveVenueCons);//保存订单
        List<ReserveVenueConsItem> itemList = reserveVenueCons.getVenueConsList();//订单的所有明细
        Double sum = 0D;//订单价格
        Double filedSum = 0D;//场地应收
        Date consDate = reserveVenueCons.getConsDate();//预订日期
        String consWeek = TimeUtils.getWeekOfDate(consDate);//周次
        for (ReserveVenueConsItem item : itemList) {
            item.setConsDate(consDate);//预订时间
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
        reserveVenueCons.setOrderPrice(filedSum);//场地应收金额
        reserveVenueCons.setShouldPrice(sum);//订单应收：没有优惠券，应收等于订单金额+教练费用
        dao.update(reserveVenueCons);//订单价格更改
    }

    @Transactional(readOnly = false)
    public void delete(ReserveVenueCons reserveVenueCons) {
        super.delete(reserveVenueCons);
    }

}