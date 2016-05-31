package com.bra.modules.cms.dao;

import com.bra.common.persistence.CrudDao;
import com.bra.common.persistence.annotation.MyBatisDao;
import com.bra.modules.cms.entity.Team;

import java.util.List;
import java.util.Map;

/**
 * 战队DAO接口
 * @author ddt
 * @version 2016-05-28
 */
@MyBatisDao
public interface TeamDao extends CrudDao<Team> {
    public List<Map<String,String>> findListMap(Team team);
	
}