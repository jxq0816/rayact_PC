package com.bra.modules.reserve.entity;

import com.bra.common.persistence.DataEntity;

import java.util.Date;

/**
 * Created by xiaobin on 16/2/17.
 */
public class Sms extends DataEntity<Sms> {

    private String mobile;    // 手机号

    private String mobileCode;    // 手机验证码

    private String bak; //短信接口返回值

    private String delFlag; //短信接口返回值

    private String serviceId;//外围系统的ID号

    private Date createDate;//创建时间

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getMobileCode() {
        return mobileCode;
    }

    public void setMobileCode(String mobileCode) {
        this.mobileCode = mobileCode;
    }

    public String getBak() {
        return bak;
    }

    public void setBak(String bak) {
        this.bak = bak;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
