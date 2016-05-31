package com.bra.modules.cms.dao;

import com.bra.common.persistence.CrudDao;
import com.bra.common.persistence.annotation.MyBatisDao;
import com.bra.modules.cms.entity.TeamMember;

import java.util.List;
import java.util.Map;

/**
 * 战队成员DAO接口
 * @author ddt
 * @version 2016-05-28
 */
@MyBatisDao
public interface TeamMemberDao extends CrudDao<TeamMember> {
	public List<Map<String,String>> findListMap(TeamMember teamMember);
}