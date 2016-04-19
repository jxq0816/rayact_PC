package com.bra.modules.reserve.entity;

import com.bra.common.persistence.SaasEntity;
import com.bra.modules.sys.entity.User;

import java.util.Date;

/**
 * 免单申请
 *
 * @author ddt
 * @version 2016-04-12
 */
public class ReserveVenueApplyCut extends SaasEntity<ReserveVenueApplyCut> {

    private static final long serialVersionUID = 1L;
    private String done;//是否已处理
    private ReserveVenueCons cons;//订单
    private ReserveMember member;//会员
    private String name;//非会员姓名
    private User applyer;        // 申请人
    private ReserveVenue venue;//所属场馆
    private double price;//订单价格
    private double cutPrice;//优惠价格
    private Date consDate;//订单时间
    private String tel;//客户电话

    public String getIsNew() {
        return (isNew!=null&&!"".equals(isNew))?isNew:"1";
    }

    public void setIsNew(String isNew) {
        this.isNew = (isNew!=null&&!"".equals(isNew))?isNew:"1";
    }

    private String isNew;//是否是新的

    private String startTime;
    private String endTime;

    public ReserveVenueApplyCut() {
        super();
    }

    public ReserveVenueApplyCut(String id) {
        super(id);
    }

    public ReserveVenueCons getCons() {
        return cons;
    }

    public void setCons(ReserveVenueCons cons) {
        this.cons = cons;
    }

    public ReserveMember getMember() {
        return member;
    }

    public void setMember(ReserveMember member) {
        this.member = member;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getApplyer() {
        return applyer;
    }

    public void setApplyer(User applyer) {
        this.applyer = applyer;
    }

    public ReserveVenue getVenue() {
        return venue;
    }

    public void setVenue(ReserveVenue venue) {
        this.venue = venue;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getCutPrice() {
        return cutPrice;
    }

    public void setCutPrice(double cutPrice) {
        this.cutPrice = cutPrice;
    }

    public Date getConsDate() {
        return consDate;
    }

    public void setConsDate(Date consDate) {
        this.consDate = consDate;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
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

    public String getDone() {
        return done;
    }

    public void setDone(String done) {
        this.done = done;
    }

    @Override
    public String getModelName() {
        return "ReserveVenueApplyCut";
    }
}