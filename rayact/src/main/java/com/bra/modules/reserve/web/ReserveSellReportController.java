package com.bra.modules.reserve.web;

import com.alibaba.fastjson.JSONArray;
import com.bra.common.utils.DateUtils;
import com.bra.common.utils.SpringContextHolder;
import com.bra.common.utils.StringUtils;
import com.bra.common.web.BaseController;
import com.bra.modules.reserve.entity.ReserveCardStatements;
import com.bra.modules.reserve.entity.ReserveCommodity;
import com.bra.modules.reserve.entity.ReserveRole;
import com.bra.modules.reserve.entity.ReserveVenue;
import com.bra.modules.reserve.entity.form.SearchForm;
import com.bra.modules.reserve.service.ReserveCardStatementsService;
import com.bra.modules.reserve.service.ReserveCommodityService;
import com.bra.modules.reserve.service.ReserveRoleService;
import com.bra.modules.reserve.service.ReserveVenueService;
import com.bra.modules.reserve.utils.AuthorityUtils;
import com.bra.modules.sys.entity.User;
import com.bra.modules.sys.service.SystemService;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Created by DDT on 2016/4/1.
 */
@Controller
@RequestMapping(value = "${adminPath}/reserve/reserveSellReport")
public class ReserveSellReportController extends BaseController {
    @Autowired
    private ReserveCardStatementsService reserveCardStatementsService;
    @Autowired
    private ReserveVenueService reserveVenueService;
    @Autowired
    private ReserveCommodityService reserveCommodityService;
    @Autowired
    private ReserveRoleService reserveRoleService;

    @RequestMapping(value = "list")
    public String list(Model model, ReserveCardStatements reserveCardStatements) {
        ReserveVenue reserveVenue = reserveCardStatements.getVenue();
        if (reserveVenue != null && reserveVenue.getId() != null && !"".equals(reserveVenue.getId().trim())) {
            if (reserveCardStatements.getSqlMap().get("dsf") == null)
                reserveCardStatements.getSqlMap().put("dsf", " and v.id = '" + reserveVenue.getId() + "'  ");
        } else {
            if (reserveCardStatements.getSqlMap().get("dsf") == null)
                reserveCardStatements.getSqlMap().put("dsf", AuthorityUtils.getDsf("v.id"));
        }
        if (reserveCardStatements.getStartDate() == null || reserveCardStatements.getEndDate() == null) {
            Calendar c = Calendar.getInstance();
            reserveCardStatements.setEndDate(c.getTime());
            Calendar c2 = Calendar.getInstance();
            c2.set(Calendar.HOUR_OF_DAY, 6);
            c2.set(Calendar.MINUTE, 0);
            reserveCardStatements.setStartDate(c2.getTime());
        }
        List<Map<String, Object>> rtn = reserveCardStatementsService.venueIncomeReport(reserveCardStatements);
        ReserveVenue v = new ReserveVenue();
        List<ReserveVenue> list = reserveVenueService.findList(v);
        model.addAttribute("rtn", rtn);
        model.addAttribute("reserveVenueList", list);
        model.addAttribute("reserveCardStatements", reserveCardStatements);
        return "reserve/report/venueIncomeReport";
    }

    @RequestMapping(value = "listChart")
    @ResponseBody
    public Map listChart(ReserveCardStatements reserveCardStatements) {
        if (reserveCardStatements.getSqlMap().get("dsf") == null)
            reserveCardStatements.getSqlMap().put("dsf", AuthorityUtils.getDsf("v.id"));
        if (reserveCardStatements.getStartDate() == null || reserveCardStatements.getEndDate() == null) {
            Calendar c = Calendar.getInstance();
            reserveCardStatements.setEndDate(c.getTime());
            Calendar c2 = Calendar.getInstance();
            c2.set(Calendar.HOUR_OF_DAY, 6);
            c2.set(Calendar.MINUTE, 0);
            reserveCardStatements.setStartDate(c2.getTime());
        }
        List<Map<String, Object>> rtn = reserveCardStatementsService.venueIncomeReport(reserveCardStatements);
        ReserveVenue v = new ReserveVenue();
        List<ReserveVenue> list = reserveVenueService.findList(v);
        Map data = new HashMap<>();
        data.put("rtn", rtn);
        data.put("reserveVenueList", list);
        return data;
    }

