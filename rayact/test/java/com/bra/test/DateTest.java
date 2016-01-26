package com.bra.test;

import com.bra.common.utils.DateUtils;
import junit.framework.TestCase;

import java.text.ParseException;
import java.util.Date;

/**
 * Created by xiaobin on 16/1/5.
 */
public class DateTest extends TestCase {

    public void testRun() throws ParseException {
        Date startTime = DateUtils.parseDate("16:43:00", "HH:mm:ss");
        Date endTime = DateUtils.parseDate("18:43:00", "HH:mm:ss");
        long t = (endTime.getTime() - startTime.getTime()) / (60 * 60 * 1000);
        System.out.println(t);

        System.out.println(DateUtils.formatDate(DateUtils.addHours(startTime, 1), "HH:mm"));

    }
}
