package com.bra.modules.reserve.entity.form;

import com.google.common.collect.Lists;

import java.io.Serializable;
import java.util.List;

/**
 * 场地的所有属性
 * Created by jiang on 16/1/11.
 */
public class FieldPrice implements Serializable{

    private String venueId;//所属场馆
    private String fieldId;//所属场地
    private String fieldName;//场地名称
    private String haveHalfCourt;//是否有半场 0：没有 1：有
    private String haveFullCourt;//是否有全场 0：没有 1：有


    private List<TimePrice> timePriceList = Lists.newArrayList();//(时间:价格)对应关系
    private FieldPrice fieldPriceFull;//所属全场
    private FieldPrice fieldPriceLeft;//左侧半场
    private FieldPrice fieldPriceRight;//右侧半场

    public String getHaveFullCourt() {
        return haveFullCourt;
    }

    public void setHaveFullCourt(String haveFullCourt) {
        this.haveFullCourt = haveFullCourt;
    }


    public FieldPrice getFieldPriceFull() {
        return fieldPriceFull;
    }

    public void setFieldPriceFull(FieldPrice fieldPriceFull) {
        this.fieldPriceFull = fieldPriceFull;
    }


    public FieldPrice getFieldPriceRight() {
        return fieldPriceRight;
    }

    public void setFieldPriceRight(FieldPrice fieldPriceRight) {
        this.fieldPriceRight = fieldPriceRight;
    }

    public FieldPrice getFieldPriceLeft() {
        return fieldPriceLeft;
    }

    public void setFieldPriceLeft(FieldPrice fieldPriceLeft) {
        this.fieldPriceLeft = fieldPriceLeft;
    }

    public String getHaveHalfCourt() {
        return haveHalfCourt;
    }

    public void setHaveHalfCourt(String haveHalfCourt) {
        this.haveHalfCourt = haveHalfCourt;
    }

    public String getVenueId() {
        return venueId;
    }

    public void setVenueId(String venueId) {
        this.venueId = venueId;
    }

    public String getFieldId() {
        return fieldId;
    }

    public void setFieldId(String fieldId) {
        this.fieldId = fieldId;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public List<TimePrice> getTimePriceList() {
        return timePriceList;
    }

    public void setTimePriceList(List<TimePrice> timePriceList) {
        this.timePriceList = timePriceList;
    }
}
