package com.bra.modules.reserve.listener.venue;

import com.bra.modules.reserve.entity.*;
import com.bra.modules.reserve.event.venue.VenueCheckoutEvent;
import com.bra.modules.reserve.service.ReserveCardStatementsService;
import com.bra.modules.reserve.service.ReserveMultiplePaymentService;
import com.bra.modules.reserve.service.ReserveVenueConsItemService;
import com.bra.modules.reserve.service.StoredCardMemberService;
import com.bra.modules.reserve.utils.TimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * 会员结账事件
 * Created by jiangxingqi on 16/1/22.
 */
@Component
public class MemberCheckoutListener{

    @Autowired
    private StoredCardMemberService storedCardMemberService;
    @Autowired
    private ReserveVenueConsItemService reserveVenueConsItemService;
    @Autowired
    private ReserveCardStatementsService reserveCardStatementsService;
    @Autowired
    private ReserveMultiplePaymentService reserveMultiplePaymentService;

    @EventListener
    public void onEvent(VenueCheckoutEvent event) {
        ReserveVenueCons venueCons = event.getReserveVenueCons();
        ReserveMember reserveMember=venueCons.getMember();
        ReserveCardStatements statements = new ReserveCardStatements();
        reserveMember = storedCardMemberService.get(reserveMember);//获得余额,如果是散客，reserveMember等于null
        //选择会员卡
        if ("1".equals(venueCons.getPayType()) && reserveMember != null) {
            if (reserveMember.getRemainder() == null) {
                reserveMember.setRemainder(0D);
            }
            reserveMember.setRemainder(reserveMember.getRemainder() - venueCons.getConsPrice());//会员卡扣款
            storedCardMemberService.save(reserveMember);

        }
        /*System.out.println("开始记录日志");*/
        //记录日志
        String payType=venueCons.getPayType();
        Date consDate=venueCons.getConsDate();
        statements.setCreateDate(consDate);//createTime:订单与结算的日期保持一致， 即使第2天结算，也会将流水计入订单的日期，updateTime:系统的扣款时间，该时间用于个人交易明细的排序
        statements.setOrderId(venueCons.getId());
        statements.setVenue(venueCons.getReserveVenue());
        statements.setTransactionType("8");//场地消费
        statements.setReserveMember(reserveMember);//余额加入日志
        String remarks=venueCons.getRemarks();
        statements.setRemarks(remarks);
        ReserveVenueConsItem search = new ReserveVenueConsItem();
        search.setConsData(venueCons);
        List<ReserveVenueConsItem> itemList = reserveVenueConsItemService.findList(search);
        int num=0;
        for(ReserveVenueConsItem i:itemList){
            String start=i.getStartTime()+":00";
            String end=i.getEndTime()+":00";
            num= TimeUtils.getTimeSpac(start,end,30);
        }
        statements.setTransactionNum(num);//预订了几个 半小时

        if("8".equals(payType)){//多方式付款
            Double memberCardInput=venueCons.getMemberCardInput();//会员卡
            Double cashInput=venueCons.getCashInput();//现金
            Double bankCardInput=venueCons.getBankCardInput();//银行卡
            Double weiXinInput=venueCons.getWeiXinInput();//微信
            Double weiXinPersonalInput=venueCons.getWeiXinPersonalInput();//微信个人
            Double aliPayInput=venueCons.getAliPayInput();//支付宝
            Double aliPayPersonalInput=venueCons.getAliPayPersonalInput();//支付宝个人
            Double couponInput=venueCons.getCouponInput();//优惠券
            ReserveField field=venueCons.getVenueConsList().get(0).getReserveField();
            String orderId=venueCons.getId();
            if(memberCardInput!=null && memberCardInput!=0){
                //会员卡扣款
                if (reserveMember.getRemainder() == null) {
                    reserveMember.setRemainder(0D);
                }
                reserveMember.setRemainder(reserveMember.getRemainder() - memberCardInput);
                storedCardMemberService.save(reserveMember);//修改余额

                statements.setReserveMember(reserveMember);//余额加入日志
                statements.setPayType("1");
                statements.setTransactionVolume(memberCardInput);
                statements.setId(null);
                reserveCardStatementsService.save(statements);
                //记录多方式付款
                this.saveMultiplePayment(orderId,field,"1",memberCardInput,remarks);
            }
            if(cashInput!=null && cashInput!=0){
                statements.setPayType("2");
                statements.setTransactionVolume(cashInput);
                statements.setId(null);
                reserveCardStatementsService.save(statements);
                //记录多方式付款
                this.saveMultiplePayment(orderId,field,"2",cashInput,remarks);
            }
            if(bankCardInput!=null && bankCardInput!=0){
                statements.setPayType("3");
                statements.setTransactionVolume(bankCardInput);
                statements.setId(null);
                reserveCardStatementsService.save(statements);
                //记录多方式付款
                this.saveMultiplePayment(orderId,field,"3",bankCardInput,remarks);
            }
            if(weiXinInput!=null && weiXinInput!=0){
                statements.setPayType("4");
                statements.setTransactionVolume(weiXinInput);
                statements.setId(null);
                reserveCardStatementsService.save(statements);
                //记录多方式付款
                this.saveMultiplePayment(orderId,field,"4",weiXinInput,remarks);
            }
            if(weiXinPersonalInput!=null && weiXinPersonalInput!=0){
                statements.setPayType("9");
                statements.setTransactionVolume(weiXinPersonalInput);
                statements.setId(null);
                reserveCardStatementsService.save(statements);
                //记录多方式付款
                this.saveMultiplePayment(orderId,field,"9",weiXinPersonalInput,remarks);
            }
            if(aliPayInput!=null && aliPayInput!=0){
                statements.setPayType("5");
                statements.setTransactionVolume(aliPayInput);
                statements.setId(null);
                reserveCardStatementsService.save(statements);
                //记录多方式付款
                this.saveMultiplePayment(orderId,field,"5",aliPayInput,remarks);
            }
            if(aliPayPersonalInput!=null && aliPayPersonalInput!=0){
                statements.setPayType("10");
                statements.setTransactionVolume(aliPayInput);
                statements.setId(null);
                reserveCardStatementsService.save(statements);
                //记录多方式付款
                this.saveMultiplePayment(orderId,field,"10",aliPayPersonalInput,remarks);
            }
            if(couponInput!=null && couponInput!=0){
                statements.setPayType("6");
                statements.setTransactionVolume(couponInput);
                statements.setId(null);
                reserveCardStatementsService.save(statements);
                this.saveMultiplePayment(orderId,field,"6",couponInput,remarks);
            }
        }else{
            statements.setPayType(payType);
            statements.setTransactionVolume(venueCons.getConsPrice());
            reserveCardStatementsService.save(statements);
        }


    }
    public void saveMultiplePayment(String orderId,ReserveField field,String payType,Double transactionVolume,String remarks){
        //记录多方式付款
        ReserveMultiplePayment payment=new ReserveMultiplePayment();
        payment.setOrderId(orderId);
        payment.setField(field);
        payment.setPaymentAmount(transactionVolume);
        payment.setPayType(payType);
        payment.setRemarks(remarks);
        reserveMultiplePaymentService.save(payment);
    }
    protected int getOrder() {
        return 1;
    }
}
