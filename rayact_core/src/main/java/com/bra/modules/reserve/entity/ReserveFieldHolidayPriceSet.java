package com.bra.modules.reserve.entity;

import com.bra.common.persistence.SaasEntity;
import com.bra.common.utils.Collections3;
import com.bra.common.utils.JsonUtils;
import com.bra.common.utils.StringUtils;
import com.bra.modules.reserve.entity.form.UserTypePrice;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.common.collect.Lists;
import org.hibernate.validator.constraints.Length;

import java.util.Date;
import java.util.List;

/**
 * 场地价格设定（节假日价格）Entity
 *
 * @author 肖斌
 * @version 2016-01-08
 */
public class ReserveFieldHolidayPriceSet extends SaasEntity<ReserveFieldHolidayPriceSet> {

    private static final long serialVersionUID = 1L;
    private ReserveVenue reserveVenue;//所属场馆
    private ReserveField reserveField;//所属场地
    private String startDate;        // 假日开始时间
    private String endDate;        // 假日结束时间
    private String fieldStartTime;        // 开始时间
    private String fieldEndTime;//结束时间
    private String consType;        // 消费类型(1:散客,2:会员,3:团体)
    private Double consPrice;       //消费价格
    private String consJson;        // 按照时间和价格组装成json

    public ReserveFieldHolidayPriceSet() {
        super();
    }

    public ReserveFieldHolidayPriceSet(String id) {
        super(id);
    }

    public ReserveVenue getReserveVenue() {
        return reserveVenue;
    }

    public void setReserveVenue(ReserveVenue reserveVenue) {
        this.reserveVenue = reserveVenue;
    }

    public ReserveField getReserveField() {
        return reserveField;
    }

    public void setReserveField(ReserveField reserveField) {
        this.reserveField = reserveField;
    }

    @Length(min = 0, max = 5, message = "假日开始时间长度必须介于 0 和 5 之间")
    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    @Length(min = 0, max = 10, message = "假日结束时间长度必须介于 0 和 10 之间")
    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    @JsonFormat(pattern = "yyyy-MM-dd")
    public String getFieldStartTime() {
        return fieldStartTime;
    }

    public void setFieldStartTime(String fieldStartTime) {
        this.fieldStartTime = fieldStartTime;
    }

    @JsonFormat(pattern = "yyyy-MM-dd")
    public String getFieldEndTime() {
        return fieldEndTime;
    }

    public void setFieldEndTime(String fieldEndTime) {
        this.fieldEndTime = fieldEndTime;
    }

    @Length(min = 0, max = 1, message = "消费类型(1:散客,2:会员,3:团体)长度必须介于 0 和 1 之间")
    public String getConsType() {
        return consType;
    }

    public void setConsType(String consType) {
        this.consType = consType;
    }

    @Length(min = 0, max = 1000, message = "按照时间和价格组装成json长度必须介于 0 和 1000 之间")
    public String getConsJson() {
        return consJson;
    }

    public void setConsJson(String consJson) {
        this.consJson = consJson;
    }

    public Double getConsPrice() {
        return consPrice;
    }

    public void setConsPrice(Double consPrice) {
        this.consPrice = consPrice;
    }

    private List<UserTypePrice> userTypePriceList = Lists.newArrayList();

    public List<UserTypePrice> getUserTypePriceList() {
        if (Collections3.isEmpty(userTypePriceList)) {
            if (StringUtils.isNotBlank(consJson)) {
                userTypePriceList = JsonUtils.readBeanByJson(consJson, List.class, UserTypePrice.class);
            }
        }
        return userTypePriceList;
    }

    public void setUserTypePriceList(List<UserTypePrice> userTypePriceList) {
        this.userTypePriceList = userTypePriceList;
    }

    private Date date;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}