package com.bra.modules.reserve.web;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bra.common.web.BaseController;
import com.bra.modules.reserve.entity.ReserveVenueCons;
import com.bra.modules.reserve.service.ReserveAppVenueConsService;
import com.bra.modules.util.pingplusplus.PingPlusPlusService;
import com.bra.modules.util.pingplusplus.WebhooksVerifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.security.PublicKey;
import java.util.Enumeration;

/**
 * 场地预定管理
 * Created by jiangxingqi on 16/1/5.
 */
@Controller
@RequestMapping(value = "${adminPath}/app/pingPlusPlus")
public class AppPingPlusPlusController extends BaseController {
    //订单service
    @Autowired
    private ReserveAppVenueConsService reserveAppVenueConsService;
    @Autowired
    private PingPlusPlusService pingPlusPlusService;
    /**
     * ping++结算订单
     *
     * @param
     * @return
     */
    @RequestMapping(value = "charge")
    @ResponseBody
    public String charge(String order_no, int amount,String subject,String body,String channel, String client_ip) {
        String charge = pingPlusPlusService.charge(order_no, amount, subject, body, channel, client_ip);
        return charge;
    }
    @RequestMapping(value = "webhooks")
    @ResponseBody
    public void webhooks ( HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException  {
        /*System.out.println("ping++　webhooks");*/
        request.setCharacterEncoding("UTF8");
        //获取头部所有信息
        Enumeration headerNames = request.getHeaderNames();
        String signature=null;
        while (headerNames.hasMoreElements()) {
            String key = (String) headerNames.nextElement();
            String value = request.getHeader(key);
            if("x-pingplusplus-signature".equals(key)){
                signature=value;
            }
        }
        /*System.out.println("signature"+signature);*/
        // 获得 http body 内容
        StringBuffer eventJson=new StringBuffer();
       BufferedReader reader= null;
        try {
            reader = request.getReader();
            do{
                eventJson.append(reader.readLine());
            }while(reader.read()!=-1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        reader.close();
        JSONObject event=JSON.parseObject(eventJson.toString());
        boolean verifyRS=false;
        try {
            PublicKey publicKey= WebhooksVerifyService.getPubKey();
          /*  System.out.println(publicKey);*/
            verifyRS=WebhooksVerifyService.verifyData(eventJson.toString(),signature,publicKey);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(verifyRS) {
            /*System.out.println("签名验证成功");*/
            if ("charge.succeeded".equals(event.get("type"))) {
                JSONObject data = JSON.parseObject(event.get("data").toString());
                JSONObject object = JSON.parseObject(data.get("object").toString());
                String orderId = (String) object.get("order_no");
                /*System.out.println("orderId:"+orderId);*/
                String channel = (String) object.get("channel");
                String payType = null;
                int amountFen = (int) object.get("amount");
                Double amountYuan = amountFen * 1.0 / 100;//ping++扣款,精确到分，而数据库精确到元
                Double weiXinInput = null;
                Double aliPayInput = null;
                Double bankCardInput = null;

                if ("wx".equals(channel)) {
                    payType = "4";//支付类型(1:储值卡，2:现金,3:银行卡,4:微信,5:支付宝,6:优惠券，7：打白条;8:多方式付款;9:微信个人，10：支付宝（个人）)
                    weiXinInput = amountYuan;
                } else if ("alipay".equals(channel)) {
                    payType = "5";
                    aliPayInput = amountYuan;
                } else if ("upacp".equals(channel) || "upacp_wap".equals(channel) || "upacp_pc".equals(channel)) {
                    payType = "3";
                    bankCardInput = amountYuan;
                }
                Double couponInput;
                ReserveVenueCons order = reserveAppVenueConsService.get(orderId);

                if (order != null) {
                    Double orderPrice = order.getShouldPrice();
                    couponInput = orderPrice - amountYuan;//订单金额-ping++扣款 等于优惠金额
                    Boolean bool = reserveAppVenueConsService.saveSettlement(order, payType, amountYuan,
                            0.0, bankCardInput, weiXinInput, aliPayInput, couponInput);
                    if (bool) {
                      /*  System.out.println("订单结算成功");*/
                        response.setStatus(200);
                        //return "订单结算成功";
                    } else {
                       /* System.out.println("订单结算失败");*/
                        //return "订单结算失败";
                        response.setStatus(500);
                    }
                } else {
                   /* System.out.println("该订单不存在");*/
                    //return "该订单不存在";
                    response.setStatus(500);
                }
            }
        }else{
            /*System.out.println("签名验证失败");*/
            //return "签名验证失败";
            response.setStatus(500);
        }
    }
}
