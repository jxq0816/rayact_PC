package com.bra.modules.cms.service;

import com.bra.common.persistence.Page;
import com.bra.common.service.CrudService;
import com.bra.modules.cms.dao.FocusDao;
import com.bra.modules.cms.entity.Focus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 关注Service
 * @author ddt
 * @version 2016-05-23
 */
@Service
@Transactional(readOnly = true)
public class FocusService extends CrudService<FocusDao, Focus> {
	@Autowired
	private FocusDao focusDao;
	public Focus get(String id) {
		return super.get(id);
	}
	
	public List<Focus> findList(Focus focus) {
		return super.findList(focus);
	}
	public List<Focus> findListUn(Focus focus) {
		return dao.findListUn(focus);
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
	@Transactional(readOnly = false)
	public void updateFocus(Focus focus){
		focusDao.updateFocus(focus);
	}
	
}