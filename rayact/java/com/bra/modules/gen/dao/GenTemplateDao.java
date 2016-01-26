/**
 * Copyright &copy; 2012-2014 <a href="https://github.com.bra.>JeeSite</a> All rights reserved.
 */
package com.bra.modules.gen.dao;

import com.bra.common.persistence.CrudDao;
import com.bra.common.persistence.annotation.MyBatisDao;
import com.bra.modules.gen.entity.GenTemplate;

/**
 * 代码模板DAO接口
 *
 * @version 2013-10-15
 */
@MyBatisDao
public interface GenTemplateDao extends CrudDao<GenTemplate> {
	
}
