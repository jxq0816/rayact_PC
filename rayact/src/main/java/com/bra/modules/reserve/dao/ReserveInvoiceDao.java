package com.bra.modules.reserve.dao;

import com.bra.common.persistence.CrudDao;
import com.bra.common.persistence.annotation.MyBatisDao;
import com.bra.modules.reserve.entity.ReserveInvoice;

/**
 * 发票管理DAO接口
 * @author xudl
 * @version 2016-04-18
 */
@MyBatisDao
public interface ReserveInvoiceDao extends CrudDao<ReserveInvoice> {
	
}