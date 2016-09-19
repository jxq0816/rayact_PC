package com.bra.modules.reserve.utils;

import com.bra.modules.reserve.entity.ReserveVenueCons;
import com.bra.modules.reserve.entity.ReserveVenueOrder;
import com.bra.modules.reserve.web.form.ReserveVenueConsOrder;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.util.List;
import java.util.Map;

/**
 * Created by xiaobin on 16/1/25.
 */
public class VenueOrderUtils {

    public static String getPayType(String payType){

        String rs=null;
        switch (payType){
            case "1":
                rs="预储值";
                break;
            case "2":
                rs= "现金";
                break;
            case "3":
                rs= "银行卡";
                break;
            case "4":
                rs= "微信";
                break;
            case "5":
                rs= "支付宝";
                break;
            case "6":
                rs= "优惠券";
                break;
            case "7":
                rs= "打白条";
                break;
            case "8":
                rs= "多方式付款";
                break;
            case "9":
                rs= "微信（个人）";
                break;
            case "10":
                rs= "支付宝（个人）";
                break;
            case "11":
                rs= "转账";
                break;
        }
        return rs;
    }

    private static void setPayType(ReserveVenueConsOrder order, String payType) {
        if ("1".equals(payType)) {
            order.setMemberCount(order.getMemberCount() + 1);
        } else if ("2".equals(payType)) {//现金
            order.setMoneyCount(order.getMoneyCount() + 1);
        } else if ("3".equals(payType)) {
            order.setBankCartCount(order.getBankCartCount() + 1);
        } else if ("4".equals(payType)) {
            order.setWeixinCount(order.getWeixinCount() + 1);
        } else if ("5".equals(payType)) {
            order.setZfbCount(order.getZfbCount() + 1);
        } else if ("6".equals(payType)) {
            order.setOtherCount(order.getOtherCount() + 1);
        }
    }

    public static List<ReserveVenueConsOrder> getOrderList(List<ReserveVenueCons> venueConsList, List<ReserveVenueOrder> venueOrderList) {

        Map<String, ReserveVenueConsOrder> map = Maps.newConcurrentMap();
        for (ReserveVenueCons venueCons : venueConsList) {
            ReserveVenueConsOrder order = map.get(venueCons.getReserveVenue().getName());
            if (order != null) {
                order.setOrderPrice(order.getOrderPrice() + venueCons.getOrderPrice());
                setPayType(order, venueCons.getPayType());
            }else{
                order = new ReserveVenueConsOrder();
                order.setVenueName(venueCons.getReserveVenue().getName());
                order.setOrderPrice(venueCons.getOrderPrice());
                setPayType(order, venueCons.getPayType());
            }
            map.put(venueCons.getReserveVenue().getName(),order);
        }

        for(ReserveVenueOrder venueOrder : venueOrderList){
            ReserveVenueConsOrder order = map.get(venueOrder.getReserveVenue().getName());
            if (order != null) {
                order.setOrderPrice(order.getOrderPrice() + venueOrder.getOrderPrice());
                setPayType(order, venueOrder.getPayType());
            }else{
                order = new ReserveVenueConsOrder();
                order.setVenueName(venueOrder.getReserveVenue().getName());
                order.setOrderPrice(venueOrder.getOrderPrice());
                setPayType(order, venueOrder.getPayType());
            }
            map.put(venueOrder.getReserveVenue().getName(),order);
        }

        return Lists.newArrayList(map.values());
    }
}
