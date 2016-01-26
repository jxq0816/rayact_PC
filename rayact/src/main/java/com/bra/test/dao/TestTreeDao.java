/**
 * Copyright &copy; 2012-2014 <a href="https://github.com.bra.>JeeSite</a> All rights reserved.
 */
package com.bra.test.dao;

import com.bra.test.entity.TestTree;
import com.bra.common.persistence.TreeDao;
import com.bra.common.persistence.annotation.MyBatisDao;

/**
 * 树结构生成DAO接口
 *
 * @version 2015-04-06
 */
@MyBatisDao
public interface TestTreeDao extends TreeDao<TestTree> {
	
}