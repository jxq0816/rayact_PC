package com.bra.modules.reserve.dao;

import com.bra.common.persistence.CrudDao;
import com.bra.common.persistence.annotation.MyBatisDao;
import com.bra.modules.reserve.entity.ReserveMultiplePayment;

/**
 * 多方式付款DAO接口
 * @author jiangxingqi
 * @version 2016-04-20
 */
@MyBatisDao
public interface ReserveMultiplePaymentDao extends CrudDao<ReserveMultiplePayment> {
	
}