package com.bra.modules.util.pingplusplus;

import com.bra.common.utils.SystemPath;
import com.pingplusplus.Pingpp;
import com.pingplusplus.exception.PingppException;
import com.pingplusplus.model.Charge;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Afon on 16/4/26.
 */
public class PingPlusCharge {

    /**
     * Pingpp 管理平台对应的 API Key
     */
    private final static String apiKey = "sk_test_D8eX5C4GiHC4azzbL8zXjLS8";

    /**
     * Pingpp 管理平台对应的应用 ID
     */
    private final static String appId = "app_5aX5q9j90O8KyPCu";

    /**
     * 你生成的私钥路径
     */
    private final static String privateKeyFilePath = SystemPath.getSysPath()+"../../src/"+"res/your_rsa_private_key.pem";

    public static String charge(String orderNo,int amount,String subject,String body,String channel,String clientIP){

        // 设置 API Key
        Pingpp.apiKey = apiKey;

        // 设置私钥路径，用于请求签名
        Pingpp.privateKeyPath = privateKeyFilePath;

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
        chargeMap.put("client_ip", clientIP); // 客户端 ip 地址(ipv4)
        Map<String, String> app = new HashMap<String, String>();
        app.put("id", appId);
        chargeMap.put("app", app);
        String chargeString=null;
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

    private static SecureRandom random = new SecureRandom();

    public static String randomString(int length) {
        String str = new BigInteger(130, random).toString(32);
        return str.substring(0, length);
    }
}
