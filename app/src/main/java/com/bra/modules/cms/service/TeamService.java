package com.bra.modules.cms.service;

import com.bra.common.persistence.Page;
import com.bra.common.service.CrudService;
import com.bra.modules.cms.dao.TeamDao;
import com.bra.modules.cms.entity.Team;
import com.bra.modules.mechanism.entity.AttMain;
import com.bra.modules.mechanism.web.bean.AttMainForm;
import com.bra.modules.sys.utils.StringUtils;
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
	public void save(Team team, AttMainForm attMainForm)throws Exception {
		if(attMainForm!=null){
			List<AttMain> list = attMainForm.getAttMains1();
			if(list!=null&&list.size()>0){
				for(AttMain att:list){
					if(att.getId()!=null)
						team.setPhoto(StringUtils.ATTPATH + list.get(0).getId());
				}
			}
		}
		List list = findListUn(team);
		if(list!=null&&list.size()>0){
			throw new Exception("数据重复");
		}else{
			super.save(team);
		}
		updateAttMain(team,attMainForm);
	}
	
	@Transactional(readOnly = false)
	public void delete(Team team) {
		super.delete(team);
	}

	public List<Map<String,String>> findListMap(Page<Team> page,Team team){
		team.setPage(page);
		return teamDao.findListMap(team);
	}
	public List<Team> findListUn(Team team){return teamDao.findListUn(team);}
	
}