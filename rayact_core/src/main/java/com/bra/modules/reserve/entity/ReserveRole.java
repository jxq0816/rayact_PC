package com.bra.modules.reserve.entity;

import com.bra.common.persistence.SaasEntity;
import com.bra.common.utils.Collections3;
import com.bra.common.utils.JsonUtils;
import com.bra.common.utils.StringUtils;
import com.bra.modules.reserve.entity.json.Authority;
import com.bra.modules.sys.entity.User;
import com.google.common.collect.Lists;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 场馆用户角色Entity
 *
 * @author 肖斌
 * @version 2016-01-20
 */
public class ReserveRole extends SaasEntity<ReserveRole> {

    private static final long serialVersionUID = 1L;
    private User user;        // 对应用户ID
    private String authority;        // 对应用户权限(json字符串)
    private String userType;        // 用户类型(1:超级管理员;2:场馆管理员;3:高管;4:收银;5:财务)
    private String venueJson;        // 对应场馆的id(json字符串)

    public ReserveRole() {
        super();
    }

    public ReserveRole(String id) {
        super(id);
    }

    @NotNull(message = "对应用户ID不能为空")
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Length(min = 0, max = 500, message = "对应用户权限(json字符串)长度必须介于 0 和 500 之间")
    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    @Length(min = 0, max = 2, message = "用户类型(1:超级管理员;2:场馆管理员;3:高管;4:收银;5:财务)长度必须介于 0 和 2 之间")
    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    @Length(min = 0, max = 300, message = "对应场馆的id(json字符串)长度必须介于 0 和 300 之间")
    public String getVenueJson() {
        return venueJson;
    }

    public void setVenueJson(String venueJson) {
        this.venueJson = venueJson;
    }

    //------------------------和数据库无关字段------------------------

    private List<Authority> authorityList = Lists.newArrayList();

    private List<String> venueList = Lists.newArrayList();

    public List<Authority> getAuthorityList() {
        if (Collections3.isEmpty(authorityList) && StringUtils.isNotBlank(authority)) {
            authorityList = JsonUtils.readBeanByJson(authority, List.class, Authority.class);
        }
        return authorityList;
    }

    public void setAuthorityList(List<Authority> authorityList) {
        this.authorityList = authorityList;
    }

    public List<String> getVenueList() {
        if (Collections3.isEmpty(venueList) && StringUtils.isNotBlank(venueJson)) {
            venueList = JsonUtils.readBeanByJson(venueJson, List.class, String.class);
        }
        return venueList;
    }

    public void setVenueList(List<String> venueList) {
        this.venueList = venueList;
    }
}