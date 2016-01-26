package com.bra.event4;

import com.bra.common.test.SpringTransactionalContextTests;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

/**
 * Created by xiaobin on 16/1/22.
 */
public class SendEvent extends SpringTransactionalContextTests {

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    public void testPublishEvent() {
        Blog blog = new Blog();
        blog.setName("ss");
        BlogModifiedEvent event = new BlogModifiedEvent(blog);
        event.setImportantChange(ListerType.M);
        applicationContext.publishEvent(event);
        System.out.println(event.getImportantChange());
    }
}
