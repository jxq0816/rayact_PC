package com.bra.modules.reserve.entity;

import com.bra.common.persistence.SaasEntity;
import com.bra.common.utils.JsonUtils;
import com.bra.common.utils.StringUtils;
import com.bra.modules.reserve.entity.json.VenueMoreService;
import org.hibernate.validator.constraints.Length;
import com.bra.modules.sys.entity.User;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import com.bra.common.persistence.DataEntity;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 场馆管理Entity
 *
 * @author 肖斌
 * @version 2015-12-29
 */
public class ReserveVenue extends SaasEntity<ReserveVenue> {

    private static final long serialVersionUID = 1L;
    private String name;        // 场馆名称
    private String available;        // 是否启用
    private String address;        // 地址
    private String addressX;//地理位置X
    private String addressY;//地理位置Y
    private User chargeUser;        // 负责人

    private String tel;//电话
    private String moreService;//场馆服务

    private String startTime;        // 营业开始时间
    private String endTime;        // 营业结束时间

    public ReserveVenue() {
        super();
    }

    public ReserveVenue(String id) {
        super(id);
    }

    @Length(min = 0, max = 30, message = "场馆名称长度必须介于 0 和 30 之间")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Length(min = 0, max = 1, message = "是否启用长度必须介于 0 和 1 之间")
    public String getAvailable() {
        return available;
    }

    public void setAvailable(String available) {
        this.available = available;
    }

    @Length(min = 0, max = 200, message = "地址长度必须介于 0 和 200 之间")
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public User getChargeUser() {
        return chargeUser;
    }

    public void setChargeUser(User chargeUser) {
        this.chargeUser = chargeUser;
    }

    @JsonFormat(pattern = "HH:mm:ss")
    @DateTimeFormat(pattern = "HH:mm:ss")
    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    @JsonFormat(pattern = "HH:mm:ss")
    @DateTimeFormat(pattern = "HH:mm:ss")
    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getAddressX() {
        return addressX;
    }

    public void setAddressX(String addressX) {
        this.addressX = addressX;
    }

    public String getAddressY() {
        return addressY;
    }

    public void setAddressY(String addressY) {
        this.addressY = addressY;
    }

    public String getMoreService() {
        if (moreServiceBean != null) {
            moreService = JsonUtils.writeObjectToJson(moreServiceBean);
        }
        return moreService;
    }

    public void setMoreService(String moreService) {
        this.moreService = moreService;
    }

    private VenueMoreService moreServiceBean;

    public VenueMoreService getMoreServiceBean() {
        return moreServiceBean;
    }

    public void setMoreServiceBean(VenueMoreService moreServiceBean) {
        this.moreServiceBean = moreServiceBean;
    }

    public VenueMoreService getVenueMoreService() {
        if (StringUtils.isNotBlank(moreService)) {
            return JsonUtils.readObjectByJson(moreService, VenueMoreService.class);
        }
        return new VenueMoreService();
    }

    @Override
    public String getModelName() {
        return "ReserveVenue";
    }
}