package com.bra.modules.cms.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bra.common.persistence.Page;
import com.bra.common.service.CrudService;
import com.bra.modules.cms.entity.Apply;
import com.bra.modules.cms.dao.ApplyDao;

/**
 * 活动报名Service
 * @author ddt
 * @version 2016-05-18
 */
@Service
@Transactional(readOnly = true)
public class ApplyService extends CrudService<ApplyDao, Apply> {

	public Apply get(String id) {
		return super.get(id);
	}
	
	public List<Apply> findList(Apply apply) {
		return super.findList(apply);
	}
	
	public Page<Apply> findPage(Page<Apply> page, Apply apply) {
		return super.findPage(page, apply);
	}
	
	@Transactional(readOnly = false)
	public void save(Apply apply) {
		super.save(apply);
	}
	
	@Transactional(readOnly = false)
	public void delete(Apply apply) {
		super.delete(apply);
	}
	
}