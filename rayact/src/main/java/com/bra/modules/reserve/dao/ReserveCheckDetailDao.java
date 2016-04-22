package com.bra.modules.reserve.dao;

import com.bra.common.persistence.CrudDao;
import com.bra.common.persistence.annotation.MyBatisDao;
import com.bra.modules.reserve.entity.ReserveCheckDetail;

/**
 * 账目审核DAO接口
 * @author xudl
 * @version 2016-04-22
 */
@MyBatisDao
public interface ReserveCheckDetailDao extends CrudDao<ReserveCheckDetail> {
	
}