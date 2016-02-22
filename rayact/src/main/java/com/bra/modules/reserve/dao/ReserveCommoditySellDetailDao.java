package com.bra.modules.reserve.dao;

import com.bra.common.persistence.CrudDao;
import com.bra.common.persistence.annotation.MyBatisDao;
import com.bra.modules.reserve.entity.ReserveCardStatements;
import com.bra.modules.reserve.entity.ReserveCommoditySellDetail;

import java.util.List;
import java.util.Map;

/**
 * 商品销售明细DAO接口
 * @author jiangxingqi
 * @version 2016-01-12
 */
@MyBatisDao
public interface ReserveCommoditySellDetailDao extends CrudDao<ReserveCommoditySellDetail> {

     List<ReserveCommoditySellDetail> findSellDetailList(ReserveCommoditySellDetail reserveCommoditySellDetail);

}