package com.bra.modules.sys.web;

import com.alibaba.fastjson.JSONObject;
import com.bra.common.web.BaseController;
import com.bra.modules.sys.entity.User;
import com.bra.modules.sys.service.SystemService;
import com.bra.modules.sys.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by DDT on 2016/5/9.
 */
@Controller
public class RegistController extends BaseController {
    @Autowired
    private SystemService systemService;

    /**
     * 快速注册
     */
    @RequestMapping(value = "${adminPath}/api/createSimple", method = RequestMethod.POST)
    @ResponseBody
    public String createSimple(HttpServletRequest request, HttpServletResponse response) {
        JSONObject rtn = new JSONObject();
        try{
            String phone = request.getParameter("username");
            String password = request.getParameter("password");
            String qq = request.getParameter("qq");
            String qqName = request.getParameter("qqName");
            String weixin = request.getParameter("weixin");
            String weixinName = request.getParameter("weixinName");
            String qqImage = request.getParameter("qqImage");
            String weixinImage = request.getParameter("weixinImage");
            User a = systemService.getUserByMobile(phone);
            if(a != null){
                rtn.put("msg","该手机号已注册！");
                rtn.put("status","fail");
            }else{
                User user = new User();
                if(!StringUtils.isNull(phone)){
                    user.setName(phone);
                    user.setLoginName(phone);
                    user.setMobile(phone);
                }
                if(!StringUtils.isNull(password)){
                    user.setPassword(StringUtils.entryptPassword(password));
                }
                if(!StringUtils.isNull(qq)&&!StringUtils.isNull(qqName)){
                    user.setQq(qq);
                    user.setQqName(qqName);
                    user.setPhoto(qqImage);
                }
                if(!StringUtils.isNull(weixin)&&!StringUtils.isNull(weixinName)){
                    user.setWeixin(weixin);
                    user.setWeixinName(weixinName);
                    user.setPhoto(weixinImage);
                }
                systemService.saveUser(user);
                rtn.put("status","success");
                rtn.put("msg",user.getId());
            }
        }catch(Exception e){
            rtn.put("status","fail");
            rtn.put("msg",e.getMessage());
        }
        try {
            response.reset();
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            response.getWriter().print(rtn.toJSONString());
            return null;
        } catch (IOException e) {
            return null;
        }
    }
}
