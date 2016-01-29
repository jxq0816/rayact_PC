package com.bra.modules.reserve.web;

import com.bra.common.security.Principal;
import com.bra.common.security.SecurityUtil;
import com.bra.modules.reserve.service.MainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * Created by xiaobin on 16/1/25.
 */
@Controller
@RequestMapping(value = "${adminPath}/reserve")
public class MainController {

    @Autowired
    private MainService mainService;

    @RequestMapping(value = "main")
    public String main(Model model) {
        Principal principal = SecurityUtil.getPrincipal();
        if (principal.isMobileLogin()) {
            return "modules/sys/main";
        }
        Map<String, Object> data = mainService.controle();
        model.addAllAttributes(data);
        return "reserve/main";
    }

    @RequestMapping(value = "loadJson")
    @ResponseBody
    public Map<String, Object> loadJson() {
        Map<String, Object> map = mainService.controle();
        return map;
    }

    @RequestMapping(value = "salesMain")
    public String salesMain(Model model) {
        return "reserve/salesMain";
    }
}
