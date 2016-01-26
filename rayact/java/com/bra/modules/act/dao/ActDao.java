/**
 * Copyright &copy; 2012-2014 <a href="https://github.com.bra.>JeeSite</a> All rights reserved.
 */
package com.bra.modules.act.dao;

import com.bra.common.persistence.CrudDao;
import com.bra.modules.act.entity.Act;
import com.bra.common.persistence.annotation.MyBatisDao;

/**
 * 审批DAO接口
 *
 * @version 2014-05-16
 */
@MyBatisDao
public interface ActDao extends CrudDao<Act> {

	public int updateProcInsIdByBusinessId(Act act);
	
}
