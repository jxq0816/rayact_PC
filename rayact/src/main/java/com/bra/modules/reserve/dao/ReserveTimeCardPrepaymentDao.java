package com.bra.modules.reserve.dao;

import com.bra.common.persistence.CrudDao;
import com.bra.common.persistence.annotation.MyBatisDao;
import com.bra.modules.reserve.entity.ReserveTimeCardPrepayment;

/**
 * 次卡预付款DAO接口
 * @author jiangxingqi
 * @version 2016-04-19
 */
@MyBatisDao
public interface ReserveTimeCardPrepaymentDao extends CrudDao<ReserveTimeCardPrepayment> {
	
}