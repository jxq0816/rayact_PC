package com.bra.modules.cms.dao;

import com.bra.common.persistence.CrudDao;
import com.bra.common.persistence.annotation.MyBatisDao;
import com.bra.modules.cms.entity.Attitude;

import java.util.List;
import java.util.Map;

/**
 * 点赞DAO接口
 * @author ddt
 * @version 2016-05-17
 */
@MyBatisDao
public interface AttitudeDao extends CrudDao<Attitude> {
	public List<Map<String,String>> getCount(Attitude attitude);
}