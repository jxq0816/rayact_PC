package com.bra.modules.reserve.web;

import com.bra.modules.reserve.service.MainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

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
        Map<String, Object> data = mainService.controle();
        model.addAllAttributes(data);
        return "reserve/main";
    }
}
