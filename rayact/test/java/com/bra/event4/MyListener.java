package com.bra.event4;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * Created by xiaobin on 16/1/22.
 */
@Component
public class MyListener {

    @Autowired
    private ExternalNotificationSender externalNotificationSender;

    @EventListener(condition = "#blogModifiedEvent.importantChange==ListerType.F")
    public void blogModified(BlogModifiedEvent blogModifiedEvent) {
        externalNotificationSender.blogModified(blogModifiedEvent);
        System.out.println("-------");
    }


}
