/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.bra.modules.cms.service;

import com.bra.common.persistence.Page;
import com.bra.common.service.CrudService;
import com.bra.modules.cms.dao.TeamMemberTmpDao;
import com.bra.modules.cms.entity.TeamMemberTmp;
import com.bra.modules.mechanism.entity.AttMain;
import com.bra.modules.mechanism.web.bean.AttMainForm;
import com.bra.modules.sys.utils.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 青草球队队员Service
 * @author 青草球队队员
 * @version 2016-07-26
 */
@Service
@Transactional(readOnly = true)
public class TeamMemberTmpService extends CrudService<TeamMemberTmpDao, TeamMemberTmp> {

	public TeamMemberTmp get(String id) {
		return super.get(id);
	}
	
	public List<TeamMemberTmp> findList(TeamMemberTmp teamMemberTmp) {
		return super.findList(teamMemberTmp);
	}
	
	public Page<TeamMemberTmp> findPage(Page<TeamMemberTmp> page, TeamMemberTmp teamMemberTmp) {
		return super.findPage(page, teamMemberTmp);
	}
	
	@Transactional(readOnly = false)
	public void save(TeamMemberTmp teamMemberTmp, AttMainForm attMainForm) {
		String remarks = "";
		if(attMainForm!=null){
			List<AttMain> list = attMainForm.getAttMains1();
			if(list!=null&&list.size()>0){
				for(AttMain att:list){
					if(att.getId()!=null&&!StringUtils.isNull(att.getId()))
						remarks += StringUtils.ATTPATH + att.getId()+";";
				}
			}
		}
		if(!StringUtils.isNull(remarks)){
			teamMemberTmp.setRemarks(remarks);
		}
		super.save(teamMemberTmp);
		updateAttMain(teamMemberTmp,attMainForm);
	}
	
	@Transactional(readOnly = false)
	public void delete(TeamMemberTmp teamMemberTmp) {
		super.delete(teamMemberTmp);
	}
	
}