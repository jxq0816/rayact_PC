/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.bra.modules.cms.dao;

import com.bra.common.persistence.CrudDao;
import com.bra.common.persistence.annotation.MyBatisDao;
import com.bra.modules.cms.entity.TeamMemberTmp;

/**
 * 青草球队队员DAO接口
 * @author 青草球队队员
 * @version 2016-07-26
 */
@MyBatisDao
public interface TeamMemberTmpDao extends CrudDao<TeamMemberTmp> {
	
}