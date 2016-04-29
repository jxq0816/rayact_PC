package com.bra.modules.sys.listener;

/**
 * Created by DDT on 2016/4/14.
 */

import com.bra.modules.sys.web.LoginController;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionListener;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 在线用户监听
 */
public class OnlineUserListener implements SessionListener {
    @Override
    public void onStart(Session session) {//会话创建时触发
        //System.out.println("会话创建：" + session.getId());
    }
    @Override
    public void onExpiration(Session session) {//会话过期时触发
        //System.out.println("会话过期：" + session.getId());//
        List<Map<String,String>> users = LoginController.users;
        Iterator<Map<String,String>> it = users.iterator();
        while(it.hasNext()){
            Map<String,String> tempobj = it.next();
            if(tempobj.get("sid").equals(session.getId())){
                it.remove();
            }
        }
    }
    @Override
    public void onStop(Session session) {//退出/会话过期时触发
        //System.out.println("会话停止：" + session.getId());
        List<Map<String,String>> users = LoginController.users;
        Iterator<Map<String,String>> it = users.iterator();
        while(it.hasNext()){
            Map<String,String> tempobj = it.next();
            if(tempobj.get("sid").equals(session.getId())){
                it.remove();
            }
        }
    }
}