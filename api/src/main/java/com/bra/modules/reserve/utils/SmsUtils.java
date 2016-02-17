package com.bra.modules.reserve.utils;

import java.util.Random;

/**
 * Created by xiaobin on 16/2/17.
 */
public class SmsUtils {

    public static String getMobileCode(int count) {
        // int count = 5;
        StringBuffer sb = new StringBuffer();
        String str = "0123456789";
        Random random = new Random();
        for (int i = 0; i < count; i++) {
            int num = random.nextInt(str.length());
            sb.append(str.charAt(num));
            str.replace((str.charAt(num) + ""), "");
        }
        return sb.toString();
        // System.out.println(sb.toString());
    }
}
