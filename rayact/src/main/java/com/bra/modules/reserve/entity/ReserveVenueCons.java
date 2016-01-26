package com.bra.modules.reserve.entity;

import com.bra.common.persistence.SaasEntity;
import com.google.common.collect.Lists;
import org.hibernate.validator.constraints.Length;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 场地预定主表Entity(订单表)
 *
 * @author 肖斌
 * @version 2016-01-11
 */
public class ReserveVenueCons extends SaasEntity<ReserveVenueCons> {

    public static final String RESERVATION = "1";//预定
    public static final String SALEGOODS = "2";//售卖商品

    public static final String MODEL_KEY = "field";

    private static final long serialVersionUID = 1L;

    private ReserveMember member;        // 会员ID
    private ReserveVenue reserveVenue;//所属场馆
    private ReserveProject project;   //所属项目
    private String consMobile;        // 预定人手机号
    private String userName;        // 预定人姓名
    private String consType;        // 预订的类型1：散客 2：会员
    private String reserveType;    //操作类型(0:可预定,1:已预定,2:锁场,3:已取消,4:已结算)
    private Date consDate;        // 预定日期(yyyy-MM-dd)

    private Double cosOrderPrice;//预定时单据金额
    private Double consPrice;//预定金额

    private Double orderPrice;//场地实际缴费金额

    private String payType;//预定金额支付类型(1:)

    private List<ReserveVenueConsItem> venueConsList = Lists.newArrayList();

    public ReserveVenueCons() {
        super();
    }

    public ReserveVenueCons(String id) {
        super(id);
    }

    public ReserveMember getMember() {
        return member;
    }

    public void setMember(ReserveMember member) {
        this.member = member;
    }

    public String getConsMobile() {
        return consMobile;
    }

    public void setConsMobile(String consMobile) {
        this.consMobile = consMobile;
    }

    @Length(min = 0, max = 30, message = "预定人姓名长度必须介于 0 和 30 之间")
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Length(min = 0, max = 1, message = "预订的类型1：散客 2：会员 3：团体长度必须介于 0 和 1 之间")
    public String getConsType() {
        return consType;
    }

    public void setConsType(String consType) {
        this.consType = consType;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getConsDate() {
        return consDate;
    }

    public void setConsDate(Date consDate) {
        this.consDate = consDate;
    }

    //所属场馆
    public ReserveVenue getReserveVenue() {
        return reserveVenue;
    }

    public void setReserveVenue(ReserveVenue reserveVenue) {
        this.reserveVenue = reserveVenue;
    }

    public List<ReserveVenueConsItem> getVenueConsList() {
        return venueConsList;
    }

    public void setVenueConsList(List<ReserveVenueConsItem> venueConsList) {
        this.venueConsList = venueConsList;
    }

    //所属项目
    public ReserveProject getProject() {
        return project;
    }

    public void setProject(ReserveProject project) {
        this.project = project;
    }

    public Double getCosOrderPrice() {
        return cosOrderPrice;
    }

    public void setCosOrderPrice(Double cosOrderPrice) {
        this.cosOrderPrice = cosOrderPrice;
    }

    /**
     *0:可预定,1:已预定,2:锁场
     *
     * @return
     */
    public String getReserveType() {
        return reserveType;
    }

    public void setReserveType(String reserveType) {
        this.reserveType = reserveType;
    }

    private String searchValue;

    public String getSearchValue() {
        return searchValue;
    }

    public void setSearchValue(String searchValue) {
        this.searchValue = searchValue;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public Double getConsPrice() {
        return consPrice;
    }

    public void setConsPrice(Double consPrice) {
        this.consPrice = consPrice;
    }

    public Double getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(Double orderPrice) {
        this.orderPrice = orderPrice;
    }

    //-------------以下和数据库无关字段-----------------

    private ReserveTutor tutor;

    public ReserveTutor getTutor() {
        return tutor;
    }

    public void setTutor(ReserveTutor tutor) {
        this.tutor = tutor;
    }

    private ReserveTutorOrder tutorOrder;

    public ReserveTutorOrder getTutorOrder() {
        return tutorOrder;
    }

    public void setTutorOrder(ReserveTutorOrder tutorOrder) {
        this.tutorOrder = tutorOrder;
    }
}