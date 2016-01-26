package com.bra.modules.reserve.web;

import com.bra.common.utils.StringUtils;
import com.bra.common.web.ViewResult;
import com.bra.common.web.annotation.Token;
import com.bra.modules.reserve.entity.*;
import com.bra.modules.reserve.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.bra.common.config.Global;
import com.bra.common.web.BaseController;

import java.util.List;

/**
 * 场地订单Controller
 *
 * @author 肖斌
 * @version 2016-01-19
 */
@Controller
@RequestMapping(value = "${adminPath}/reserve/reserveVenueOrder")
public class ReserveVenueOrderController extends BaseController {

    @Autowired
    private ReserveVenueOrderService reserveVenueOrderService;
    @Autowired
    private ReserveVenueVisitorsSetService reserveVenueVisitorsSetService;
    @Autowired
    private ReserveTutorService reserveTutorService;
    @Autowired
    private ReserveMemberService reserveMemberService;
    @Autowired
    private ReserveProjectService reserveProjectService;

    @RequestMapping(value = "list")
    public String list(ReserveVenueVisitorsSet visitorsSet, Model model) {
        List<ReserveVenueVisitorsSet> visitorsSets = reserveVenueVisitorsSetService.findList(visitorsSet);
        model.addAttribute("visitorsSets", visitorsSets);

        List<ReserveProject> projects = reserveProjectService.findList(new ReserveProject());
        if (visitorsSet.getProject() != null && StringUtils.isNotBlank(visitorsSet.getProject().getId())) {
            model.addAttribute("projectId", visitorsSet.getProject().getId());
        }
        model.addAttribute("projects", projects);
        return "reserve/visitorsSetOrder/list";
    }

    @RequestMapping(value = "form")
    @Token(save = true)
    public String form(String vsId, Model model) {
        model.addAttribute("visitorsSet", reserveVenueVisitorsSetService.get(vsId));
        //教练
        model.addAttribute("tutors", reserveTutorService.findList(new ReserveTutor()));
        //会员
        model.addAttribute("memberList", reserveMemberService.findList(new ReserveMember()));
        return "reserve/visitorsSetOrder/form";
    }

    @RequestMapping(value = "save")
    @ResponseBody
    @Token(remove = true)
    public ViewResult save(ReserveVenueOrder reserveVenueOrder, Model model, RedirectAttributes redirectAttributes) {
        if (!beanValidator(model, reserveVenueOrder)) {
            return new ViewResult(false, model.asMap().get("message").toString());
        }
        reserveVenueOrderService.save(reserveVenueOrder);
        return new ViewResult(true, "保存成功");
    }

    @RequestMapping(value = "delete")
    public String delete(ReserveVenueOrder reserveVenueOrder, RedirectAttributes redirectAttributes) {
        reserveVenueOrderService.delete(reserveVenueOrder);
        addMessage(redirectAttributes, "删除场地订单成功");
        return "redirect:" + Global.getAdminPath() + "/reserve/reserveVenueOrder/?repage";
    }

}