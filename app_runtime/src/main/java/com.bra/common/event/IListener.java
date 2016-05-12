package com.bra.common.event;


/**
 * Created by xiaobin on 16/1/22.
 */
public abstract class IListener<T extends IEvent>  {

    protected abstract int getOrder();

    protected abstract void onEvent(T event);

}
