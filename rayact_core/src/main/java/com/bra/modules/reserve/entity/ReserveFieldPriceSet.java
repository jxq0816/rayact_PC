package com.bra.modules.reserve.entity;

import com.bra.common.persistence.SaasEntity;
import com.bra.common.utils.Collections3;
import com.bra.common.utils.JsonUtils;
import com.bra.common.utils.StringUtils;
import com.bra.modules.reserve.entity.form.TimePrice;
import org.hibernate.validator.constraints.Length;

import java.util.List;

/**
 * 场地时间价格Entity
 *
 * @author 肖斌
 * @version 2016-01-06
 */
public class ReserveFieldPriceSet extends SaasEntity<ReserveFieldPriceSet> {

    private static final long serialVersionUID = 1L;
    private ReserveVenue reserveVenue;//所属场馆
    private ReserveField reserveField;//所属场地
    private String week;            //星期  1：周一至周五 2：周六 3：周日
    private String consType;        // 消费类型(1:市场价,2:会员,3:团体)
    private String consJson;      //按照时间和价格组装成json
    private ReserveTimeInterval reserveTimeInterval;//时令

    public ReserveTimeInterval getReserveTimeInterval() {
        return reserveTimeInterval;
    }

    public void setReserveTimeInterval(ReserveTimeInterval reserveTimeInterval) {
        this.reserveTimeInterval = reserveTimeInterval;
    }

    public ReserveFieldPriceSet() {
        super();
    }

    public ReserveFieldPriceSet(String id) {
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

    @Length(min = 0, max = 5, message = "星期几?(如:周日)长度必须介于 0 和 5 之间")
    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    @Length(min = 0, max = 1, message = "消费类型(1:散客,2:会员,3:团体)长度必须介于 0 和 1 之间")
    public String getConsType() {
        return consType;
    }

    public void setConsType(String consType) {
        this.consType = consType;
    }

    public String getConsJson() {
        return consJson;
    }

    public void setConsJson(String consJson) {
        this.consJson = consJson;
    }

    private List<TimePrice> timePriceList;

    public List<TimePrice> getTimePriceList() {
        if (Collections3.isEmpty(timePriceList)) {
            if (StringUtils.isNotBlank(consJson)) {
                return JsonUtils.readBeanByJson(consJson, List.class, TimePrice.class);
            }
        }
        return timePriceList;
    }

    public void setTimePriceList(List<TimePrice> timePriceList) {
        this.timePriceList = timePriceList;
    }

    public String getWeekName() {
        if ("1".equals(week)) {
            return "周一至周五";
        } else if ("2".equals(week)) {
            return "周六";
        } else if ("3".equals(week)) {
            return "周日";
        }
        return "";
    }

    public String getConsTypeName() {
        if ("1".equals(consType)) {
            return "散客";
        } else if ("2".equals(consType)) {
            return "会员";
        } else if ("3".equals(consType)) {
            return "团体";
        }
        return "";
    }
}