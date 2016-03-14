package com.bra.modules.reserve.web;

import com.bra.common.config.Global;
import com.bra.common.persistence.Page;
import com.bra.common.utils.StringUtils;
import com.bra.common.web.BaseController;
import com.bra.common.web.annotation.Token;
import com.bra.modules.reserve.entity.ReserveTimeInterval;
import com.bra.modules.reserve.service.ReserveTimeIntervalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 教练Controller
 *
 * @author jiangxingqi
 * @version 2016-01-15
 */
@Controller
@RequestMapping(value = "${adminPath}/reserve/reserveTimeInterval")
public class ReserveTimeIntervalController extends BaseController {

    @Autowired
    private ReserveTimeIntervalService reserveTimeIntervalService;

    @ModelAttribute
    public ReserveTimeInterval get(@RequestParam(required = false) String id) {
        ReserveTimeInterval entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = reserveTimeIntervalService.get(id);
        }
        if (entity == null) {
            entity = new ReserveTimeInterval();
        }
        return entity;
    }

    @RequestMapping(value = {"list", ""})
    public String list(ReserveTimeInterval reserveTimeInterval, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<ReserveTimeInterval> page = reserveTimeIntervalService.findPage(new Page<ReserveTimeInterval>(request, response), reserveTimeInterval);
        model.addAttribute("page", page);
        return "reserve/timeInterval/list";
    }

    @RequestMapping(value = "form")
    @Token(save = true)
    public String form(ReserveTimeInterval reserveTimeInterval, Model model) {
        List<ReserveTimeInterval> reserveTimeIntervalList = reserveTimeIntervalService.findList(new ReserveTimeInterval());
        model.addAttribute("reserveTimeIntervalList", reserveTimeIntervalList);
        model.addAttribute("reserveTimeInterval", reserveTimeInterval);
        return "reserve/timeInterval/form";
    }

    @RequestMapping(value = "save")
    @Token(remove = true)
    public String save(ReserveTimeInterval reserveTimeInterval, Model model, RedirectAttributes redirectAttributes) {
        if (!beanValidator(model, reserveTimeInterval)) {
            return form(reserveTimeInterval, model);
        }
        reserveTimeIntervalService.save(reserveTimeInterval);
        addMessage(redirectAttributes, "保存时令成功");
        return "redirect:" + Global.getAdminPath() + "/reserve/reserveTimeInterval/?repage";
    }

    @RequestMapping(value = "delete")
    public String delete(ReserveTimeInterval reserveTimeInterval, RedirectAttributes redirectAttributes) {
        reserveTimeIntervalService.delete(reserveTimeInterval);
        addMessage(redirectAttributes, "删除时令成功");
        return "redirect:" + Global.getAdminPath() + "/reserve/reserveTimeInterval/?repage";
    }
}