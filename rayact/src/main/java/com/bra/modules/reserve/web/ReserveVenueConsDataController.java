package com.bra.modules.reserve.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bra.modules.reserve.entity.ReserveRole;
import com.bra.modules.reserve.entity.ReserveVenueOrder;
import com.bra.modules.reserve.service.ReserveRoleService;
import com.bra.modules.reserve.service.ReserveVenueOrderService;
import com.bra.modules.reserve.utils.AuthorityUtils;
import com.bra.modules.reserve.utils.VenueOrderUtils;
import com.bra.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.bra.common.persistence.Page;
import com.bra.common.web.BaseController;
import com.bra.common.utils.StringUtils;
import com.bra.modules.reserve.entity.ReserveVenueCons;
import com.bra.modules.reserve.service.ReserveVenueConsService;

import java.util.List;

/**
 * 场地预定主表Controller
 *
 * @author 肖斌
 * @version 2016-01-11
 */
@Controller
@RequestMapping(value = "${adminPath}/reserve/reserveVenueConsData")
public class ReserveVenueConsDataController extends BaseController {

    @Autowired
    private ReserveVenueConsService reserveVenueConsService;
    @Autowired
    private ReserveVenueOrderService reserveVenueOrderService;

    @Autowired
    private ReserveRoleService reserveRoleService;

    @ModelAttribute
    public ReserveVenueCons get(@RequestParam(required = false) String id) {
        ReserveVenueCons entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = reserveVenueConsService.get(id);
        }
        if (entity == null) {
            entity = new ReserveVenueCons();
        }
        return entity;
    }

    //订单列表
    @RequestMapping(value = "order")
    public String order(ReserveVenueCons reserveVenueCons, HttpServletRequest request, HttpServletResponse response, Model model) {
        if (reserveVenueCons == null) {
            reserveVenueCons = new ReserveVenueCons();
        }

        ReserveRole reserveRole = new ReserveRole();
        reserveRole.setUser(UserUtils.getUser());
        List<String> venueIds = reserveRoleService.findVenueIdsByRole(reserveRole);

        reserveVenueCons.getSqlMap().put("dsf", AuthorityUtils.getVenueIds(venueIds));
        List<ReserveVenueCons> datas = reserveVenueConsService.findListOrder(reserveVenueCons);

        ReserveVenueOrder venueOrder = new ReserveVenueOrder();
        venueOrder.getSqlMap().put("dsf", AuthorityUtils.getVenueIds(venueIds));
        List<ReserveVenueOrder> orderList = reserveVenueOrderService.findListOrder(venueOrder);

        model.addAttribute("datas", VenueOrderUtils.getOrderList(datas, orderList));
        return "reserve/record/fieldOrder";
    }

    //预定列表
    @RequestMapping(value = "reservationList")
    public String reservation(ReserveVenueCons reserveVenueCons, HttpServletRequest request, HttpServletResponse response, Model model) {
        if (reserveVenueCons == null) {
            reserveVenueCons = new ReserveVenueCons();
        }
        Page<ReserveVenueCons> page = reserveVenueConsService.findPage(new Page<>(request, response), reserveVenueCons);
        model.addAttribute("page", page);
        return "modules/reserve/reserveVenueConsDataReservation";
    }


}