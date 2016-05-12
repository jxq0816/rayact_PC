package com.bra.modules.reserve.web;

import com.alibaba.fastjson.JSON;
import com.bra.common.persistence.Page;
import com.bra.common.web.BaseController;
import com.bra.modules.reserve.entity.ReserveVenue;
import com.bra.modules.reserve.service.ReserveVenueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 场馆管理Controller
 *
 * @author jiangxingqi
 * @version 2016-5-12
 */
@Controller
@RequestMapping(value = "${adminPath}/app/reserve/reserveVenue")
public class ReserveAppVenueController extends BaseController {
    @Autowired
    private ReserveVenueService reserveVenueService;

    @RequestMapping(value = {"list", ""})
    public String list(ReserveVenue reserveVenue, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<ReserveVenue> page = reserveVenueService.findPage(new Page<>(request, response), reserveVenue);
        model.addAttribute("page", page);
        return JSON.toJSONString(page);
    }
}