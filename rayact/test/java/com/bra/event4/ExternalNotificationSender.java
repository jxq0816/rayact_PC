package com.bra.event4;

import org.springframework.stereotype.Component;

/**
 * Created by xiaobin on 16/1/22.
 */
@Component
public class ExternalNotificationSender {

    public void blogModified(BlogModifiedEvent blogModifiedEvent){
        //blogModifiedEvent.setImportantChange(true) ;
    }
}
