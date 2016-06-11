package com.bra.modules.reserve.entity.form;


import com.bra.modules.reserve.entity.ReserveVenueConsItem;
import com.bra.modules.reserve.entity.ReserveVenueEmptyCheck;
import com.bra.modules.reserve.entity.ReserveVenueOrder;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 场地的时间价格
 * Created by jiangxingqi on 16/1/8.
 */
public class TimePrice {

    private String time;
    private Double price;

    private String startTime;
    private String endTime;

    private ReserveVenueConsItem consItem;
    private ReserveVenueOrder ticket;
    private ReserveVenueEmptyCheck check;

    private String frequency;//频率(1:单次;2:每天;3:每周)
    private String consWeek;//周几?
    private String halfCourt;//是否半场

    private String consType;
    private String userName;

    private String status = "0";//0:可预定,1:已预定,2:锁场,3:已取消,4:已结算,00:审核正常，01：审核异常,11:场次票（仅用于空场审核）

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }



    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @JsonIgnore
    public ReserveVenueConsItem getConsItem() {
        return consItem;
    }

    public void setConsItem(ReserveVenueConsItem consItem) {
        this.consItem = consItem;
    }

    @JsonIgnore
    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    @JsonIgnore
    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }


    @JsonIgnore
    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }
    @JsonIgnore
    public String getConsWeek() {
        return consWeek;
    }

    public void setConsWeek(String consWeek) {
        this.consWeek = consWeek;
    }
    @JsonIgnore
    public String getHalfCourt() {
        return halfCourt;
    }

    public void setHalfCourt(String halfCourt) {
        this.halfCourt = halfCourt;
    }



    @JsonIgnore
    public String getConsType() {
        return consType;
    }

    public void setConsType(String consType) {
        this.consType = consType;
    }

    @JsonIgnore
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public ReserveVenueEmptyCheck getCheck() {
        return check;
    }

    public void setCheck(ReserveVenueEmptyCheck check) {
        this.check = check;
    }

    public ReserveVenueOrder getTicket() {
        return ticket;
    }

    public void setTicket(ReserveVenueOrder ticket) {
        this.ticket = ticket;
    }


}
