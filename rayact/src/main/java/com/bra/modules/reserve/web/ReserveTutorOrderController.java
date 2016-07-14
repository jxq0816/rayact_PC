package com.bra.modules.reserve.web;

import com.bra.common.config.Global;
import com.bra.common.persistence.Page;
import com.bra.common.utils.DateUtils;
import com.bra.common.utils.StringUtils;
import com.bra.common.web.BaseController;
import com.bra.common.web.annotation.Token;
import com.bra.modules.reserve.entity.ReserveProject;
import com.bra.modules.reserve.entity.ReserveTutor;
import com.bra.modules.reserve.entity.ReserveTutorOrder;
import com.bra.modules.reserve.service.ReserveProjectService;
import com.bra.modules.reserve.service.ReserveTutorOrderService;
import com.bra.modules.reserve.utils.ExcelInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 教练订单Controller
 *
 * @author 肖斌
 * @version 2016-01-19
 */
@Controller
@RequestMapping(value = "${adminPath}/reserve/reserveTutorOrder")
public class ReserveTutorOrderController extends BaseController {

    @Autowired
    private ReserveTutorOrderService reserveTutorOrderService;

    @Autowired
    private ReserveProjectService projectService;

    @ModelAttribute
    public ReserveTutorOrder get(@RequestParam(required = false) String id) {
        ReserveTutorOrder entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = reserveTutorOrderService.get(id);
        }
        if (entity == null) {
            entity = new ReserveTutorOrder();
        }
        return entity;
    }

    @RequestMapping(value = {"list", ""})
    public String list(ReserveTutorOrder reserveTutorOrder, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<ReserveTutorOrder> page = reserveTutorOrderService.findPage(new Page<ReserveTutorOrder>(request, response), reserveTutorOrder);
        model.addAttribute("page", page);
        return "modules/reserve/reserveTutorOrderList";
    }

    @RequestMapping(value = "form")
    @Token(save = true)
    public String form(ReserveTutorOrder reserveTutorOrder, Model model) {
        model.addAttribute("reserveTutorOrder", reserveTutorOrder);
        return "modules/reserve/reserveTutorOrderForm";
    }

    @RequestMapping(value = "save")
    @Token(remove = true)
    public String save(ReserveTutorOrder reserveTutorOrder, Model model, RedirectAttributes redirectAttributes) {
        if (!beanValidator(model, reserveTutorOrder)) {
            return form(reserveTutorOrder, model);
        }
        reserveTutorOrderService.save(reserveTutorOrder);
        addMessage(redirectAttributes, "保存教练订单成功");
        return "redirect:" + Global.getAdminPath() + "/reserve/reserveTutorOrder/?repage";
    }

    @RequestMapping(value = "delete")
    public String delete(ReserveTutorOrder reserveTutorOrder, RedirectAttributes redirectAttributes) {
        reserveTutorOrderService.delete(reserveTutorOrder);
        addMessage(redirectAttributes, "删除教练订单成功");
        return "redirect:" + Global.getAdminPath() + "/reserve/reserveTutorOrder/?repage";
    }

    @RequestMapping(value = "orderDetail")
    public String orderDetail(HttpServletRequest request, HttpServletResponse response, Model model) {
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        Date start = null;
        Date end = null;
        if (!"".equals(startDate) && !"".equals(endDate)) {
            start = DateUtils.parseDate(startDate);
            end = DateUtils.parseDate(endDate);
        }
        String tutorId = request.getParameter("tutorId");
        ReserveTutorOrder order = new ReserveTutorOrder();
        order.setStartDate(start);
        order.setEndDate(end);
        ReserveTutor tutor = new ReserveTutor();
        tutor.setId(tutorId);
        order.setTutor(tutor);
        Page page = new Page<ReserveTutorOrder>(request, response);
        order.setPage(page);
        List<Map<String, Object>> list = reserveTutorOrderService.getTutorDetail(order);
        page.setList(list);
        model.addAttribute("page", page);
        model.addAttribute("startDate", start);
        model.addAttribute("endDate", end);
        return "reserve/report/tutorDetailList";
    }

    @RequestMapping(value = "orderDetailExport")
    public void findSellDetailListExport(HttpServletRequest request, HttpServletResponse response){
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        Date start = null;
        Date end = null;
        if (!"".equals(startDate) && !"".equals(endDate)) {
            start = DateUtils.parseDate(startDate);
            end = DateUtils.parseDate(endDate);
        }
        String tutorId = request.getParameter("tutorId");
        String tutorName = request.getParameter("tutorName");
        ReserveTutorOrder order = new ReserveTutorOrder();
        order.setStartDate(start);
        order.setEndDate(end);
        ReserveTutor tutor = new ReserveTutor();
        tutor.setId(tutorId);
        order.setTutor(tutor);
        Page page = new Page<ReserveTutorOrder>(request, response);
        order.setPage(page);
        List<Map<String, Object>> list = reserveTutorOrderService.getTutorDetail(order);
        String[] titles = {"时期", "客户姓名", "授课时段", "授课场地", "授课时长/小时", "教练费"};
        List<String[]> contentList = new ArrayList<>();
        for (Map map : list) {
            String[] o = new String[6];
            o[0] = map.get("date").toString();
            o[1] = map.get("name").toString();
            o[2] = map.get("startTime").toString()+"-"+ map.get("endTime").toString();
            o[3] = map.get("fieldName").toString();
            o[4] = map.get("hour").toString();
            o[5] = map.get("price").toString();
            contentList.add(o);
        }
        Date now = new Date();
        ExcelInfo info = null;
        try {
            info = new ExcelInfo(response, tutorName+"教练授课明细 导出时间：" + DateUtils.formatDate(now), titles, contentList);
            info.export();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "orderAll")
    public String orderAll(ReserveTutorOrder reserveTutorOrder, HttpServletRequest request, HttpServletResponse response, Model model) {
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        request.setAttribute("startDate", startDate);
        request.setAttribute("endDate", endDate);
        Page page = new Page<ReserveTutorOrder>(request, response);
        reserveTutorOrder.setPage(page);
        List<Map<String, Object>> list = reserveTutorOrderService.getTutorOrderAll(reserveTutorOrder);
        page.setList(list);
        List<ReserveProject> reserveProjectList = projectService.findList(new ReserveProject());
        model.addAttribute("reserveProjectList", reserveProjectList);
        model.addAttribute("query", reserveTutorOrder);
        model.addAttribute("page", page);
        return "reserve/report/tutorOrderAll";
    }

    @RequestMapping(value = "orderAllExport")
    public void listOpenRateExport(ReserveTutorOrder reserveTutorOrder, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        request.setAttribute("startDate", startDate);
        request.setAttribute("endDate", endDate);
        Page page = new Page<ReserveTutorOrder>(request, response);
        reserveTutorOrder.setPage(page);
        List<Map<String, Object>> list = reserveTutorOrderService.getTutorOrderAll(reserveTutorOrder);

        String[] titles = {"教练姓名", "授课时长/小时", "教练费/元"};
        List<String[]> contentList = new ArrayList<>();
        for (Map<String, Object> map : list) {
            String[] o = new String[3];
            o[0] = map.get("tutorName") != null ? String.valueOf(map.get("tutorName")) : "";
            o[1] = map.get("time") != null ? String.valueOf(map.get("time")) : "";
            o[2] = map.get("price") != null ? String.valueOf(map.get("price")) : "";
            contentList.add(o);
        }
        Date now = new Date();
        ExcelInfo info = new ExcelInfo(response, "教练收入报表" + DateUtils.formatDate(now), titles, contentList);
        info.export();
    }

}