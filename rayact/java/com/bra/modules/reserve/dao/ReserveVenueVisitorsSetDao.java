package com.bra.modules.reserve.dao;

import com.bra.common.persistence.CrudDao;
import com.bra.common.persistence.annotation.MyBatisDao;
import com.bra.modules.reserve.entity.ReserveVenueVisitorsSet;

/**
 * 人次票设置DAO接口
 * @author 肖斌
 * @version 2016-01-18
 */
@MyBatisDao
public interface ReserveVenueVisitorsSetDao extends CrudDao<ReserveVenueVisitorsSet> {
	
}