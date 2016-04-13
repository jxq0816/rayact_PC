package com.bra.modules.reserve.dao;

import com.bra.common.persistence.CrudDao;
import com.bra.common.persistence.annotation.MyBatisDao;
import com.bra.modules.reserve.entity.ReserveCommodityStorageLog;

/**
 * 商品入库记录DAO接口
 * @author jiangxingqi
 * @version 2016-04-13
 */
@MyBatisDao
public interface ReserveCommodityStorageLogDao extends CrudDao<ReserveCommodityStorageLog> {
	
}