package com.bra.modules.cms.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bra.common.persistence.Page;
import com.bra.common.service.CrudService;
import com.bra.modules.cms.entity.Focus;
import com.bra.modules.cms.dao.FocusDao;

/**
 * 关注Service
 * @author ddt
 * @version 2016-05-23
 */
@Service
@Transactional(readOnly = true)
public class FocusService extends CrudService<FocusDao, Focus> {

	public Focus get(String id) {
		return super.get(id);
	}
	
	public List<Focus> findList(Focus focus) {
		return super.findList(focus);
	}
	
	public Page<Focus> findPage(Page<Focus> page, Focus focus) {
		return super.findPage(page, focus);
	}
	
	@Transactional(readOnly = false)
	public void save(Focus focus) {
		super.save(focus);
	}
	
	@Transactional(readOnly = false)
	public void delete(Focus focus) {
		super.delete(focus);
	}
	
}