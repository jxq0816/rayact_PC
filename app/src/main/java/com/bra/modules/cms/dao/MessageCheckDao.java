package com.bra.modules.cms.dao;

import com.bra.common.persistence.CrudDao;
import com.bra.common.persistence.annotation.MyBatisDao;
import com.bra.modules.cms.entity.MessageCheck;

/**
 * 消息查收DAO接口
 * @author ddt
 * @version 2016-06-07
 */
@MyBatisDao
public interface MessageCheckDao extends CrudDao<MessageCheck> {
	
}