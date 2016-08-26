package com.bra.modules.reserve.entity;

import com.bra.common.persistence.SaasEntity;

/**
 * 赠品
 * Created by jiangxingqi on 16/1/27.
 */
public class ReserveVenueGift extends SaasEntity<ReserveVenueGift> {

    private ReserveCommodity gift;

    private ReserveCommoditySell commoditySell;

    private String reserveMemberName;

    private String modelId;

    private String modelKey;//field:场地,visitor:人次

    private Integer num;//数量

    public ReserveCommodity getGift() {
        return gift;
    }

    public void setGift(ReserveCommodity gift) {
        this.gift = gift;
    }

    public ReserveCommoditySell getCommoditySell() {
        return commoditySell;
    }

    public void setCommoditySell(ReserveCommoditySell commoditySell) {
        this.commoditySell = commoditySell;
    }

    public String getModelId() {
        return modelId;
    }

    public void setModelId(String modelId) {
        this.modelId = modelId;
    }

    public String getModelKey() {
        return modelKey;
    }

    public void setModelKey(String modelKey) {
        this.modelKey = modelKey;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public ReserveVenueGift(){
        super();
    }

    public ReserveVenueGift(String id){
        super(id);
    }

    public ReserveVenueGift(String modelId,String modelKey){
        super();
        this.modelId = modelId;
        this.modelKey = modelKey;
    }

    public String getReserveMemberName() {
        return reserveMemberName;
    }

    public void setReserveMemberName(String reserveMemberName) {
        this.reserveMemberName = reserveMemberName;
    }
}
