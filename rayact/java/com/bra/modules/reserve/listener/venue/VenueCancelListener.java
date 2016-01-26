package com.bra.modules.reserve.listener.venue;

import com.bra.modules.reserve.event.venue.VenueCancelEvent;
import org.springframework.stereotype.Component;

/**
 * 取消预定监听
 * Created by xiaobin on 16/1/22.
 */
@Component
public class VenueCancelListener{
    protected int getOrder() {
        return 0;
    }

    protected void onEvent(VenueCancelEvent event) {

    }
}
