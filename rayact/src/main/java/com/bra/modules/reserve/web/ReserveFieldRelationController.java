package com.bra.modules.reserve.web;

import com.bra.common.utils.StringUtils;
import com.bra.common.web.BaseController;
import com.bra.modules.reserve.entity.ReserveFieldRelation;
import com.bra.modules.reserve.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 场地管理Controller
 *
 * @author jiang
 * @version 2015-12-29
 */
@Controller
@RequestMapping(value = "${adminPath}/reserve/reserveFieldRelation")
public class ReserveFieldRelationController extends BaseController {

    @Autowired
    private ReserveFieldRelationService reserveFieldRalationService;

    @ModelAttribute
    public ReserveFieldRelation get(@RequestParam(required = false) String id) {
        ReserveFieldRelation entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = reserveFieldRalationService.get(id);
        }
        if (entity == null) {
            entity = new ReserveFieldRelation();
        }
        return entity;
    }

    @RequestMapping(value = {"list", ""})
    public String list(ReserveFieldRelation reserveFieldRelation, HttpServletRequest request, HttpServletResponse response, Model model) {
        List<ReserveFieldRelation> reserveFieldRelationList = reserveFieldRalationService.findList(reserveFieldRelation);
        model.addAttribute("reserveFieldRelationList", reserveFieldRelationList);
        return "reserve/fieldRelation/list";
    }
}