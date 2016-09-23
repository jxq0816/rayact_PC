package com.bra.modules.sys.web;

import com.alibaba.fastjson.JSONObject;
import com.bra.common.web.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by DDT on 2016/5/9.
 */
@Controller
public class VersionCheckController extends BaseController {

    /**
     * 版本检测
     */
    @RequestMapping(value = "${adminPath}/api/checkVersion")
    public void createSimple(HttpServletRequest request, HttpServletResponse response) {
        JSONObject rtn = new JSONObject();
        rtn.put("version",com.bra.modules.sys.utils.StringUtils.CURRENT_VERSION);
        try {
            response.reset();
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            response.getWriter().print(rtn.toJSONString());
        } catch (IOException e) {
        }
    }
}
