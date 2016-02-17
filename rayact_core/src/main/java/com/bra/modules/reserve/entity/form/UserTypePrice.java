package com.bra.modules.reserve.entity.form;

/**
 * Created by xiaobin on 16/1/8.
 */
public class UserTypePrice {

    private String userType;//1:散客
    private Double price;//价格

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
