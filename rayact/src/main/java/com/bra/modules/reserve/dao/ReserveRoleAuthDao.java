package com.bra.modules.reserve.dao;

import com.bra.common.persistence.CrudDao;
import com.bra.common.persistence.annotation.MyBatisDao;
import com.bra.modules.reserve.entity.ReserveRoleAuth;

/**
 * 角色权限管理DAO接口
 * @author jiangxingqi
 * @version 2016-06-28
 */
@MyBatisDao
public interface ReserveRoleAuthDao extends CrudDao<ReserveRoleAuth> {
	
}