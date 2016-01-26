package com.bra.modules.reserve.listener.venue;

import com.bra.modules.reserve.entity.ReserveMember;
import com.bra.modules.reserve.entity.ReserveVenueCons;
import com.bra.modules.reserve.event.venue.VenueCheckoutEvent;
import com.bra.modules.reserve.service.StoredCardMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * 会员结账事件
 * Created by xiaobin on 16/1/22.
 */
@Component
public class MemberCheckoutListener{

    @Autowired
    private StoredCardMemberService storedCardMemberService;

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
            reserveMember.setRemainder(reserveMember.getRemainder() - venueCons.getOrderPrice());
            storedCardMemberService.save(reserveMember);
        }
    }

    protected int getOrder() {
        return 1;
    }
}
