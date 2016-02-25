package com.bra.modules.reserve.web;

import com.bra.common.config.Global;
import com.bra.common.persistence.Page;
import com.bra.common.utils.StringUtils;
import com.bra.common.web.BaseController;
import com.bra.common.web.annotation.Token;
import com.bra.modules.mechanism.web.bean.AttMainForm;
import com.bra.modules.reserve.entity.ReserveVenue;
import com.bra.modules.reserve.entity.ReserveVenueConsItem;
import com.bra.modules.reserve.entity.form.ReserveVenueProjectDayReport;
import com.bra.modules.reserve.entity.form.ReserveVenueProjectIntervalReport;
import com.bra.modules.reserve.entity.form.ReserveVenueTotalIntervalReport;
import com.bra.modules.reserve.service.ReserveVenueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 场馆管理Controller
 *
 * @author 肖斌
 * @version 2015-12-29
 */
@Controller
@RequestMapping(value = "${adminPath}/reserve/reserveVenue")
public class ReserveVenueController extends BaseController {

    @Autowired
    private ReserveVenueService reserveVenueService;

    @ModelAttribute
    public ReserveVenue get(@RequestParam(required = false) String id) {
        ReserveVenue entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = reserveVenueService.get(id);
        }
        if (entity == null) {
            entity = new ReserveVenue();
        }
        return entity;
    }

    @RequestMapping(value = {"list", ""})
    public String list(ReserveVenue reserveVenue, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<ReserveVenue> page = reserveVenueService.findPage(new Page<>(request, response), reserveVenue);
        model.addAttribute("page", page);
        return "reserve/venue/list";
    }

    @RequestMapping(value = "form")
    @Token(save = true)
    public String form(ReserveVenue reserveVenue, Model model) {
        model.addAttribute("reserveVenue", reserveVenue);
        return "reserve/venue/form";
    }

    @RequestMapping(value = "save")
    @Token(remove = true)
    public String save(ReserveVenue reserveVenue, AttMainForm attMainForm, Model model, RedirectAttributes redirectAttributes) {
        if (!beanValidator(model, reserveVenue)) {
            return form(reserveVenue, model);
        }
        reserveVenueService.save(reserveVenue, attMainForm);
        addMessage(redirectAttributes, "保存场馆成功");
        return "redirect:" + Global.getAdminPath() + "/reserve/reserveVenue/?repage";
    }

    @RequestMapping(value = "delete")
    public String delete(ReserveVenue reserveVenue,RedirectAttributes redirectAttributes) {
        reserveVenueService.delete(reserveVenue);
        addMessage(redirectAttributes, "删除场馆成功");
        return "redirect:" + Global.getAdminPath() + "/reserve/reserveVenue/?repage";
    }

    @RequestMapping(value = "totalIncomeReport")
    public String totalIncomeReport(ReserveVenueTotalIntervalReport intervalTotalReport, Model model) {
        Date startDate=intervalTotalReport.getStartDate();
        Date endDate=intervalTotalReport.getEndDate();
        if(startDate==null){
            startDate=new Date();//默认当天
            intervalTotalReport.setStartDate(startDate);
        }
        if(endDate==null){
            endDate=new Date();//默认当天
            intervalTotalReport.setEndDate(endDate);
        }
        intervalTotalReport.setReserveVenue(reserveVenueService.get(intervalTotalReport.getReserveVenue()));
        List<ReserveVenue> reserveVenueList=reserveVenueService.findList(new ReserveVenue());//场馆列表

        intervalTotalReport=reserveVenueService.totalIncomeReport(intervalTotalReport);
        model.addAttribute("reserveVenueList",reserveVenueList);//场馆列表

        model.addAttribute("intervalTotalReport",intervalTotalReport);//请求参数&返回结果
        return "reserve/report/venueIncomeTotalReport";
    }

    @RequestMapping(value = "report")
    public String report(ReserveVenueProjectIntervalReport intervalReport, Model model) {
        Date startDate=intervalReport.getStartDate();//月份
        Date endDate=intervalReport.getEndDate();//月份
        if(startDate==null){
            startDate=new Date();//默认当天
            intervalReport.setStartDate(startDate);
        }
        if(endDate==null){
            endDate=new Date();//默认当天
            intervalReport.setEndDate(endDate);
        }
        ReserveVenue venue=intervalReport.getReserveVenue();//场馆
        if(venue==null){
            venue=new ReserveVenue();
        }else if(StringUtils.isNoneEmpty(venue.getId())){
            venue=reserveVenueService.get(venue);
        }
        intervalReport.setReserveVenue(venue);

        List<ReserveVenue> reserveVenueList=reserveVenueService.findList(new ReserveVenue());//场馆列表

        List<ReserveVenueProjectIntervalReport> intervalReports=reserveVenueService.intervalReports(intervalReport);//区间报表

        ReserveVenueProjectDayReport totalReport=new ReserveVenueProjectDayReport();//总报表
        Double storedCardSum=0.0;
        Double cashSum=0.0;
        Double bankCardSum=0.0;
        Double weiXinSum=0.0;
        Double aliPaySum=0.0;
        Double dueSum=0.0;
        Double otherSum=0.0;
        Double billSum=0.0;
        for(ReserveVenueProjectIntervalReport report:intervalReports){
            storedCardSum+=report.getFieldBillStoredCard();
            cashSum+=report.getFieldBillCash();
            bankCardSum+=report.getFieldBillBankCard();
            weiXinSum+=report.getFieldBillWeiXin();
            aliPaySum+=report.getFieldBillAliPay();
            dueSum+=report.getFieldBillDue();//欠账
            otherSum+=report.getFieldBillOther();
            billSum+=report.getBill();
        }
        totalReport.setFieldBillStoredCard(storedCardSum);
        totalReport.setFieldBillCash(cashSum);
        totalReport.setFieldBillBankCard(bankCardSum);
        totalReport.setFieldBillWeiXin(weiXinSum);
        totalReport.setFieldBillAliPay(aliPaySum);
        totalReport.setFieldBillDue(dueSum);
        totalReport.setFieldBillOther(otherSum);
        totalReport.setBill(billSum);

        model.addAttribute("venue",venue);//场馆
        model.addAttribute("reserveVenueList",reserveVenueList);//场馆列表
        model.addAttribute("intervalReports",intervalReports);//区间报表
        model.addAttribute("totalReport",totalReport);//月总报表
        model.addAttribute("intervalReport",intervalReport);//请求参数
        return "reserve/report/venueIncomeReport";
    }

}