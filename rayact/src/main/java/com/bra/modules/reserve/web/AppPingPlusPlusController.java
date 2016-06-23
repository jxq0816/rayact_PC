package com.bra.modules.reserve.web;

import com.bra.common.web.BaseController;
import com.bra.modules.reserve.entity.ReserveVenueCons;
import com.bra.modules.reserve.service.ReserveAppVenueConsService;
import com.bra.modules.util.pingplusplus.PingPlusPlusService;
import com.pingplusplus.model.Charge;
import com.pingplusplus.model.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
    @RequestMapping(value = "Webhooks")
    @ResponseBody
    public void Webhooks (Event event) {
        if("charge.succeeded".equals(event.getType())){
            Charge charge=(Charge)event.getData().getObject();
            String orderId=charge.getId();
            String channel=charge.getChannel();
            String payType=null;
            int amountFen=charge.getAmount();
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
            ReserveVenueCons reserveVenueCons = reserveAppVenueConsService.get(orderId);
            Double orderPrice=reserveVenueCons.getOrderPrice();
            couponInput=orderPrice-amountYuan;//订单金额-ping++扣款 等于优惠金额
            reserveAppController.saveSettlement(orderId,payType,amountYuan,0.0,bankCardInput,weiXinInput,aliPayInput,couponInput);
        }
    }
}
