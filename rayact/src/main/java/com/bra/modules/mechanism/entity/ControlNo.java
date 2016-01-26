package com.bra.modules.mechanism.entity;

import com.bra.common.persistence.DataEntity;

/**
 * 生成如:field20160101000005
 * 订单编号
 * Created by xiaobin on 16/1/12.
 */
public class ControlNo extends DataEntity<ControlNo> {

    private String modelName;

    private String controlHead;//业务头()
    private String controlNo;//业务编码
    private String controlDate;//时间 20160101

    @Override
    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getControlHead() {
        return controlHead;
    }

    public void setControlHead(String controlHead) {
        this.controlHead = controlHead;
    }

    public String getControlNo() {
        return controlNo;
    }

    public void setControlNo(String controlNo) {
        this.controlNo = controlNo;
    }

    public String getControlDate() {
        return controlDate;
    }

    public void setControlDate(String controlDate) {
        this.controlDate = controlDate;
    }
}
