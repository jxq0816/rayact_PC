package com.bra.common.event;


/**
 * 事件驱动接口
 * Created by xiaobin on 16/1/22.
 */
public abstract class IEvent {

    private boolean status;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
