package com.bra.modules.reserve.entity;

import org.hibernate.validator.constraints.Length;
import com.bra.common.persistence.DataEntity;

/**
 * 角色权限管理Entity
 * @author jiangxingqi
 * @version 2016-06-28
 */
public class ReserveRoleAuth extends DataEntity<ReserveRoleAuth> {
	
	private static final long serialVersionUID = 1L;
	private String fkReserveRoleId;		// 角色ID
	private String authority;		// 对应用户权限(json字符串)
	
	public ReserveRoleAuth() {
		super();
	}

	public ReserveRoleAuth(String id){
		super(id);
	}

	@Length(min=1, max=19, message="角色ID长度必须介于 1 和 19 之间")
	public String getFkReserveRoleId() {
		return fkReserveRoleId;
	}

	public void setFkReserveRoleId(String fkReserveRoleId) {
		this.fkReserveRoleId = fkReserveRoleId;
	}
	
	@Length(min=0, max=5000, message="对应用户权限(json字符串)长度必须介于 0 和 5000 之间")
	public String getAuthority() {
		return authority;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
	}
	
}