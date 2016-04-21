package com.bra.modules.reserve.dao;

import com.bra.common.persistence.CrudDao;
import com.bra.common.persistence.annotation.MyBatisDao;
import com.bra.modules.reserve.entity.ReserveVenueEmptyCheck;

/**
 * 空场审核DAO接口
 * @author xudl
 * @version 2016-04-21
 */
@MyBatisDao
public interface ReserveVenueEmptyCheckDao extends CrudDao<ReserveVenueEmptyCheck> {
	
}