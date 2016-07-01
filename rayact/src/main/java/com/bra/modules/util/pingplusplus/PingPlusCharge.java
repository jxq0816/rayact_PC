package com.bra.modules.util.pingplusplus;

import com.pingplusplus.exception.PingppException;
import com.pingplusplus.model.Charge;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Afon on 16/4/26.
 */
public class PingPlusCharge {

    private String appId;


    PingPlusCharge(String appId) {
        this.appId = appId;
    }

    public String createCharge(String orderNo, int amount, String subject, String body, String channel, String clientIP) {

        /**
         * 或者直接设置私钥内容
         Pingpp.privateKey = "-----BEGIN RSA PRIVATE KEY-----\n" +
         "... 私钥内容字符串 ...\n" +
         "-----END RSA PRIVATE KEY-----\n";
         */
        Map<String, Object> chargeMap = new HashMap<String, Object>();
        chargeMap.put("amount", amount);
        chargeMap.put("currency", "cny");
        chargeMap.put("subject", subject);
        chargeMap.put("body", body);
        chargeMap.put("order_no", orderNo);
        chargeMap.put("channel", channel);

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MINUTE, 15);//15分钟失效
        long timestamp = cal.getTimeInMillis()/ 1000L;
        chargeMap.put("time_expire", timestamp);

        chargeMap.put("client_ip", clientIP); // 客户端 ip 地址(ipv4)
        Map<String, String> app = new HashMap<String, String>();
        app.put("id", appId);
        chargeMap.put("app", app);
        String chargeString = null;
        try {
            //发起交易请求
            Charge charge = Charge.create(chargeMap);
            // 传到客户端请先转成字符串 .toString(), 调该方法，会自动转成正确的 JSON 字符串
            chargeString = charge.toString();
        } catch (PingppException e) {
            e.printStackTrace();
        }
        return chargeString;
    }
}
