package com.bra.modules.reserve.web;

import com.bra.common.config.Global;
import com.bra.common.persistence.Page;
import com.bra.common.utils.StringUtils;
import com.bra.common.web.BaseController;
import com.bra.common.web.annotation.Token;
import com.bra.modules.reserve.entity.ReserveProject;
import com.bra.modules.reserve.entity.ReserveTutor;
import com.bra.modules.reserve.service.ReserveProjectService;
import com.bra.modules.reserve.service.ReserveTutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
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
@RequestMapping(value = "${adminPath}/reserve/reserveTutor")
public class ReserveTutorController extends BaseController {

    @Autowired
    private ReserveTutorService reserveTutorService;

    @Autowired
    private ReserveProjectService projectService;

    @ModelAttribute
    public ReserveTutor get(@RequestParam(required = false) String id) {
        ReserveTutor entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = reserveTutorService.get(id);
        }
        if (entity == null) {
            entity = new ReserveTutor();
        }
        return entity;
    }

    @RequestMapping(value = {"list", ""})
    public String list(ReserveTutor reserveTutor, HttpServletRequest request, HttpServletResponse response, Model model) {
        List<ReserveProject> reserveProjectList=projectService.findList(new ReserveProject());
        Page<ReserveTutor> page = reserveTutorService.findPage(new Page<ReserveTutor>(request, response), reserveTutor);
        model.addAttribute("reserveProjectList", reserveProjectList);
        model.addAttribute("query", reserveTutor);
        model.addAttribute("page", page);
        return "reserve/commodity/reserveTutorList";
    }

    @RequestMapping(value = "form")
    @Token(save = true)
    public String form(ReserveTutor reserveTutor, Model model) {
        List<ReserveProject> reserveProjectList = projectService.findList(new ReserveProject());
        model.addAttribute("reserveProjectList", reserveProjectList);
        model.addAttribute("reserveTutor", reserveTutor);
        return "reserve/commodity/reserveTutorForm";
    }

    @RequestMapping(value = "save")
    @Token(remove = true)
    public String save(ReserveTutor reserveTutor, Model model, RedirectAttributes redirectAttributes) {
        if (!beanValidator(model, reserveTutor)) {
            return form(reserveTutor, model);
        }
        reserveTutorService.save(reserveTutor);
        addMessage(redirectAttributes, "保存教练成功");
        return "redirect:" + Global.getAdminPath() + "/reserve/reserveTutor/?repage";
    }

    @RequestMapping(value = "delete")
    public String delete(ReserveTutor reserveTutor, RedirectAttributes redirectAttributes) {
        reserveTutorService.delete(reserveTutor);
        addMessage(redirectAttributes, "删除教练成功");
        return "redirect:" + Global.getAdminPath() + "/reserve/reserveTutor/?repage";
    }

    @RequestMapping("getTutorById")
    @ResponseBody
    public ReserveTutor getTutorById(String tutorId) {
        return reserveTutorService.get(tutorId);
    }

}