package com.bra.modules.reserve.dao;

import com.bra.common.persistence.CrudDao;
import com.bra.common.persistence.annotation.MyBatisDao;
import com.bra.modules.reserve.entity.ReserveRole;

/**
 * 场馆用户角色DAO接口
 * @author 肖斌
 * @version 2016-01-20
 */
@MyBatisDao
public interface ReserveRoleDao extends CrudDao<ReserveRole> {
	
}