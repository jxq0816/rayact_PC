package com.bra.modules.cms.service;

import com.bra.common.persistence.Page;
import com.bra.common.service.CrudService;
import com.bra.modules.cms.dao.TeamMemberDao;
import com.bra.modules.cms.entity.TeamMember;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * 战队成员Service
 * @author ddt
 * @version 2016-05-28
 */
@Service
@Transactional(readOnly = true)
public class TeamMemberService extends CrudService<TeamMemberDao, TeamMember> {
	@Autowired
	private TeamMemberDao teamMemberDao;

	public TeamMember get(String id) {
		return super.get(id);
	}
	
	public List<TeamMember> findList(TeamMember teamMember) {
		return super.findList(teamMember);
	}
	
	public Page<TeamMember> findPage(Page<TeamMember> page, TeamMember teamMember) {
		return super.findPage(page, teamMember);
	}
	
	@Transactional(readOnly = false)
	public void save(TeamMember teamMember) {
		super.save(teamMember);
	}
	
	@Transactional(readOnly = false)
	public void delete(TeamMember teamMember) {
		super.delete(teamMember);
	}

	public List<Map<String,String>> findListMap(TeamMember teamMember){
		return teamMemberDao.findListMap(teamMember);
	}
	
}