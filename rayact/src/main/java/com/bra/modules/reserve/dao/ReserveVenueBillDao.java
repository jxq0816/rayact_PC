package com.bra.modules.reserve.dao;

import com.bra.common.persistence.CrudDao;
import com.bra.common.persistence.annotation.MyBatisDao;
import com.bra.modules.reserve.entity.ReserveVenueBill;

/**
 * 场馆损益DAO接口
 * @author jiangxingqi
 * @version 2016-02-02
 */
@MyBatisDao
public interface ReserveVenueBillDao extends CrudDao<ReserveVenueBill> {
	
}