    /**
     * APP 高管总收入
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "mobile/incomeAll")
    public String incomeAll(HttpServletRequest request) {
        String userId = request.getParameter("userId");
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        Date start = DateUtils.parseDate(startDate);
        Date end = DateUtils.parseDate(endDate);
        ReserveCardStatements reserveCardStatements = new ReserveCardStatements();
        reserveCardStatements.setStartDate(start);
        reserveCardStatements.setEndDate(end);
        if (userId != null && !userId.equals("")) {
            User user = SpringContextHolder.getBean(SystemService.class).getUser(userId);
            reserveCardStatements.setTenantId(user.getCompany().getId());
            if (reserveCardStatements.getSqlMap().get("dsf") == null) {
                ReserveRole reserveRole = new ReserveRole();
                reserveRole.setUser(user);
                reserveRole.setTenantId(user.getCompany().getId());
                List<String> venueIds = reserveRoleService.findVenueIdsByRole(reserveRole);
                String venues = AuthorityUtils.getVenueIds(venueIds, "v.id");
                reserveCardStatements.getSqlMap().put("dsf", venues);
            }
        }
        if (reserveCardStatements.getStartDate() == null || reserveCardStatements.getEndDate() == null) {
            Calendar c = Calendar.getInstance();
            reserveCardStatements.setEndDate(c.getTime());
            Calendar c2 = Calendar.getInstance();
            c2.set(Calendar.HOUR_OF_DAY, 6);
            c2.set(Calendar.MINUTE, 0);
            reserveCardStatements.setStartDate(c2.getTime());
        }
        List<Map<String, Object>> rtn = reserveCardStatementsService.venueIncomeReport(reserveCardStatements);
        request.setAttribute("rtn", rtn);
        request.setAttribute("jsonRtn", JSONArray.toJSONString(rtn));
        //return JSONArray.toJSONString(rtn);
        return "/reserve/report/incomeAll";
    }

    @RequestMapping(value = "mobile/fieldIncome")
    public String fieldIncome(HttpServletRequest request) {
        String venueId = request.getParameter("venueId");
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        Date start = DateUtils.parseDate(startDate);
        Date end = DateUtils.parseDate(endDate);
        SearchForm form = new SearchForm();
        form.setStartDate(start);
        form.setEndDate(end);
        form.setVenueId(venueId);
        if (form.getStartDate() == null || form.getEndDate() == null) {
            Calendar c = Calendar.getInstance();
            form.setEndDate(c.getTime());
            Calendar c2 = Calendar.getInstance();
            c2.set(Calendar.HOUR_OF_DAY, 6);
            c2.set(Calendar.MINUTE, 0);
            form.setStartDate(c2.getTime());
        }
        List<Map<String, Object>> rtn = reserveCardStatementsService.fieldIncome(form);
        request.setAttribute("rtn", rtn);
        List<Map<String, Object>> ticket = reserveCardStatementsService.ticketIncome(form);
        request.setAttribute("rtnTicket", ticket);
        return "/reserve/report/fieldIncome";
        //return JSONArray.toJSONString(rtn);
    }

    @RequestMapping(value = "mobile/storeIncome")
    public String storeIncome(HttpServletRequest request) {
        String venueId = request.getParameter("venueId");
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        Date start = DateUtils.parseDate(startDate);
        Date end = DateUtils.parseDate(endDate);
        SearchForm form = new SearchForm();
        form.setStartDate(start);
        form.setEndDate(end);
        form.setVenueId(venueId);
        if (form.getStartDate() == null || form.getEndDate() == null) {
            Calendar c = Calendar.getInstance();
            form.setEndDate(c.getTime());
            Calendar c2 = Calendar.getInstance();
            c2.set(Calendar.HOUR_OF_DAY, 6);
            c2.set(Calendar.MINUTE, 0);
            form.setStartDate(c2.getTime());
        }
        List<Map<String, Object>> rtn = reserveCardStatementsService.storeIncome(form);
        request.setAttribute("rtn", rtn);
        return "/reserve/report/storeIncome";
        //return JSONArray.toJSONString(rtn);
    }

    /**
     * APP 高管 商品收入
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "mobile/commIncome")
    public String commIncome(HttpServletRequest request) {

        List<Map<String, String>> rtn = new ArrayList<>();
        String userId = request.getParameter("userId");
        User user = SpringContextHolder.getBean(SystemService.class).getUser(userId);
        String tenantId=user.getCompany().getId();
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        if(StringUtils.isBlank(startDate)){
            startDate=DateFormatUtils.format(new Date(), "yyyy-MM-dd");
        }
        if(StringUtils.isBlank(endDate)){
            endDate=DateFormatUtils.format(new Date(), "yyyy-MM-dd");
        }
        Date start = DateUtils.parseDate(startDate);
        Date end = DateUtils.parseDate(endDate);
        SearchForm form = new SearchForm();
        form.setStartDate(start);
        form.setEndDate(end);
        if (form.getStartDate() == null || form.getEndDate() == null) {
            Calendar c = Calendar.getInstance();
            form.setEndDate(c.getTime());
            Calendar c2 = Calendar.getInstance();
            c2.set(Calendar.HOUR_OF_DAY, 6);
            c2.set(Calendar.MINUTE, 0);
            form.setStartDate(c2.getTime());
        }
        //查询该用户管理的所有场馆
        ReserveVenue reserveVenue = new ReserveVenue();
        if (userId != null && !userId.equals("")) {
            reserveVenue.setTenantId(tenantId);
            //场馆权限过滤
            if (reserveVenue.getSqlMap().get("dsf") == null) {
                ReserveRole reserveRole = new ReserveRole();
                reserveRole.setUser(user);
                reserveRole.setTenantId(tenantId);
                List<String> venueIds = reserveRoleService.findVenueIdsByRole(reserveRole);
                String venues = AuthorityUtils.getVenueIds(venueIds, "a.id");
                reserveVenue.getSqlMap().put("dsf", venues);
            }
        }
        //查询该用户管理的所有场馆
        List<ReserveVenue> vs = reserveVenueService.findList(reserveVenue);
        ReserveCommodity reserveCommodity = new ReserveCommodity();
        if (userId != null && !userId.equals("")) {
            reserveCommodity.setTenantId(tenantId);
            //场馆权限过滤
            if (form.getSqlMap().get("dsf") == null) {
                ReserveRole reserveRole = new ReserveRole();
                reserveRole.setUser(user);
                List<String> venueIds = reserveRoleService.findVenueIdsByRole(reserveRole);
                String venues = AuthorityUtils.getVenueIds(venueIds, "v.id");
                form.getSqlMap().put("dsf", venues);
            }
        }
        //查询该用户管理的所有场馆 售卖的所有商品
        form.setTenantId(tenantId);
        List<ReserveCommodity> cs = reserveCardStatementsService.commSell(form);
        if (cs != null) {
            for (ReserveCommodity c : cs) {
                Map node = new HashMap<>();

                node.put("commId", c.getId());
                node.put("commName", c.getName());
                node.put("startDate",startDate);
                node.put("endDate",endDate);
                //查询该用户管理的所有场馆 售卖的某件商品的销量
                List<Map<String, Object>> tmp = reserveCardStatementsService.commIncome(node);
                node.put("data", JSONArray.toJSONString(tmp));
                rtn.add(node);
            }
        }
        ReserveCardStatements reserveCardStatements = new ReserveCardStatements();
        reserveCardStatements.setStartDate(start);
        reserveCardStatements.setEndDate(end);
        reserveCardStatements.setTenantId(tenantId);
        if (reserveCardStatements.getStartDate() == null || reserveCardStatements.getEndDate() == null) {
            Calendar c = Calendar.getInstance();
            reserveCardStatements.setEndDate(c.getTime());
            Calendar c2 = Calendar.getInstance();
            c2.set(Calendar.HOUR_OF_DAY, 6);
            c2.set(Calendar.MINUTE, 0);
            reserveCardStatements.setStartDate(c2.getTime());
        }
        //查询该用户管理的所有场馆 售卖的所有商品 合计
        List<Map<String, Object>> all = reserveCardStatementsService.venueIncomeReport(reserveCardStatements);
        request.setAttribute("all", JSONArray.toJSONString(all));
        request.setAttribute("vs", vs);
        request.setAttribute("rtn", rtn);
        return "/reserve/report/commIncome";
        //return JSONArray.toJSONString(rtn);
    }

    @RequestMapping(value = "mobile/commAll")
    public String commAll(HttpServletRequest request) {
        List<Map<String, String>> rtn = new ArrayList<>();
        String userId = request.getParameter("userId");
        ReserveCommodity reserveCommodity = new ReserveCommodity();
        if (userId != null && !userId.equals("")) {
            User user = SpringContextHolder.getBean(SystemService.class).getUser(userId);
            reserveCommodity.setTenantId(user.getCompany().getId());
        }
        reserveCommodity.getSqlMap().put("dsf", " and 1=1 ");
        List<ReserveCommodity> vs = reserveCommodityService.findList(reserveCommodity);
        if (vs != null) {
            for (ReserveCommodity c : vs) {
                Map<String, String> node = new HashMap<>();
                node.put("commodityId", c.getId());
                node.put("commodityName", c.getName());
                rtn.add(node);
            }
        }
        request.setAttribute("rtn", rtn);
        //return JSONArray.toJSONString(rtn);
        return "reserve/report/commIncome";
    }


}
