package com.bra.modules.reserve.service;

import com.bra.common.persistence.Page;
import com.bra.common.service.CrudService;
import com.bra.common.utils.JsonUtils;
import com.bra.modules.reserve.dao.ReserveRoleAuthDao;
import com.bra.modules.reserve.entity.ReserveRoleAuth;
import com.bra.modules.reserve.entity.json.Authority;
import com.bra.modules.reserve.utils.AuthorityUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
		List<Authority> authorities = AuthorityUtils.findByAuths(reserveRoleAuth.getAuthorityList());
		String authJson = JsonUtils.writeObjectToJson(authorities);
		reserveRoleAuth.setAuthority(authJson);
		super.save(reserveRoleAuth);
	}
	
	@Transactional(readOnly = false)
	public void delete(ReserveRoleAuth reserveRoleAuth) {
		super.delete(reserveRoleAuth);
	}
	
}