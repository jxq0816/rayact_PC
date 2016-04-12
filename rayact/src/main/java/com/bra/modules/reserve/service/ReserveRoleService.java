package com.bra.modules.reserve.service;

import com.bra.common.persistence.Page;
import com.bra.common.service.CrudService;
import com.bra.common.utils.Collections3;
import com.bra.modules.reserve.dao.ReserveRoleDao;
import com.bra.modules.reserve.dao.ReserveVenueDao;
import com.bra.modules.reserve.entity.ReserveRole;
import com.bra.modules.reserve.entity.ReserveVenue;
import com.bra.modules.sys.entity.User;
import com.bra.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

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
    @Autowired
    private ReserveUserService userService;

    public ReserveRole get(String id) {
        return super.get(id);
    }

    public List<ReserveRole> findList(ReserveRole reserveRole) {
        return super.findList(reserveRole);
    }

    public List<String> findVenueIdsByRole(ReserveRole reserveRole) {
        List<ReserveVenue> venueList = reserveVenueDao.findList(new ReserveVenue());

        if("1".equals(reserveRole.getUser().getUserType())){
            return Collections3.extractToList(venueList, "id");
        }
        List<ReserveRole> reserveRoles = findList(reserveRole);
        if (!Collections3.isEmpty(reserveRoles)) {
            ReserveRole role = reserveRoles.get(0);
            if ("1".equals(role.getUserType())) {
                return Collections3.extractToList(venueList, "id");
            }
            return role.getVenueList();
        }
        List<String> rs=new ArrayList<>();
        rs.add("");
        return rs;
    }
    public List<User> findCheckOutUser() {
        ReserveRole reserveRole = new ReserveRole();
        reserveRole.setUser(UserUtils.getUser());
        List<String> venueIds = this.findVenueIdsByRole(reserveRole);
        String venueId=null;
        for(String i:venueIds){
            venueId=i;//收银所在职的场馆
        }
        User user=new User();
        user.setUserType("2");//用户类型(1:超级管理员；2:场馆管理员；3：高管；4：收银；5：财务)
        List<User> userList=userService.findList(user);//所有的场馆管理员
        List<User> authUsers=new ArrayList<>();//授权人
        for(User u:userList){
            ReserveRole role = new ReserveRole();
            role.setUser(u);
            List<String> ids = this.findVenueIdsByRole(role);
            for(String j:ids){
                //场馆管理员所在职的场馆编号为j
                if(venueId.equals(j)){
                    authUsers.add(u);
                }
            }
        }
        user.setUserType("3");//用户类型(1:超级管理员；2:场馆管理员；3：高管；4：收银；5：财务)
        List<User> bossList=userService.findList(user);//所有的高管
        authUsers.addAll(bossList);
        return authUsers;
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