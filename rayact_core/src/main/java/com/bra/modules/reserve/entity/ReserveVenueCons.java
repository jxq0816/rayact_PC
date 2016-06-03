package com.bra.modules.reserve.entity;

import com.bra.common.persistence.SaasEntity;
import com.bra.modules.sys.entity.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.common.collect.Lists;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

/**
 * 场地预定主表Entity(订单表)
 *
 * @author 肖斌
 * @version 2016-01-11
 */
public class ReserveVenueCons extends SaasEntity<ReserveVenueCons> {

    public static final String RESERVATION = "1";//已预定
    public static final String MODEL_KEY = "field";
    private static final long serialVersionUID = 1L;

    private ReserveMember member;        // 会员ID
    private ReserveVenue reserveVenue;//所属场馆
    private ReserveProject project;   //所属项目
    private String consMobile;        // 预定人手机号
    private String userName;        // 预定人姓名
    private String consType;        // 预订的类型1：散客 2：会员
    private String reserveType;    //操作类型(1:已预定,4:已结算)
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date consDate;        // 预定日期(yyyy-MM-dd)
    private Double cosOrderPrice;//预定时单据金额??????????

    private Double discountPrice;//通过关系（经理以上领导）优惠价格
    private Double orderPrice;//订单金额
    private Double shouldPrice;//场地应缴费金额
    private Double consPrice;//实收金额

    private String halfCourt;//是否半场(1:是)
    private User checkOutUser;
    private String payType;////支付类型(1:储值卡，2:现金,3:银行卡,4:微信,5:支付宝,6:优惠券，7：打白条;8:多方式付款)
    private String byPC;

    /*多方式付款*/
    private Double memberCardInput;
    private Double cashInput;
    private Double bankCardInput;
    private Double weiXinInput;
    private Double weiXinPersonalInput;
    private Double aliPayInput;
    private Double aliPayPersonalInput;
    private Double couponInput;
    /*private Double owningInput;*/


    private List<ReserveVenueConsItem> venueConsList = Lists.newArrayList();//预订详情
    private List<ReserveTutorOrder> tutorOrderList;//教练预订列表

    public String getByPC() {
        return byPC;
    }

    public void setByPC(String byPC) {
        this.byPC = byPC;
    }

    public List<ReserveTutorOrder> getTutorOrderList() {
        return tutorOrderList;
    }

    public void setTutorOrderList(List<ReserveTutorOrder> tutorOrderList) {
        this.tutorOrderList = tutorOrderList;
    }

    public ReserveVenueCons() {
        super();
    }

    public String getHalfCourt() {
        return halfCourt;
    }

    public void setHalfCourt(String halfCourt) {
        this.halfCourt = halfCourt;
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

    @Length(min = 0, max = 1, message = "预订的类型1：散客 2：会员 2")
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

    public Double getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(Double discountPrice) {
        this.discountPrice = discountPrice;
    }

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

    public Double getShouldPrice() {
        return shouldPrice;
    }

    public void setShouldPrice(Double shouldPrice) {
        this.shouldPrice = shouldPrice;
    }

    public User getCheckOutUser() {
        return checkOutUser;
    }

    public void setCheckOutUser(User checkOutUser) {
        this.checkOutUser = checkOutUser;
    }

    public Double getMemberCardInput() {
        return memberCardInput;
    }

    public void setMemberCardInput(Double memberCardInput) {
        this.memberCardInput = memberCardInput;
    }

    public Double getCashInput() {
        return cashInput;
    }

    public void setCashInput(Double cashInput) {
        this.cashInput = cashInput;
    }

    public Double getBankCardInput() {
        return bankCardInput;
    }

    public void setBankCardInput(Double bankCardInput) {
        this.bankCardInput = bankCardInput;
    }

    public Double getWeiXinInput() {
        return weiXinInput;
    }

    public void setWeiXinInput(Double weiXinInput) {
        this.weiXinInput = weiXinInput;
    }

    public Double getAliPayInput() {
        return aliPayInput;
    }

    public void setAliPayInput(Double aliPayInput) {
        this.aliPayInput = aliPayInput;
    }

    public Double getCouponInput() {
        return couponInput;
    }

    public void setCouponInput(Double couponInput) {
        this.couponInput = couponInput;
    }

    public Double getWeiXinPersonalInput() {
        return weiXinPersonalInput;
    }

    public void setWeiXinPersonalInput(Double weiXinPersonalInput) {
        this.weiXinPersonalInput = weiXinPersonalInput;
    }

    public Double getAliPayPersonalInput() {
        return aliPayPersonalInput;
    }

    public void setAliPayPersonalInput(Double aliPayPersonalInput) {
        this.aliPayPersonalInput = aliPayPersonalInput;
    }

   /* public Double getOwningInput() {
        return owningInput;

    }

    public void setOwningInput(Double owningInput) {
        this.owningInput = owningInput;
    }*/

    //-------------以下和数据库无关字段-----------------

    private ReserveTutor tutor;
    private Date endDate;//预定结束日期(按照频率)
    private Date startDate; //预订开始日期
    private String frequency;//频率(1:单次;2:每天;3:每周)

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

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }
}