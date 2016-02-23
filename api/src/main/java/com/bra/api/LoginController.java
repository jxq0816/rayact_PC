package com.bra.api;

import com.bra.api.entity.BackMsg;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by xiaobin on 16/2/16.
 */
@RestController
public class LoginController {

    //登录接口
    @ResponseBody
    @RequestMapping("/login")
    public BackMsg login(String username, String password) {
        return new BackMsg(username, password, null);
    }
}
