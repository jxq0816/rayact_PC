package com.bra.modules.reserve.service;

import com.bra.common.persistence.Page;
import com.bra.common.service.CrudService;
import com.bra.common.service.ServiceException;
import com.bra.common.utils.CacheUtils;
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
        user.getSqlMap().put("dsf", dataScopeFilter(user.getCurrentUser(), "o", "a"));
        // 设置分页参数
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
        user.setOffice(UserUtils.getUser().getOffice());
        if (StringUtils.isBlank(user.getId())) {
            user.preInsert();
            dao.insert(user);
        } else {
            // 清除原用户机构用户缓存
            User oldUser = dao.get(user.getId());
            if (oldUser.getOffice() != null && oldUser.getOffice().getId() != null) {
                CacheUtils.remove(UserUtils.USER_CACHE, UserUtils.USER_CACHE_LIST_BY_OFFICE_ID_ + oldUser.getOffice().getId());
            }
            // 更新用户数据
            user.preUpdate();
            dao.update(user);
        }

        ReserveRole reserveRole = getRoleByUser(user);
        if (reserveRole != null)
            reserveRoleDao.delete(reserveRole);
        if (user.getReserveRole() != null) {
            List<Authority> authorities = AuthorityUtils.findByAuths(user.getReserveRole().getAuthorityList());
            String authJson = JsonUtils.writeObjectToJson(authorities);

            String venueJson = JsonUtils.writeObjectToJson(user.getReserveRole().getVenueList());
            reserveRole.setAuthority(authJson);
            reserveRole.setVenueJson(venueJson);
            reserveRole.setUserType(user.getReserveRole().getUserType());
            reserveRole.preInsert();
            reserveRoleDao.insert(reserveRole);
        }

        if (StringUtils.isNotBlank(user.getId())) {
            // 更新用户与角色关联
            dao.deleteUserRole(user);
            //user.setRoleList(UserUtils.getRoleList());
            if (user.getRoleList() != null && user.getRoleList().size() > 0) {
                dao.insertUserRole(user);
            } else {
                throw new ServiceException(user.getLoginName() + "没有设置角色！");
            }
            // 清除用户缓存
            UserUtils.clearCache(user);
//			// 清除权限缓存
//			systemRealm.clearAllCachedAuthorizationInfo();
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

    /**
     * 通过部门ID获取用户列表，仅返回用户id和name（树查询用户时用）
     *
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<User> findUserByOfficeId(String officeId) {
        List<User> list = (List<User>) CacheUtils.get(UserUtils.USER_CACHE, UserUtils.USER_CACHE_LIST_BY_OFFICE_ID_ + officeId);
        if (list == null) {
            User user = new User();
            user.setOffice(new Office(officeId));
            list = dao.findUserByOfficeId(user);
            CacheUtils.put(UserUtils.USER_CACHE, UserUtils.USER_CACHE_LIST_BY_OFFICE_ID_ + officeId, list);
        }
        return list;
    }


}
