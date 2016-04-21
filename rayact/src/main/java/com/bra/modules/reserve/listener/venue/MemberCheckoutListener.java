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
 * Created by xiaobin on 16/1/22.
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
        //选择会员卡
        if ("1".equals(venueCons.getPayType()) && venueCons.getMember() != null) {
            //会员卡扣款
            ReserveMember reserveMember = storedCardMemberService.get(venueCons.getMember().getId());
            if (reserveMember.getRemainder() == null) {
                reserveMember.setRemainder(0D);
            }
            reserveMember.setRemainder(reserveMember.getRemainder() - venueCons.getConsPrice());
            storedCardMemberService.save(reserveMember);
            venueCons.setMember(reserveMember);//余额加入日志
        }
        //记录日志
        String payType=venueCons.getPayType();
        ReserveCardStatements statements = new ReserveCardStatements();
        statements.setVenue(venueCons.getReserveVenue());
        statements.setCreateDate(new Date());
        statements.setTransactionType("8");//场地收入
        statements.setReserveMember(venueCons.getMember());
        statements.setRemarks("场地收入");

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
            Double memberCardInput=venueCons.getMemberCardInput();
            Double cashInput=venueCons.getCashInput();
            Double bankCardInput=venueCons.getBankCardInput();
            Double weiXinInput=venueCons.getWeiXinInput();
            Double aliPayInput=venueCons.getAliPayInput();
            Double couponInput=venueCons.getCouponInput();
            Double owningInput=venueCons.getOwningInput();
            String orderId=venueCons.getId();
            if(memberCardInput!=null && memberCardInput!=0){
                statements.setPayType("1");
                statements.setTransactionVolume(memberCardInput);
                statements.setId(null);
                reserveCardStatementsService.save(statements);
                //记录多方式付款
                ReserveMultiplePayment payment=new ReserveMultiplePayment();
                payment.setOrderId(orderId);
                payment.setPaymentAmount(memberCardInput);
                payment.setPayType("1");
                payment.setRemarks(venueCons.getMember().getName()+"，于"+venueCons.getConsDate()+"会员卡支付"+memberCardInput);
                reserveMultiplePaymentService.save(payment);
            }
            if(cashInput!=null && cashInput!=0){
                statements.setPayType("2");
                statements.setTransactionVolume(cashInput);
                statements.setId(null);
                reserveCardStatementsService.save(statements);
                //记录多方式付款
                ReserveMultiplePayment payment=new ReserveMultiplePayment();
                payment.setOrderId(orderId);
                payment.setPaymentAmount(cashInput);
                payment.setPayType("2");
                payment.setRemarks(venueCons.getMember().getName()+"，于"+venueCons.getConsDate()+"现金支付"+cashInput);
                reserveMultiplePaymentService.save(payment);
            }
            if(bankCardInput!=null && bankCardInput!=0){
                statements.setPayType("3");
                statements.setTransactionVolume(bankCardInput);
                statements.setId(null);
                reserveCardStatementsService.save(statements);
                //记录多方式付款
                ReserveMultiplePayment payment=new ReserveMultiplePayment();
                payment.setOrderId(orderId);
                payment.setPaymentAmount(bankCardInput);
                payment.setPayType("3");
                payment.setRemarks(venueCons.getMember().getName()+"，于"+venueCons.getConsDate()+"银行卡支付"+bankCardInput);
                reserveMultiplePaymentService.save(payment);
            }
            if(weiXinInput!=null && weiXinInput!=0){
                statements.setPayType("4");
                statements.setTransactionVolume(weiXinInput);
                statements.setId(null);
                reserveCardStatementsService.save(statements);
                //记录多方式付款
                ReserveMultiplePayment payment=new ReserveMultiplePayment();
                payment.setOrderId(orderId);
                payment.setPaymentAmount(bankCardInput);
                payment.setPayType("4");
                payment.setRemarks(venueCons.getMember().getName()+"，于"+venueCons.getConsDate()+"微信支付"+weiXinInput);
                reserveMultiplePaymentService.save(payment);
            }
            if(aliPayInput!=null && aliPayInput!=0){
                statements.setPayType("5");
                statements.setTransactionVolume(aliPayInput);
                statements.setId(null);
                reserveCardStatementsService.save(statements);
                //记录多方式付款
                ReserveMultiplePayment payment=new ReserveMultiplePayment();
                payment.setOrderId(orderId);
                payment.setPaymentAmount(aliPayInput);
                payment.setPayType("5");
                payment.setRemarks(venueCons.getMember().getName()+"，于"+venueCons.getConsDate()+"支付宝支付"+aliPayInput);
                reserveMultiplePaymentService.save(payment);
            }
            if(couponInput!=null && couponInput!=0){
                statements.setPayType("6");
                statements.setTransactionVolume(couponInput);
                statements.setId(null);
                reserveCardStatementsService.save(statements);
                //记录多方式付款
                ReserveMultiplePayment payment=new ReserveMultiplePayment();
                payment.setOrderId(orderId);
                payment.setPaymentAmount(couponInput);
                payment.setPayType("6");
                payment.setRemarks(venueCons.getMember().getName()+"，于"+venueCons.getConsDate()+"优惠券支付"+couponInput);
                reserveMultiplePaymentService.save(payment);
            }
            if(owningInput!=null && owningInput!=0){
                statements.setPayType("7");
                statements.setTransactionVolume(owningInput);
                statements.setId(null);
                reserveCardStatementsService.save(statements);
                //记录多方式付款
                ReserveMultiplePayment payment=new ReserveMultiplePayment();
                payment.setOrderId(orderId);
                payment.setPaymentAmount(owningInput);
                payment.setPayType("7");
                payment.setRemarks(venueCons.getMember().getName()+"，于"+venueCons.getConsDate()+"打白条"+owningInput);
                reserveMultiplePaymentService.save(payment);
            }
        }else{
            statements.setPayType(payType);
            statements.setTransactionVolume(venueCons.getConsPrice());
            reserveCardStatementsService.save(statements);
        }


    }

    protected int getOrder() {
        return 1;
    }
}
