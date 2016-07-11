package com.bra.modules.reserve.utils;

import com.bra.common.utils.*;
import com.bra.modules.reserve.entity.ReserveRole;
import com.bra.modules.reserve.entity.json.Authority;
import com.bra.modules.reserve.service.ReserveRoleService;
import com.bra.modules.reserve.service.ReserveUserService;
import com.bra.modules.sys.entity.User;
import com.bra.modules.sys.service.SystemService;
import com.bra.modules.sys.utils.UserUtils;
import com.google.common.collect.Lists;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by xiaobin on 16/1/20.
 */
public class AuthorityUtils {

    private static final String location = "properties/venue_auth.json";

    private static final Logger logger = LoggerFactory.getLogger(AuthorityUtils.class);

    private static ResourceLoader resourceLoader = new DefaultResourceLoader();

    public static boolean isAdmin() {
        return isAdmin(UserUtils.getUser().getId());
    }

    public static boolean isAdmin(String userId) {
        if (StringUtils.isBlank(userId))
            return false;
        SystemService systemService = SpringContextHolder.getBean("systemService");
        User user = systemService.getUser(userId);
        if ("1".equals(user.getUserType())) {
            return true;
        }

        ReserveUserService reserveUserService = SpringContextHolder.getBean("reserveUserService");
        ReserveRole reserveRole = reserveUserService.getRole(user);
        if (reserveRole == null)
            return false;
        return "1".equals(reserveRole.getUserType());
    }

    public static String hidePhone(String phone) {
        if (isAdmin(UserUtils.getUser().getId())) {
            return phone;
        }
        return StringUtils.hidePhone(phone);
    }

    public static String getVenueIds(List<String> venueIds) {
        return getVenueIds(venueIds, "a.venue_id");
    }

    public static String getVenueIds(List<String> venueIds, String defaultField) {
        List<String> venueIdInList = Lists.newArrayList();
        StringBuffer dsf = new StringBuffer(" and " + defaultField + " in(");

        if(venueIds.size()!=0){
            venueIdInList.addAll(venueIds.stream().map(venueId -> "'" + venueId + "'").collect(Collectors.toList()));
            dsf.append(StringUtils.join(venueIdInList, ','));
            dsf.append(")");
        }else{
            dsf.append("null)");
        }
        return dsf.toString();
    }

    //获取当前用户的场地权限
    public static String getDsf(String filedId) {
        ReserveRoleService reserveRoleService = SpringContextHolder.getBean("reserveRoleService");
        ReserveRole reserveRole = new ReserveRole();
        reserveRole.setUser(UserUtils.getUser());
        List<String> venueIds = reserveRoleService.findVenueIdsByRole(reserveRole);
        return getVenueIds(venueIds, filedId);
    }

    //获取当前用户的场馆
    public static String getVenueIdSql(String defaultField) {
        ReserveRole reserveRole = new ReserveRole();
        reserveRole.setUser(UserUtils.getUser());
        ReserveRoleService reserveRoleService = SpringContextHolder.getBean("reserveRoleService");
        List<String> venueIds = reserveRoleService.findVenueIdsByRole(reserveRole);
        return getVenueIds(venueIds, defaultField);
    }

    //获取用户类型
    public static String getUserType() {
        ReserveRole reserveRole = new ReserveRole();
        reserveRole.setUser(UserUtils.getUser());
        ReserveRoleService reserveRoleService = SpringContextHolder.getBean("reserveRoleService");
        List<ReserveRole> roleList = reserveRoleService.findList(reserveRole);
        if (Collections3.isEmpty(roleList)) {
            return null;
        }
        return roleList.get(0).getUserType();
    }

    public static List<Authority> getAuthByUser(User user) {
        if ("1".equals(user.getUserType())) {
            return getAll();
        }
        ReserveUserService reserveUserService = SpringContextHolder.getBean("reserveUserService");
        ReserveRole reserveRole = reserveUserService.getRole(user);
        return reserveRole.getAuthorityList();
    }

    private static String loadAuthJson() {
        InputStream is = null;
        Resource resource = resourceLoader.getResource(location);
        try {
            is = resource.getInputStream();
            return StreamUtils.InputStreamTOString(is);
        } catch (IOException e) {
            logger.info("Could not load properties from path:" + location + ", " + e.getMessage());
        } finally {
            IOUtils.closeQuietly(is);
        }
        return null;
    }

    public static List<Authority> getAll() {
        String json = loadAuthJson();
        return JsonUtils.readBeanByJson(json, List.class, Authority.class);
    }

    public static List<Authority> getAllBuilt() {
        List<Authority> list = getAll();
        List<Authority> builts = Lists.newArrayList();
        for (Authority authority : list) {
            builts.add(authority);
            List<Authority> child = authority.getAuthorityList();
            for (Authority auth : child) {
                builts.add(auth);
            }
        }
        return builts;
    }

    private static Authority getAuthority(String code, List<Authority> all) {
        if (StringUtils.isBlank(code))
            return null;
        for (Authority authority : all) {
            if (code.equals(authority.getCode())) {
                return new Authority(authority.getCode(), authority.getName(), authority.getHref());
            }
        }
        return null;
    }

    public static List<Authority> findByAuths(List<Authority> authorityList) {
        List<Authority> allAuth = getAllBuilt();
        List<Authority> search = Lists.newArrayList();
        Authority root;
        for (Authority authority : authorityList) {
            root = getAuthority(authority.getCode(), allAuth);
            if (root == null) {
                continue;
            }
            Authority childAuth;
            for (Authority child : authority.getAuthorityList()) {
                childAuth = getAuthority(child.getCode(), allAuth);
                if (childAuth != null) {
                    root.getAuthorityList().add(childAuth);
                }
            }
            search.add(root);
        }
        return search;
    }

    public static void main(String[] args) {
        System.out.println(loadAuthJson());
    }
}
