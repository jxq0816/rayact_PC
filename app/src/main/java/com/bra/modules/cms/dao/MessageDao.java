package com.bra.modules.cms.dao;

import com.bra.common.persistence.CrudDao;
import com.bra.common.persistence.annotation.MyBatisDao;
import com.bra.modules.cms.entity.Message;

/**
 * 消息DAO接口
 * @author ddt
 * @version 2016-05-23
 */
@MyBatisDao
public interface MessageDao extends CrudDao<Message> {
	
}