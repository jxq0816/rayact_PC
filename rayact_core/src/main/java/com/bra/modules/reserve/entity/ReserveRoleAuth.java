package com.bra.modules.reserve.entity;

import com.bra.common.persistence.SaasEntity;
import org.hibernate.validator.constraints.Length;

/**
 * 角色权限Entity
 * @author jiangxingqi
 * @version 2016-06-29
 */
public class ReserveRoleAuth extends SaasEntity<ReserveRoleAuth> {

	private static final long serialVersionUID = 1L;
	private String name;		// 角色
	private String authority;		// 对应用户权限(json字符串)

	public ReserveRoleAuth() {
		super();
	}

	public ReserveRoleAuth(String id){
		super(id);
	}

	@Length(min=1, max=19, message="角色长度必须介于 1 和 19 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Length(min=0, max=5000, message="对应用户权限(json字符串)长度必须介于 0 和 5000 之间")
	public String getAuthority() {
		return authority;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
	}

}