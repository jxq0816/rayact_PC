package com.bra.modules.reserve.web;

import com.bra.common.web.BaseController;
import com.bra.modules.reserve.service.MainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

/**
 * 监控Controller
 *
 * @author jiangxingqi
 * @version 2015-12-29
 */
@Controller
@RequestMapping(value = "${adminPath}/reserve/reserveListener")
public class ReserveListenerController extends BaseController {

    @Autowired
    private MainService mainService;

    @RequestMapping(value = "report")
    public String report(Model model) {
        Map<String, Object> data = mainService.controle();
        model.addAllAttributes(data);
        return "reserve/report/dataListenerReport";
    }
}