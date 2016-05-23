package com.bra.modules.cms.dao;

import com.bra.common.persistence.CrudDao;
import com.bra.common.persistence.annotation.MyBatisDao;
import com.bra.modules.cms.entity.Apply;

/**
 * 活动报名DAO接口
 * @author ddt
 * @version 2016-05-18
 */
@MyBatisDao
public interface ApplyDao extends CrudDao<Apply> {
	
}