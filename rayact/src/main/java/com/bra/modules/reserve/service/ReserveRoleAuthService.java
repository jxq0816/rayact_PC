package com.bra.modules.reserve.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bra.common.persistence.Page;
import com.bra.common.service.CrudService;
import com.bra.modules.reserve.entity.ReserveRoleAuth;
import com.bra.modules.reserve.dao.ReserveRoleAuthDao;

/**
 * 角色权限管理Service
 * @author jiangxingqi
 * @version 2016-06-28
 */
@Service
@Transactional(readOnly = true)
public class ReserveRoleAuthService extends CrudService<ReserveRoleAuthDao, ReserveRoleAuth> {

	public ReserveRoleAuth get(String id) {
		return super.get(id);
	}
	
	public List<ReserveRoleAuth> findList(ReserveRoleAuth reserveRoleAuth) {
		return super.findList(reserveRoleAuth);
	}
	
	public Page<ReserveRoleAuth> findPage(Page<ReserveRoleAuth> page, ReserveRoleAuth reserveRoleAuth) {
		return super.findPage(page, reserveRoleAuth);
	}
	
	@Transactional(readOnly = false)
	public void save(ReserveRoleAuth reserveRoleAuth) {
		super.save(reserveRoleAuth);
	}
	
	@Transactional(readOnly = false)
	public void delete(ReserveRoleAuth reserveRoleAuth) {
		super.delete(reserveRoleAuth);
	}
	
}