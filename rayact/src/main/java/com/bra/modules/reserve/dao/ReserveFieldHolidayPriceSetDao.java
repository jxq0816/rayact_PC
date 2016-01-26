package com.bra.modules.reserve.dao;

import com.bra.common.persistence.CrudDao;
import com.bra.common.persistence.annotation.MyBatisDao;
import com.bra.modules.reserve.entity.ReserveFieldHolidayPriceSet;

/**
 * 场地价格设定（节假日价格）DAO接口
 * @author 肖斌
 * @version 2016-01-08
 */
@MyBatisDao
public interface ReserveFieldHolidayPriceSetDao extends CrudDao<ReserveFieldHolidayPriceSet> {
	
}