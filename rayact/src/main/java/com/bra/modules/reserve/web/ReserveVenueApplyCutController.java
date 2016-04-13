package com.bra.modules.reserve.web;

import com.bra.common.config.Global;
import com.bra.common.persistence.Page;
import com.bra.common.utils.StringUtils;
import com.bra.common.web.BaseController;
import com.bra.common.web.annotation.Token;
import com.bra.modules.reserve.entity.ReserveVenueApplyCut;
import com.bra.modules.reserve.service.ReserveVenueApplyCutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 场馆管理Controller
 *
 * @author 肖斌
 * @version 2015-12-29
 */
@Controller
@RequestMapping(value = "${adminPath}/reserve/reserveVenueApplyCut")
public class ReserveVenueApplyCutController extends BaseController {

    @Autowired
    private ReserveVenueApplyCutService reserveVenueApplyCutService;

    @ModelAttribute
    public ReserveVenueApplyCut get(@RequestParam(required = false) String id) {
        ReserveVenueApplyCut entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = reserveVenueApplyCutService.get(id);
        }
        if (entity == null) {
            entity = new ReserveVenueApplyCut();
        }
        return entity;
    }

    @RequestMapping(value = {"list", ""})
    public String list(ReserveVenueApplyCut reserveVenueApplyCut, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<ReserveVenueApplyCut> page = reserveVenueApplyCutService.findPage(new Page<>(request, response), reserveVenueApplyCut);
        model.addAttribute("page", page);
        return "reserve/venue/list";
    }

    @RequestMapping(value = "form")
    @Token(save = true)
    public String form(ReserveVenueApplyCut reserveVenueApplyCut, Model model) {
        model.addAttribute("reserveVenueApplyCut", reserveVenueApplyCut);
        return "reserve/venue/ApplyCutform";
    }

    @RequestMapping(value = "save")
    @Token(remove = true)
    public String save(ReserveVenueApplyCut reserveVenueApplyCut, Model model, RedirectAttributes redirectAttributes) {
        if (!beanValidator(model, reserveVenueApplyCut)) {
            return form(reserveVenueApplyCut, model);
        }
        reserveVenueApplyCutService.save(reserveVenueApplyCut);
        addMessage(redirectAttributes, "保存场馆成功");
        return "redirect:" + Global.getAdminPath() + "/reserve/reserveVenueApplyCut/";
    }

}