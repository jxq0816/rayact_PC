package com.bra.modules.cms.dao;

import com.bra.common.persistence.CrudDao;
import com.bra.common.persistence.annotation.MyBatisDao;
import com.bra.modules.cms.entity.Message;

import java.util.List;
import java.util.Map;

/**
 * 消息DAO接口
 * @author ddt
 * @version 2016-05-23
 */
@MyBatisDao
public interface MessageDao extends CrudDao<Message> {
    public List<Map<String,String>> findMapList(Message message);
}