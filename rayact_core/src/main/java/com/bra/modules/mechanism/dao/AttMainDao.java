package com.bra.modules.mechanism.dao;

import com.bra.common.persistence.CrudDao;
import com.bra.common.persistence.annotation.MyBatisDao;
import com.bra.modules.mechanism.entity.AttMain;

/**
 * 文档管理DAO接口
 * @author 肖斌
 * @version 2015-12-30
 */
@MyBatisDao
public interface AttMainDao extends CrudDao<AttMain> {

    void updateAttMain(AttMain bean);
	
}