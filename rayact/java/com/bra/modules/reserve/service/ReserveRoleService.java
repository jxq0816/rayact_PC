package com.bra.modules.reserve.service;

import java.util.List;

import com.bra.common.utils.Collections3;
import com.bra.modules.reserve.dao.ReserveVenueDao;
import com.bra.modules.reserve.entity.ReserveVenue;
import com.bra.modules.sys.entity.Role;
import com.bra.modules.sys.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bra.common.persistence.Page;
import com.bra.common.service.CrudService;
import com.bra.modules.reserve.entity.ReserveRole;
import com.bra.modules.reserve.dao.ReserveRoleDao;

/**
 * 场馆用户角色Service
 *
 * @author 肖斌
 * @version 2016-01-20
 */
@Service
@Transactional(readOnly = true)
public class ReserveRoleService extends CrudService<ReserveRoleDao, ReserveRole> {

    @Autowired
    private ReserveVenueDao reserveVenueDao;

    public ReserveRole get(String id) {
        return super.get(id);
    }

    public List<ReserveRole> findList(ReserveRole reserveRole) {
        return super.findList(reserveRole);
    }

    public List<String> findVenueIdsByRole(ReserveRole reserveRole) {
        List<Role> roleList = reserveRole.getUser().getRoleList();
        if (Collections3.isEmpty(roleList)) {
            return null;
        }
        List<ReserveVenue> venueList = reserveVenueDao.findList(new ReserveVenue());
        for (Role role : roleList) {
            if (Role.COOPERATIVE.equals(role.getEnname())) {//管理员
                return Collections3.extractToList(venueList, "id");
            }
        }
        List<ReserveRole> reserveRoles = findList(reserveRole);
        if (!Collections3.isEmpty(reserveRoles)) {
            ReserveRole role = reserveRoles.get(0);
            if ("1".equals(role.getUserType())) {
                return Collections3.extractToList(venueList, "id");
            }
            return role.getVenueList();
        }
        return null;
    }

    public Page<ReserveRole> findPage(Page<ReserveRole> page, ReserveRole reserveRole) {
        return super.findPage(page, reserveRole);
    }

    @Transactional(readOnly = false)
    public void save(ReserveRole reserveRole) {
        super.save(reserveRole);
    }

    @Transactional(readOnly = false)
    public void delete(ReserveRole reserveRole) {
        super.delete(reserveRole);
    }

}