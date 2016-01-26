/**
 * Copyright &copy; 2012-2014 <a href="https://github.com.bra.>JeeSite</a> All rights reserved.
 */
package com.bra.modules.reserve.dao;

import com.bra.common.persistence.CrudDao;
import com.bra.common.persistence.annotation.MyBatisDao;
import com.bra.modules.reserve.entity.ReserveProject;

/**
 * 项目管理DAO接口
 * @author xiaobin
 * @version 2015-12-29
 */
@MyBatisDao
public interface ReserveProjectDao extends CrudDao<ReserveProject> {
	
}