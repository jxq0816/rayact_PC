package com.bra.modules.reserve.web.form;

import com.bra.common.persistence.SaasEntity;
import com.bra.modules.reserve.entity.ReserveVenue;
import com.bra.modules.sys.entity.User;

import java.util.Date;

/**
 * Created by xiaobin on 16/1/28.
 */
public class SaleVenueLog extends SaasEntity<SaleVenueLog> {

    private String id;

    private double orderPrice;

    private double shouldPrice;

    private double discountPrice;

    private double consPrice;

    private String payType;

    private String checkoutName;

    private String projectName;

    private String startTime;

    private String endTime;

    private Date consDate;

    private ReserveVenue venue;

    private User user;

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    public double getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(double orderPrice) {
        this.orderPrice = orderPrice;
    }

    public double getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(double discountPrice) {
        this.discountPrice = discountPrice;
    }

    public double getShouldPrice() {
        return shouldPrice;
    }

    public void setShouldPrice(double shouldPrice) {
        this.shouldPrice = shouldPrice;
    }

    public double getConsPrice() {
        return consPrice;
    }

    public void setConsPrice(double consPrice) {
        this.consPrice = consPrice;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getCheckoutName() {
        return checkoutName;
    }

    public void setCheckoutName(String checkoutName) {
        this.checkoutName = checkoutName;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Date getConsDate() {
        return consDate;
    }

    public void setConsDate(Date consDate) {
        this.consDate = consDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


    public ReserveVenue getVenue() {
        return venue;
    }

    public void setVenue(ReserveVenue venue) {
        this.venue = venue;
    }

    //---------------------------------------------------------
    private Date startDate;
    private Date endDate;
    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    //--------------------------------------

}
