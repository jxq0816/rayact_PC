package com.bra.modules.sys.listener;

/**
 * Created by DDT on 2016/4/14.
 */

import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionListener;

/**
 * 在线用户监听
 */
public class OnlineUserListener implements SessionListener {
    @Override
    public void onStart(Session session) {//会话创建时触发
        System.out.println("会话创建：" + session.getId());
    }
    @Override
    public void onExpiration(Session session) {//会话过期时触发
        System.out.println("会话过期：" + session.getId());
    }
    @Override
    public void onStop(Session session) {//退出/会话过期时触发
        System.out.println("会话停止：" + session.getId());
    }
}