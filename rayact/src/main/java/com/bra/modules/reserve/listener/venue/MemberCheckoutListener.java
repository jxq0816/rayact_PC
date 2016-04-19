package com.bra.modules.reserve.listener.venue;

import com.bra.modules.reserve.entity.ReserveCardStatements;
import com.bra.modules.reserve.entity.ReserveMember;
import com.bra.modules.reserve.entity.ReserveVenueCons;
import com.bra.modules.reserve.entity.ReserveVenueConsItem;
import com.bra.modules.reserve.event.venue.VenueCheckoutEvent;
import com.bra.modules.reserve.service.ReserveCardStatementsService;
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
        ReserveCardStatements card = new ReserveCardStatements();
        card.setVenue(venueCons.getReserveVenue());
        card.setTransactionVolume(venueCons.getConsPrice());
        card.setPayType(venueCons.getPayType());
        card.setCreateDate(new Date());
        card.setTransactionType("8");//场地收入
        card.setReserveMember(venueCons.getMember());
        card.setRemarks("场地收入");

        ReserveVenueConsItem search = new ReserveVenueConsItem();
        search.setConsData(venueCons);
        List<ReserveVenueConsItem> itemList = reserveVenueConsItemService.findList(search);
        int num=0;
        for(ReserveVenueConsItem i:itemList){
            String start=i.getStartTime()+":00";
            String end=i.getEndTime()+":00";
            num= TimeUtils.getTimeSpac(start,end,30);
        }
        card.setTransactionNum(num);//预订了几个 半小时
        reserveCardStatementsService.save(card);
    }

    protected int getOrder() {
        return 1;
    }
}
