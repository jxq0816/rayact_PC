package com.bra.modules.reserve.service;

import com.bra.common.persistence.Page;
import com.bra.common.service.CrudService;
import com.bra.modules.reserve.dao.ReserveProjectDao;
import com.bra.modules.reserve.entity.ReserveProject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * 项目管理Service
 * @author xiaobin
 * @version 2015-12-29
 */
@Service
@Transactional(readOnly = true)
public class ReserveProjectService extends CrudService<ReserveProjectDao, ReserveProject> {

	public ReserveProject get(String id) {
		return super.get(id);
	}
	
	public List<ReserveProject> findList(ReserveProject reserveProject) {
		return super.findList(reserveProject);
	}

	public List<Map> findListForApp(ReserveProject reserveProject) {
		return dao.findListForApp(reserveProject);
	}
	
	public Page<ReserveProject> findPage(Page<ReserveProject> page, ReserveProject reserveProject) {
		return super.findPage(page, reserveProject);
	}
	
	@Transactional(readOnly = false)
	public void save(ReserveProject reserveProject) {
		super.save(reserveProject);
	}
	
	@Transactional(readOnly = false)
	public void delete(ReserveProject reserveProject) {
		super.delete(reserveProject);
	}
	
}