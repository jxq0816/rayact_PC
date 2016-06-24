package com.bra.modules.reserve.web;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bra.common.web.BaseController;
import com.bra.modules.reserve.entity.ReserveVenueCons;
import com.bra.modules.reserve.service.ReserveAppVenueConsService;
import com.bra.modules.util.pingplusplus.PingPlusPlusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;

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
    @Autowired
    private ReserveAppController reserveAppController;
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
    public String webhooks ( HttpServletRequest request) {
        StringBuffer eventJson=new StringBuffer();
        String rs=null;
       BufferedReader reader= null;
        try {
            reader = request.getReader();
            do{
                eventJson.append(reader.readLine());
            }while(reader.read()!=-1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        JSONObject event=JSON.parseObject(eventJson.toString());
        if("charge.succeeded".equals(event.get("type"))){
            JSONObject data=JSON.parseObject(event.get("data").toString());
            JSONObject object=JSON.parseObject(data.get("object").toString());
            String orderId=(String)object.get("order_no");
            String channel=(String)object.get("channel");
            String payType=null;
            int amountFen=(int)object.get("amount");
            Double amountYuan= amountFen*1.0/100;//ping++扣款,精确到分，而数据库精确到元
            Double weiXinInput = null;
            Double aliPayInput = null;
            Double bankCardInput=null;

            if("wx".equals(channel)){
                payType="4";//支付类型(1:储值卡，2:现金,3:银行卡,4:微信,5:支付宝,6:优惠券，7：打白条;8:多方式付款;9:微信个人，10：支付宝（个人）)
                weiXinInput=amountYuan;
            }else if("alipay".equals(channel)){
                payType="5";
                aliPayInput=amountYuan;
            }else if("upacp".equals(channel)||"upacp_wap".equals(channel)||"upacp_pc".equals(channel)){
                payType="3";
                bankCardInput=amountYuan;
            }
            Double couponInput;
            System.out.println("orderId:"+orderId);
            ReserveVenueCons order = reserveAppVenueConsService.get(orderId);
            if(order!=null){
                Double orderPrice=order.getOrderPrice();
                couponInput=orderPrice-amountYuan;//订单金额-ping++扣款 等于优惠金额
                Boolean bool = reserveAppVenueConsService.saveSettlement(order, payType, amountYuan,
                        0.0, bankCardInput, weiXinInput, aliPayInput, couponInput);
                if(bool){
                    return "订单结算成功";
                }else{
                    return "订单结算失败";
                }
            }else{
                return "该订单不存在";
            }
        }
        return "p++ 支付成功";
    }
}
