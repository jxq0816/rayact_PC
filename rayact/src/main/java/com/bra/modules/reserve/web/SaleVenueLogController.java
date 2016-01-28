package com.bra.modules.reserve.web;

import com.bra.common.web.BaseController;
import com.bra.modules.reserve.entity.ReserveVenue;
import com.bra.modules.reserve.service.ReserveUserService;
import com.bra.modules.reserve.service.ReserveVenueConsService;
import com.bra.modules.reserve.service.ReserveVenueService;
import com.bra.modules.reserve.utils.AuthorityUtils;
import com.bra.modules.reserve.web.form.SaleVenueLog;
import com.bra.modules.sys.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 场地售卖日志表
 * Created by xiaobin on 16/1/28.
 */
@Controller
@RequestMapping(value = "${adminPath}/reserve/saleVenue")
public class SaleVenueLogController extends BaseController {

    @Autowired
    private ReserveVenueConsService reserveVenueConsService;
    @Autowired
    private ReserveUserService reserveUserService;
    @Autowired
    private ReserveVenueService reserveVenueService;

    @RequestMapping(value = "list")
    public String list(Model model, SaleVenueLog venueLog) {

        model.addAttribute("userList",reserveUserService.findList(new User()));
        ReserveVenue venue = new ReserveVenue();
        venue.getSqlMap().put("dsf",AuthorityUtils.getVenueIdSql("a.id"));
        model.addAttribute("venueList",reserveVenueService.findList(venue));
        model.addAttribute("venueLog",venueLog);

        venueLog.setDsf(AuthorityUtils.getVenueIdSql("a.venue_id"));
        model.addAttribute("venueLogList", reserveVenueConsService.findOrderLog(venueLog));
        return "/reserve/saleField/saleVenueLog";
    }
}
