package com.bra.common.event;

import org.springframework.util.ErrorHandler;

/**
 * Created by xiaobin on 16/1/22.
 */
public class EventErrorHandler implements ErrorHandler {
    @Override
    public void handleError(Throwable throwable) {
        throw new RuntimeException(throwable);
    }

    public void handleError(String error){
        throw new RuntimeException(error);
    }
}
