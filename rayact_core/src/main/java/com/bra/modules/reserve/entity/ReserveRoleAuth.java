package com.bra.modules.reserve.entity;

import com.bra.common.persistence.SaasEntity;
import com.bra.common.utils.Collections3;
import com.bra.common.utils.JsonUtils;
import com.bra.common.utils.StringUtils;
import com.bra.modules.reserve.entity.json.Authority;
import org.hibernate.validator.constraints.Length;

import java.util.ArrayList;
import java.util.List;

/**
 * 角色权限Entity
 * @author jiangxingqi
 * @version 2016-06-29
 */
public class ReserveRoleAuth extends SaasEntity<ReserveRoleAuth> {

	private static final long serialVersionUID = 1L;
	private String name;		// 角色
	private String userType; //角色编号
	private String authority;		// 对应用户权限(json字符串)

	private List<Authority> frontAuthorityList;//该字段用于传输用户所修改的权限
	private List<Authority> authorityList=new ArrayList<Authority>();

	public List<Authority> getAuthorityList() {
		if (Collections3.isEmpty(authorityList) && StringUtils.isNotBlank(authority)) {
			authorityList = JsonUtils.readBeanByJson(authority, List.class, Authority.class);
		}
		return authorityList;
	}

	public void setAuthorityList(List<Authority> authorityList) {
		this.authorityList = authorityList;
	}

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

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public List<Authority> getFrontAuthorityList() {
		return frontAuthorityList;
	}

	public void setFrontAuthorityList(List<Authority> frontAuthorityList) {
		this.frontAuthorityList = frontAuthorityList;
	}


}