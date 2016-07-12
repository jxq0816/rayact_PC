package com.bra.modules.reserve.dao;

import com.bra.common.persistence.CrudDao;
import com.bra.common.persistence.annotation.MyBatisDao;
import com.bra.modules.reserve.entity.ReserveOffice;

/**
 * 集团DAO接口
 * @author jiangxingqi
 * @version 2016-07-11
 */
@MyBatisDao
public interface ReserveOfficeDao extends CrudDao<ReserveOffice> {
	
}