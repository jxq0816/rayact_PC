package com.bra.modules.cms.service;

import com.bra.common.persistence.Page;
import com.bra.common.service.CrudService;
import com.bra.modules.cms.dao.TeamDao;
import com.bra.modules.cms.entity.Team;
import com.bra.modules.mechanism.web.bean.AttMainForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * 战队Service
 * @author ddt
 * @version 2016-05-28
 */
@Service
@Transactional(readOnly = true)
public class TeamService extends CrudService<TeamDao, Team> {
	@Autowired
	private TeamDao teamDao;

	public Team get(String id) {
		return super.get(id);
	}
	
	public List<Team> findList(Team team) {
		return super.findList(team);
	}
	
	public Page<Team> findPage(Page<Team> page, Team team) {
		return super.findPage(page, team);
	}
	
	@Transactional(readOnly = false)
	public void save(Team team, AttMainForm attMainForm) {
		super.save(team);
		updateAttMain(team,attMainForm);
	}
	
	@Transactional(readOnly = false)
	public void delete(Team team) {
		super.delete(team);
	}

	public List<Map<String,String>> findListMap(Team team){
		return teamDao.findListMap(team);
	}
	
}