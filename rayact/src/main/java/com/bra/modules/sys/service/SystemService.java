/**
 * Copyright &copy; 2012-2014 <a href="https://github.com.bra.>JeeSite</a> All rights reserved.
 */
package com.bra.modules.sys.service;

import com.bra.common.config.Global;
import com.bra.common.security.Digests;
import com.bra.common.security.shiro.session.SessionDAO;
import com.bra.common.service.BaseService;
import com.bra.common.utils.Encodes;
import com.bra.common.utils.MD5Util;
import com.bra.common.utils.StringUtils;
import com.bra.common.web.Servlets;
import com.bra.modules.sys.dao.UserDao;
import com.bra.modules.sys.entity.User;
import com.bra.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * 系统管理，安全相关实体的管理类,包括用户、角色、菜单.
 *
 * @version 2013-12-05
 */
@Service
@Transactional(readOnly = true)
public class SystemService extends BaseService {

    public static final int HASH_INTERATIONS = 1024;

    @Autowired
    private UserDao userDao;
    @Autowired
    private SessionDAO sessionDao;

    public SessionDAO getSessionDao() {
        return sessionDao;
    }

    //-- User Service --//

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
        User user=UserUtils.getByLoginName(loginName);
        return user;
    }

    //查询用户授权码
    public User checkUserAuth(String userId,String authPassword) {
        User userFromBack=userDao.get(userId);
        String checkNo=userFromBack.getCheckoutPwd();
        if(StringUtils.isNoneEmpty(checkNo)&&checkNo.equals(authPassword)){
            return userFromBack;
        }
        return null;
    }

    @Transactional(readOnly = false)
    public void updateUserLoginInfo(User user) {
        // 保存上次登录信息
        user.setOldLoginIp(user.getLoginIp());
        user.setOldLoginDate(user.getLoginDate());
        // 更新本次登录信息
        user.setLoginIp(StringUtils.getRemoteAddr(Servlets.getRequest()));
        user.setLoginDate(new Date());
        userDao.updateLoginInfo(user);
    }

    /**
     * 生成安全的密码，生成随机的16位salt并经过1024次 sha-1 hash
     */
    public static String entryptPassword(String plainPassword) {
        return MD5Util.getMD5String(plainPassword);
    }

    /**
     * 验证密码
     *
     * @param plainPassword 明文密码
     * @param password      密文密码
     * @return 验证成功返回true
     */
    public static boolean validatePassword(String plainPassword, String password) {
        byte[] salt = Encodes.decodeHex(password.substring(0, 16));
        byte[] hashPassword = Digests.sha1(plainPassword.getBytes(), salt, HASH_INTERATIONS);
        return password.equals(Encodes.encodeHex(salt) + Encodes.encodeHex(hashPassword));
    }

    /**
     * 获取Key加载信息
     */
    public static boolean printKeyLoadMessage() {
        StringBuilder sb = new StringBuilder();
        sb.append("\r\n======================================================================\r\n");
        sb.append("\r\n    " + Global.getConfig("productName") + "  版权所有\r\n");
        sb.append("\r\n======================================================================\r\n");
        System.out.println(sb.toString());
        return true;
    }


}
