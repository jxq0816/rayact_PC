package com.bra.modules.reserve.dao;

import com.bra.common.persistence.CrudDao;
import com.bra.common.persistence.annotation.MyBatisDao;
import com.bra.modules.reserve.entity.ReserveCommodityType;

/**
 * 商品类别DAO接口
 * @author jiangxingqi
 * @version 2016-01-07
 */
@MyBatisDao
public interface ReserveCommodityTypeDao extends CrudDao<ReserveCommodityType> {
	
}