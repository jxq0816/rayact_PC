package com.bra.modules.reserve.dao;

import com.bra.common.persistence.CrudDao;
import com.bra.common.persistence.annotation.MyBatisDao;
import com.bra.modules.reserve.entity.ReserveCommoditySell;

/**
 * 商品销售主表DAO接口
 * @author jiangxingqi
 * @version 2016-01-12
 */
@MyBatisDao
public interface ReserveCommoditySellDao extends CrudDao<ReserveCommoditySell> {
	
}