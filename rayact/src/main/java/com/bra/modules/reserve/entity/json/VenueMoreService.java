package com.bra.modules.reserve.entity.json;

/**
 *  场馆更多服务
 * Created by xiaobin on 15/12/30.
 */
public class VenueMoreService {

    private String isInvoice;//提供发票
    private String invoice;//发票
    private String isParking;//提供停车
    private String parking;//停车
    private String isSale;//提供售卖
    private String sale;//场馆售卖
    private String isTransit;//提供公交信息
    private String transit;//公交
    private String isSubway;//提供地铁信息
    private String subway;//地铁

    public String getInvoice() {
        return invoice;
    }

    public void setInvoice(String invoice) {
        this.invoice = invoice;
    }

    public String getParking() {
        return parking;
    }

    public void setParking(String parking) {
        this.parking = parking;
    }

    public String getSale() {
        return sale;
    }

    public void setSale(String sale) {
        this.sale = sale;
    }

    public String getTransit() {
        return transit;
    }

    public void setTransit(String transit) {
        this.transit = transit;
    }

    public String getSubway() {
        return subway;
    }

    public void setSubway(String subway) {
        this.subway = subway;
    }

    public String getIsInvoice() {
        return isInvoice;
    }

    public void setIsInvoice(String isInvoice) {
        this.isInvoice = isInvoice;
    }

    public String getIsParking() {
        return isParking;
    }

    public void setIsParking(String isParking) {
        this.isParking = isParking;
    }

    public String getIsSale() {
        return isSale;
    }

    public void setIsSale(String isSale) {
        this.isSale = isSale;
    }

    public String getIsTransit() {
        return isTransit;
    }

    public void setIsTransit(String isTransit) {
        this.isTransit = isTransit;
    }

    public String getIsSubway() {
        return isSubway;
    }

    public void setIsSubway(String isSubway) {
        this.isSubway = isSubway;
    }
}
