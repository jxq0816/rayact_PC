package com.bra.modules.reserve.entity;

import com.bra.common.persistence.SaasEntity;
import com.bra.modules.sys.entity.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.List;

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
    private Double longitude;//经度
    private Double latitude;//维度
    private Double addressX;//地理位置X 经度
    private Double addressY;//地理位置Y 维度
    private String cityName;//城市
    private String districtName;//区
    private double evaluateScore;//评分
    private double avePrice;//平均消费
    private User chargeUser;        // 负责人
    private String tel;//电话
    private String moreService;//场馆标签
    private String startTime;        // 营业开始时间
    private String endTime;        // 营业结束时间
    private String projectId;//项目编号 仅仅用于查询
    private List<ReserveProject> projectList;//项目列表

    public List<ReserveProject> getProjectList() {
        return projectList;
    }

    public void setProjectList(List<ReserveProject> projectList) {
        this.projectList = projectList;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

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

    public Double getAddressX() {
        return addressX;
    }

    public void setAddressX(Double addressX) {
        this.addressX = addressX;
    }

    public Double getAddressY() {
        return addressY;
    }

    public void setAddressY(Double addressY) {
        this.addressY = addressY;
    }

    public void setMoreService(String moreService) {
        this.moreService = moreService;
    }

    public String getMoreService() {
        return moreService;
    }

    @Override
    public String getModelName() {
        return "ReserveVenue";
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    public double getEvaluateScore() {
        return evaluateScore;
    }

    public void setEvaluateScore(double evaluateScore) {
        this.evaluateScore = evaluateScore;
    }

    public double getAvePrice() {
        return avePrice;
    }

    public void setAvePrice(double avePrice) {
        this.avePrice = avePrice;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

}