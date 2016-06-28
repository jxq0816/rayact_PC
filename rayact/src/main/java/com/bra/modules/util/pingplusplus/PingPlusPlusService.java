package com.bra.modules.util.pingplusplus;

import com.bra.common.utils.SystemPath;
import com.pingplusplus.Pingpp;
import org.springframework.stereotype.Service;

import java.io.File;

/**
 * Created by Afon on 16/4/26.
 */
@Service
public class PingPlusPlusService {
    
    /**
     * Pingpp 管理平台对应的 API Key
     */
    //private final static String apiKey = "sk_test_D8eX5C4GiHC4azzbL8zXjLS8";
    private final static String apiKey = "sk_live_9qnbz59qXzbLKerj18XrjTuD";



    /**
     * Pingpp 管理平台对应的应用 ID
     */
    private final static String appId = "app_5aX5q9j90O8KyPCu";
    /**
     * 你生成的私钥路径
     */
    private final static String privateKeyFilePath = File.separator+SystemPath.getClassPath()+"res"+ File.separator+"rsa_private_key.pem";

    public static String charge(String orderNo,int amount,String subject,String body,String channel,String clientIP){

        // 设置 API Key
        Pingpp.apiKey = apiKey;

        // 设置私钥路径，用于请求签名
        Pingpp.privateKeyPath = privateKeyFilePath;
        PingPlusCharge charge=new PingPlusCharge(appId);
        String chargeString=charge.createCharge(orderNo,amount,subject,body,channel,clientIP);
        return chargeString;
    }
}
