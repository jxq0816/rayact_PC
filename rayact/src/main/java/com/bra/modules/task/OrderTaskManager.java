package com.bra.modules.task;

import com.bra.modules.reserve.entity.ReserveVenueCons;
import com.bra.modules.reserve.service.ReserveVenueConsService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by lenovo on 2016/6/9.
 */
public class OrderTaskManager {
    @Autowired
    private ReserveVenueConsService orderService;
    public void cancelOrder() {
        ReserveVenueCons order=new ReserveVenueCons();
        order.setByPC("0");//手机端
        order.setReserveType("1");//已预订
        List<ReserveVenueCons> list = orderService.findList(order);
        for(ReserveVenueCons i:list){
            //订单创建时间
            Calendar createCalendar= Calendar.getInstance();
            Date createTime=i.getCreateDate();
            createCalendar.setTime(createTime);
            //当前时间
            Calendar nowCalendar=Calendar.getInstance();
            Date systemTime=new Date();
            nowCalendar.setTime(systemTime);

            long create=createCalendar.getTimeInMillis();
            long now=nowCalendar.getTimeInMillis();
            long minute=(now-create)/(1000*60);//转化minute
            if(minute>15){
                orderService.delete(i);//订单15分钟未支付，自动删除
            }
        }
    }
}
