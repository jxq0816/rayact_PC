package com.bra.modules.reserve.dao;

import com.bra.common.persistence.CrudDao;
import com.bra.common.persistence.annotation.MyBatisDao;
import com.bra.modules.reserve.entity.ReserveCardStatements;
import com.bra.modules.reserve.entity.ReserveCommoditySell;
import com.bra.modules.reserve.entity.form.ReserveCommodityDayReport;
import com.bra.modules.reserve.entity.form.ReserveCommodityIntervalReport;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 商品销售主表DAO接口
 * @author jiangxingqi
 * @version 2016-01-12
 */
@MyBatisDao
public interface ReserveCommoditySellDao extends CrudDao<ReserveCommoditySell> {

    BigDecimal findSellOfMonth(ReserveCardStatements reserveCardStatements);

    List<ReserveCommodityIntervalReport> commodityIncomeCollectReport(ReserveCommodityIntervalReport reserveCommodityIntervalReport);

    List<ReserveCommodityIntervalReport> reserveCommodityIncomeIntervalReport(ReserveCommodityIntervalReport reserveCommodityIntervalReport);

    List<ReserveCommodityDayReport> reserveCommodityIncomeDayReport(ReserveCommodityDayReport reserveCommodityIncomeDayReport);

    BigDecimal sellOfToday(ReserveCardStatements reserveCardStatements);

    List<Map<String, Object>> sellOfChart(ReserveCardStatements reserveCardStatements);

}