package com.bra.modules.reserve.service;

import com.bra.common.persistence.Page;
import com.bra.common.service.CrudService;
import com.bra.modules.reserve.dao.ReserveUserDao;
import com.bra.modules.reserve.entity.ReserveUser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 商家用户Service
 * @author jiangxingqi
 * @version 2016-07-12
 */
@Service
@Transactional(readOnly = true)
public class ReserveUserService extends CrudService<ReserveUserDao, ReserveUser> {

	public ReserveUser get(String id) {
		return super.get(id);
	}
	
	public List<ReserveUser> findList(ReserveUser reserveUser) {
		return super.findList(reserveUser);
	}
	
	public Page<ReserveUser> findPage(Page<ReserveUser> page, ReserveUser reserveUser) {
		return super.findPage(page, reserveUser);
	}
	
	@Transactional(readOnly = false)
	public void save(ReserveUser reserveUser) {
		super.save(reserveUser);
	}
	
	@Transactional(readOnly = false)
	public void delete(ReserveUser reserveUser) {
		super.delete(reserveUser);
	}
	
}