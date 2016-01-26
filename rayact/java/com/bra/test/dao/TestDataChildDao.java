/**
 * Copyright &copy; 2012-2014 <a href="https://github.com.bra.>JeeSite</a> All rights reserved.
 */
package com.bra.test.dao;

import com.bra.common.persistence.CrudDao;
import com.bra.common.persistence.annotation.MyBatisDao;
import com.bra.test.entity.TestDataChild;

/**
 * 主子表生成DAO接口
 *
 * @version 2015-04-06
 */
@MyBatisDao
public interface TestDataChildDao extends CrudDao<TestDataChild> {
	
}