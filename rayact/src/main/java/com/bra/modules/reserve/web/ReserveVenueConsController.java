package com.bra.modules.reserve.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bra.common.web.annotation.Token;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bra.common.config.Global;
import com.bra.common.persistence.Page;
import com.bra.common.web.BaseController;
import com.bra.common.utils.StringUtils;
import com.bra.modules.reserve.entity.ReserveVenueConsItem;
import com.bra.modules.reserve.service.ReserveVenueConsItemService;;

/**
 * 11Controller
 *
 * @author 11
 * @version 2016-01-06
 */
@Controller
@RequestMapping(value = "${adminPath}/reserve/reserveVenueCons")
public class ReserveVenueConsController extends BaseController {

    @Autowired
    private ReserveVenueConsItemService reserveVenueConsItemService;

    @ModelAttribute
    public ReserveVenueConsItem get(@RequestParam(required = false) String id) {
        ReserveVenueConsItem entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = reserveVenueConsItemService.get(id);
        }
        if (entity == null) {
            entity = new ReserveVenueConsItem();
        }
        return entity;
    }

    @RequiresPermissions("reserve:reserveVenueCons:view")
    @RequestMapping(value = {"list", ""})
    public String list(ReserveVenueConsItem reserveVenueCons, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<ReserveVenueConsItem> page = reserveVenueConsItemService.findPage(new Page<ReserveVenueConsItem>(request, response), reserveVenueCons);
        model.addAttribute("page", page);
        return "modules/reserve/reserveVenueConsList";
    }


    @RequiresPermissions("reserve:reserveVenueCons:view")
    @RequestMapping(value = "form")
    @Token(save = true)
    public String form(ReserveVenueConsItem reserveVenueCons, Model model) {
        model.addAttribute("reserveVenueCons", reserveVenueCons);
        return "modules/reserve/reserveVenueConsForm";
    }

    @RequiresPermissions("reserve:reserveVenueCons:edit")
    @RequestMapping(value = "save")
    @Token(remove = true)
    public String save(ReserveVenueConsItem reserveVenueCons, Model model, RedirectAttributes redirectAttributes) {
        if (!beanValidator(model, reserveVenueCons)) {
            return form(reserveVenueCons, model);
        }
        reserveVenueConsItemService.save(reserveVenueCons);
        addMessage(redirectAttributes, "保存场地预订信息成功");
        return "redirect:" + Global.getAdminPath() + "/reserve/reserveVenueCons/?repage";
    }

    @RequiresPermissions("reserve:reserveVenueCons:edit")
    @RequestMapping(value = "delete")
    public String delete(ReserveVenueConsItem reserveVenueCons, RedirectAttributes redirectAttributes) {
        reserveVenueConsItemService.delete(reserveVenueCons);
        addMessage(redirectAttributes, "删除场地预订记录成功");
        return "redirect:" + Global.getAdminPath() + "/reserve/reserveVenueCons/?repage";
    }

}