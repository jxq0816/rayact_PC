/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.bra.modules.cms.service;

import com.bra.common.persistence.Page;
import com.bra.common.service.CrudService;
import com.bra.modules.cms.dao.TeamTmpDao;
import com.bra.modules.cms.entity.TeamTmp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * 青草球队Service
 * @author 青草球队
 * @version 2016-07-26
 */
@Service
@Transactional(readOnly = true)
public class TeamTmpService extends CrudService<TeamTmpDao, TeamTmp> {
	@Autowired
	private TeamTmpDao teamTmpDao;

	public TeamTmp get(String id) {
		return super.get(id);
	}
	
	public List<TeamTmp> findList(TeamTmp teamTmp) {
		return super.findList(teamTmp);
	}
	public List<Map<String,String>> getMemberCount(TeamTmp teamTmp) {
		return teamTmpDao.getMemberCount(teamTmp);
	}
	
	public Page<TeamTmp> findPage(Page<TeamTmp> page, TeamTmp teamTmp) {
		return super.findPage(page, teamTmp);
	}
	
	@Transactional(readOnly = false)
	public void save(TeamTmp teamTmp) {
		super.save(teamTmp);
	}
	
	@Transactional(readOnly = false)
	public void delete(TeamTmp teamTmp) {
		super.delete(teamTmp);
	}
	@Transactional(readOnly = false)
	public void changeStatus(TeamTmp teamTmp) {
		teamTmpDao.changeStatus(teamTmp);
	}
	
}