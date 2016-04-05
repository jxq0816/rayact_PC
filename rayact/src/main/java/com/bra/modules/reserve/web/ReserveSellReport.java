package com.bra.modules.reserve.web;

import com.bra.common.web.BaseController;
import com.bra.modules.reserve.entity.ReserveCardStatements;
import com.bra.modules.reserve.entity.ReserveVenue;
import com.bra.modules.reserve.service.ReserveCardStatementsService;
import com.bra.modules.reserve.service.ReserveVenueService;
import com.bra.modules.reserve.utils.AuthorityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

/**
 * Created by DDT on 2016/4/1.
 */
@Controller
@RequestMapping(value = "${adminPath}/reserve/reserveSellReport")
public class ReserveSellReport extends BaseController {
    @Autowired
    private ReserveCardStatementsService reserveCardStatementsService;
    @Autowired
    private ReserveVenueService reserveVenueService;
    @RequestMapping(value = "list")
    public String list(Model model, ReserveCardStatements reserveCardStatements) {
        ReserveVenue reserveVenue = reserveCardStatements.getVenue();
        if (reserveVenue != null&&reserveVenue.getId() != null) {
            if (reserveCardStatements.getSqlMap().get("dsf") == null)
                reserveCardStatements.getSqlMap().put("dsf", " and s.venue_id = '"+reserveVenue.getId()+"'  ");
        }else{
            if (reserveCardStatements.getSqlMap().get("dsf") == null)
                reserveCardStatements.getSqlMap().put("dsf", AuthorityUtils.getDsf("s.venue_id"));
        }
        List<Map<String,Object>> rtn = reserveCardStatementsService.allReport(reserveCardStatements);
        ReserveVenue v = new ReserveVenue();
        List<ReserveVenue> list = reserveVenueService.findList(v);
        model.addAttribute("rtn",rtn);
        model.addAttribute("reserveVenueList",list);
        model.addAttribute("reserveCardStatements",reserveCardStatements);
        return "/reserve/report/allReport";
    }
}
