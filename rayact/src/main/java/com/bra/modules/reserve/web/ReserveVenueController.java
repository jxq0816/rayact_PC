package com.bra.modules.reserve.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bra.common.web.annotation.Token;
import com.bra.modules.mechanism.web.bean.AttMainForm;
import com.bra.modules.reserve.entity.ReserveVenueConsItem;
import com.bra.modules.reserve.entity.form.ReserveVenueReport;
import com.bra.modules.reserve.service.ReserveVenueService;
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
import com.bra.modules.reserve.entity.ReserveVenue;

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

    @RequestMapping(value = "report")
    public String report(ReserveVenueConsItem venueConsItem, Model model) {
        Date month=venueConsItem.getConsDate();//月份
        ReserveVenue rv=venueConsItem.getReserveVenue();//场馆
        if(rv==null){
            rv=new ReserveVenue();
        }
        List<ReserveVenue> reserveVenueList=reserveVenueService.findList(rv);//场馆列表

        List<ReserveVenueReport> reserveVenueReportList=reserveVenueService.report(rv,month);//日保表

        ReserveVenueReport monthReport=new ReserveVenueReport();//月报表
        Double storedCardSum=0.0;
        Double cashSum=0.0;
        Double bankCardSum=0.0;
        Double weixinSum=0.0;
        Double aliPaySum=0.0;
        Double dueSum=0.0;
        Double otherSum=0.0;
        for(ReserveVenueReport report:reserveVenueReportList){
            storedCardSum+=report.getFieldBillStoredCard();
            cashSum+=report.getFieldBillCash();
            bankCardSum+=report.getFieldBillBankCard();
            weixinSum+=report.getFieldBillWeiXin();
            aliPaySum+=report.getFieldBillAliPay();
            //dueSum+=report.getFieldBillDue();//欠账
            otherSum+=report.getFieldBillOther();
        }
        monthReport.setFieldBillStoredCard(storedCardSum);
        monthReport.setFieldBillCash(cashSum);
        monthReport.setFieldBillBankCard(bankCardSum);
        monthReport.setFieldBillWeiXin(weixinSum);
        monthReport.setFieldBillAliPay(aliPaySum);
        monthReport.setFieldBillDue(dueSum);
        monthReport.setFieldBillOther(otherSum);

        model.addAttribute("reserveVenueList",reserveVenueList);//场馆列表
        model.addAttribute("reserveVenueReportList",reserveVenueReportList);//日报表
        model.addAttribute("monthReport",monthReport);//月报表
        return "reserve/venue/report";
    }

}