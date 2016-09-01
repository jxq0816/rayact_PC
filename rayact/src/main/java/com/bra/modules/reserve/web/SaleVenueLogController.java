package com.bra.modules.reserve.web;

import com.bra.common.config.Global;
import com.bra.common.persistence.Page;
import com.bra.common.utils.DateUtils;
import com.bra.common.web.BaseController;
import com.bra.modules.reserve.entity.ReserveProject;
import com.bra.modules.reserve.entity.ReserveVenue;
import com.bra.modules.reserve.entity.ReserveVenueCons;
import com.bra.modules.reserve.entity.ReserveVenueOrder;
import com.bra.modules.reserve.service.*;
import com.bra.modules.reserve.utils.ExcelInfo;
import com.bra.modules.reserve.web.form.SaleVenueLog;
import com.bra.modules.sys.entity.User;
import com.bra.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 场地售卖日志表
 * Created by xiaobin on 16/1/28.
 */
@Controller
@RequestMapping(value = "${adminPath}/reserve/saleVenue")
public class SaleVenueLogController extends BaseController {

    @Autowired
    private ReserveVenueConsService reserveVenueConsService;
    @Autowired
    private ReserveUserService reserveUserService;
    @Autowired
    private ReserveVenueService reserveVenueService;
    @Autowired
    private ReserveProjectService reserveProjectService;
    @Autowired
    private ReserveVenueOrderService reserveVenueOrderService;

    @RequestMapping(value = "list")
    public String list(Model model, SaleVenueLog venueLog, HttpServletRequest request, HttpServletResponse response) {
        model.addAttribute("userList",reserveUserService.findList(new User()));
        ReserveVenue venue = new ReserveVenue();
        model.addAttribute("venueList",reserveVenueService.findList(venue));
        List<ReserveProject> projectList = reserveProjectService.findList(new ReserveProject());
        model.addAttribute("projectList",projectList);
        model.addAttribute("query",venueLog);//参数返回
        Page<SaleVenueLog> page = reserveVenueConsService.findOrderLog(new Page<>(request, response), venueLog);
        model.addAttribute("page", page);
        String userType=UserUtils.getUser().getUserType();
        model.addAttribute("userType", userType);
        return "/reserve/saleField/saleVenueLog";
    }

    @RequestMapping(value = "listExport")
    public void listExport(SaleVenueLog venueLog,  HttpServletRequest request,HttpServletResponse response)throws Exception {

        List<SaleVenueLog> sellLogs = reserveVenueConsService.findOrderLogList(venueLog);
        String[] titles = {"所属场馆","所属项目","时间区间","订单金额","应收金额","优惠金额","实收金额","支付类型","预定人","操作人","授权人","教练","订单时间","操作时间"};
        List<String[]> contentList = new ArrayList<>();
        for(SaleVenueLog log :sellLogs){
            String[] o = new String[14];
            o[0] = log.getVenue().getName();
            o[1] = log.getProject().getName();
            o[2] = log.getStartTime()+"-"+log.getEndTime();
            o[3] = String.valueOf(log.getOrderPrice());
            o[4] = String.valueOf(log.getShouldPrice());
            o[5] = String.valueOf(log.getDiscountPrice());
            o[6] = String.valueOf(log.getConsPrice());
            o[7] = log.getPayType();
            o[8] = log.getMember().getName();
            if(log.getCreateBy()!=null){
                o[9] = log.getCreateBy().getName();
            }else{
                o[9] = "";
            }
            o[10] = log.getCheckoutName();
            o[11] = log.getTutorName();
            if(log.getConsDate()!=null){
                SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
                o[12] = String.valueOf(format.format(log.getConsDate()));
            }else{
                o[12] = "";
            }

            SimpleDateFormat myFmt=new SimpleDateFormat("yyyy-MM-dd HH:mm");
            o[13] =String.valueOf(myFmt.format(log.getUpdateDate()));
            contentList.add(o);
        }
        Date now = new Date();
        ExcelInfo info = new ExcelInfo(response,"场地售卖报表"+ DateUtils.formatDate(now),titles,contentList);
        info.export();
    }
    @RequestMapping(value = "delete")
    private String delete(String orderId,String isTicket,RedirectAttributes redirectAttributes){
        if("1".equals(isTicket)){
            ReserveVenueOrder order =reserveVenueOrderService.get(orderId);
            reserveVenueOrderService.delete(order);
        }else{
            ReserveVenueCons order=reserveVenueConsService.get(orderId);
            reserveVenueConsService.delete(order);
        }
        addMessage(redirectAttributes, "删除记录成功");
        return "redirect:" + Global.getAdminPath() + "/reserve/saleVenue/list";
    }
}
