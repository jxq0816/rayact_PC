package com.bra.modules.cms.dao;

import com.bra.common.persistence.CrudDao;
import com.bra.common.persistence.annotation.MyBatisDao;
import com.bra.modules.cms.entity.Activity;

import java.util.List;
import java.util.Map;

/**
 * 活动DAO接口
 * @author ddt
 * @version 2016-05-18
 */
@MyBatisDao
public interface ActivityDao extends CrudDao<Activity> {
	public List<Map<String,String>> findListMap(Activity activity);
}