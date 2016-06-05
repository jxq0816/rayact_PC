package com.bra.modules.reserve.dao;

import com.bra.common.persistence.CrudDao;
import com.bra.common.persistence.annotation.MyBatisDao;
import com.bra.modules.reserve.entity.ReserveCommoditySupplier;

/**
 * 供应商DAO接口
 * @author jiangxingqi
 * @version 2016-06-05
 */
@MyBatisDao
public interface ReserveCommoditySupplierDao extends CrudDao<ReserveCommoditySupplier> {
	
}