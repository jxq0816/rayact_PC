package com.bra.modules.reserve.dao;

import com.bra.common.persistence.CrudDao;
import com.bra.common.persistence.annotation.MyBatisDao;
import com.bra.modules.reserve.entity.ReserveVenueCons;
import com.bra.modules.reserve.web.form.SaleVenueLog;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 场地预定主表DAO接口
 *
 * @author 肖斌
 * @version 2016-01-11
 */
@MyBatisDao
public interface ReserveVenueConsDao extends CrudDao<ReserveVenueCons> {

    List<ReserveVenueCons> findListOrder(ReserveVenueCons venueCons);

    /**
     * 聚合项目查询每个项目的销售金额
     * @param venueCons
     * @return
     */
    List<Map<String,Object>> findPriceGroupProject(ReserveVenueCons venueCons);

    /**
     * 场地售卖报表查询
     * @param venueCons
     * @return
     */
    List<Map<String,Object>> findPriceGroupProjectReport(ReserveVenueCons venueCons);

    List<SaleVenueLog> findOrderLog(SaleVenueLog venueLog);
    List<Map<String,Object>> findTimeCardSaleLog(SaleVenueLog venueLog);

    /**
     * 查询空场率
     * @param venueCons
     * @return
     */
    List<Map<String,Object>> findOpenRateReport(ReserveVenueCons venueCons);
    /*
        查询当年的销量柱状图
     */
    List<Map<String,Object>> sellOfHistogram(ReserveVenueCons venueCons);
    /*
        查询当天的销售量
    */
    List<Map<String,Object>> sellOfChart(ReserveVenueCons venueCons);

    BigDecimal sellMonthOfChart(ReserveVenueCons venueCons);
    /*订单详情*/
    Map detail(Map map);
    /*检测用户是否有未付款的订单*/
    List<Map> checkUserUnpaidOrder(Map map);
    /*订单列表*/
    List<Map> orderList(Map map);
}