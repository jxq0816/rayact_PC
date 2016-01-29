package com.bra.modules.reserve.dao;

import com.bra.common.persistence.CrudDao;
import com.bra.common.persistence.annotation.MyBatisDao;
import com.bra.modules.reserve.entity.ReserveCardStatements;
import com.bra.modules.reserve.entity.ReserveCommoditySell;

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

    BigDecimal sellOfToday(ReserveCardStatements reserveCardStatements);

    List<Map<String, Object>> sellOfChart(ReserveCardStatements reserveCardStatements);


}