/**
 * Copyright &copy; 2012-2014 <a href="https://github.com.bra.>JeeSite</a> All rights reserved.
 */
package com.bra.modules.sys.dao;

import com.bra.modules.sys.entity.Office;
import com.bra.common.persistence.TreeDao;
import com.bra.common.persistence.annotation.MyBatisDao;

/**
 * 机构DAO接口
 *
 * @version 2014-05-16
 */
@MyBatisDao
public interface OfficeDao extends TreeDao<Office> {
	
}
