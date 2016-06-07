package com.bra.modules.cms.service;

import com.bra.common.persistence.Page;
import com.bra.common.service.CrudService;
import com.bra.modules.cms.dao.TeamMemberDao;
import com.bra.modules.cms.entity.Team;
import com.bra.modules.cms.entity.TeamMember;
import com.bra.modules.sys.entity.User;
import com.bra.modules.sys.service.SystemService;
import com.bra.modules.sys.utils.StringUtils;
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
	private SystemService systemService;
	@Autowired
	private TeamService teamService;
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
		String phone = teamMember.getPhone();
		if(!StringUtils.isNull(phone)){
			User user = new User();
			user.setLoginName(phone);
			List<User> users = systemService.findListApi(user);
			if(users!=null && users.size()>0){
				teamMember.setMemberId(users.get(0).getId());
			}
		}
		super.save(teamMember);
		Team t = teamService.get(teamMember.getTeam().getId());
		if(!StringUtils.isNull(teamMember.getMemberId())&&t.getPersonNum()>0){
			t.setMemberIds(t.getMemberIds()+","+teamMember.getMemberId());
		}else if(!StringUtils.isNull(teamMember.getMemberId())&&t.getPersonNum()<=0){
			t.setMemberIds(teamMember.getMemberId());
		}
		t.setPersonNum(t.getPersonNum()+1);
		teamService.save(t);
	}
	
	@Transactional(readOnly = false)
	public void delete(TeamMember teamMember) {
		super.delete(teamMember);
		Team t = teamService.get(teamMember.getTeam().getId());
		String ids = t.getMemberIds();
		ids = ids.replaceAll(teamMember.getMemberId()+",","");
		t.setMemberIds(ids);
		t.setPersonNum(t.getPersonNum()-1);
		teamService.save(t);
	}

	public List<Map<String,String>> findListMap(TeamMember teamMember){
		return teamMemberDao.findListMap(teamMember);
	}
	
}