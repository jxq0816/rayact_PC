package com.bra.modules.sys.listener;

/**
 * Created by DDT on 2016/4/14.
 */

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionListener;

/**
 * 在线用户监听
 */
public class OnlineUserListener implements SessionListener {
    private static final Log logger = LogFactory.getLog(OnlineUserListener.class);
    @Override
    public void onStart(Session session) {//会话创建时触发
        logger.info("session start：" + session.getId()+"&host="+session.getHost()+"&timeout="+session.getTimeout()+"&lastaccess="+session.getLastAccessTime());
        System.out.println("会话开始：" + session.getId()+"&host="+session.getHost()+"&timeout="+session.getTimeout()+"&lastaccess="+session.getLastAccessTime());//
    }
    @Override
    public void onExpiration(Session session) {//会话过期时触发
        logger.info("session expiration：" + session.getId()+"&host="+session.getHost()+"&timeout="+session.getTimeout()+"&lastaccess="+session.getLastAccessTime());
        System.out.println("会话过期：" + session.getId()+"&host="+session.getHost()+"&timeout="+session.getTimeout()+"&lastaccess="+session.getLastAccessTime());//
//        List<Map<String,String>> users = LoginController.users;
//        Iterator<Map<String,String>> it = users.iterator();
//        while(it.hasNext()){
//            Map<String,String> tempobj = it.next();
//            if(tempobj.get("sid").equals(session.getId())){
//                it.remove();
//            }
//        }
    }
    @Override
    public void onStop(Session session) {//退出/会话过期时触发
        logger.info("session stop：" + session.getId()+"&host="+session.getHost()+"&timeout="+session.getTimeout()+"&lastaccess="+session.getLastAccessTime());
        System.out.println("会话stop：" + session.getId()+"&host="+session.getHost()+"&timeout="+session.getTimeout()+"&lastaccess="+session.getLastAccessTime());//
//        List<Map<String,String>> users = LoginController.users;
//        Iterator<Map<String,String>> it = users.iterator();
//        while(it.hasNext()){
//            Map<String,String> tempobj = it.next();
//            if(tempobj.get("sid").equals(session.getId())){
//                it.remove();
//            }
//        }
    }
}