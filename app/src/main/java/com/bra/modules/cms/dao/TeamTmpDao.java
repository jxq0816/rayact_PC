/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.bra.modules.cms.dao;

import com.bra.common.persistence.CrudDao;
import com.bra.common.persistence.annotation.MyBatisDao;
import com.bra.modules.cms.entity.TeamTmp;

import java.util.List;
import java.util.Map;

/**
 * 青草球队DAO接口
 * @author 青草球队
 * @version 2016-07-26
 */
@MyBatisDao
public interface TeamTmpDao extends CrudDao<TeamTmp> {
	public void changeStatus(TeamTmp teamTmp);
    public List<Map<String,String>> getMemberCount(TeamTmp teamTmp);
}