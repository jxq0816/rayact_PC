package com.bra.modules.reserve.service;

import com.bra.common.persistence.Page;
import com.bra.common.service.CrudService;
import com.bra.common.utils.Collections3;
import com.bra.common.utils.JsonUtils;
import com.bra.common.utils.StringUtils;
import com.bra.modules.reserve.dao.ReserveRoleDao;
import com.bra.modules.reserve.entity.ReserveRole;
import com.bra.modules.reserve.entity.json.Authority;
import com.bra.modules.reserve.utils.AuthorityUtils;
import com.bra.modules.sys.dao.UserDao;
import com.bra.modules.sys.entity.Office;
import com.bra.modules.sys.entity.User;
import com.bra.modules.sys.service.SystemService;
import com.bra.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by xiaobin on 16/1/20.
 */
@Service
@Transactional(readOnly = true)
public class ReserveUserService extends CrudService<UserDao, User> {

    @Autowired
    private ReserveRoleDao reserveRoleDao;

    public ReserveRole getRole(User user) {
        ReserveRole reserveRole = new ReserveRole();
        reserveRole.setUser(user);
        List<ReserveRole> roleList = reserveRoleDao.findList(reserveRole);
        if (!Collections3.isEmpty(roleList)) {
            return roleList.get(0);
        }
        return null;
    }

    public Page<User> findUser(Page<User> page, User user) {
        // 生成数据权限过滤条件（dsf为dataScopeFilter的简写，在xml中使用 ${sqlMap.dsf}调用权限SQL）
        // 设置分页参数
        Office company=UserUtils.getUser().getCompany();
        user.setCompany(company);
        user.setPage(page);
        // 执行分页查询
        page.setList(dao.findList(user));
        return page;
    }

    public ReserveRole getRoleByUser(User user) {
        ReserveRole reserveRole = new ReserveRole();
        reserveRole.setUser(user);
        List<ReserveRole> roleList = reserveRoleDao.findList(reserveRole);
        if (Collections3.isEmpty(roleList)) {
            return null;
        }
        return roleList.get(0);
    }

    @Transactional(readOnly = false)
    public void saveUser(User user) {
        user.setCompany(UserUtils.getUser().getCompany());
        if (StringUtils.isBlank(user.getId())) {
            user.preInsert();
            dao.insert(user);
        } else {
            // 更新用户数据
            user.preUpdate();
            dao.update(user);
        }

        ReserveRole reserveRole = getRoleByUser(user);
        if (reserveRole != null)
            reserveRoleDao.delete(reserveRole);
        else{
            reserveRole = new ReserveRole();
            reserveRole.setUser(user);
        }
        if (user.getReserveRole() != null) {
            List<Authority> authorities = AuthorityUtils.findByAuths(user.getReserveRole().getAuthorityList());
            String authJson = JsonUtils.writeObjectToJson(authorities);

            String venueJson = JsonUtils.writeObjectToJson(user.getReserveRole().getVenueList());
            reserveRole.setAuthority(authJson);
            reserveRole.setVenueJson(venueJson);
            reserveRole.setUserType(user.getUserType());
            reserveRole.preInsert();
            reserveRoleDao.insert(reserveRole);
        }
    }

    @Transactional(readOnly = false)
    public void deleteUser(User user) {
        dao.delete(user);
        // 清除用户缓存
        UserUtils.clearCache(user);
//		// 清除权限缓存
//		systemRealm.clearAllCachedAuthorizationInfo();
    }

    /**
     * 获取用户
     *
     * @param id
     * @return
     */
    public User getUser(String id) {
        return UserUtils.get(id);
    }

    /**
     * 根据登录名获取用户
     *
     * @param loginName
     * @return
     */
    public User getUserByLoginName(String loginName) {
        return UserUtils.getByLoginName(loginName);
    }


    @Transactional(readOnly = false)
    public void updateUserInfo(User user) {
        user.preUpdate();
        dao.updateUserInfo(user);
        // 清除用户缓存
        UserUtils.clearCache(user);
    }

    @Transactional(readOnly = false)
    public void updatePasswordById(String id, String loginName, String newPassword) {
        User user = new User(id);
        user.setPassword(SystemService.entryptPassword(newPassword));
        dao.updatePasswordById(user);
        // 清除用户缓存
        user.setLoginName(loginName);
        UserUtils.clearCache(user);
    }


}
