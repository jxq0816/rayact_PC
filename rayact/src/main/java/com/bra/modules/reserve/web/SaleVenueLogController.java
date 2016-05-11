package com.bra.modules.reserve.web;

import com.bra.common.persistence.Page;
import com.bra.common.utils.DateUtils;
import com.bra.common.web.BaseController;
import com.bra.modules.reserve.entity.ReserveVenue;
import com.bra.modules.reserve.service.ReserveUserService;
import com.bra.modules.reserve.service.ReserveVenueConsService;
import com.bra.modules.reserve.service.ReserveVenueService;
import com.bra.modules.reserve.utils.ExcelInfo;
import com.bra.modules.reserve.web.form.SaleVenueLog;
import com.bra.modules.sys.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

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

    @RequestMapping(value = "list")
    public String list(Model model, SaleVenueLog venueLog, HttpServletRequest request, HttpServletResponse response) {

        model.addAttribute("userList",reserveUserService.findList(new User()));
        ReserveVenue venue = new ReserveVenue();
        model.addAttribute("venueList",reserveVenueService.findList(venue));
        model.addAttribute("venueLog",venueLog);//参数返回
        Page<SaleVenueLog> page = reserveVenueConsService.findOrderLog(new Page<SaleVenueLog>(request, response), venueLog);
        model.addAttribute("page", page);
        return "/reserve/saleField/saleVenueLog";
    }

    @RequestMapping(value = "listExport")
    public void listExport(SaleVenueLog venueLog,  HttpServletRequest request,HttpServletResponse response)throws Exception {

        List<SaleVenueLog> sellLogs = reserveVenueConsService.findOrderLogList(venueLog);
        String[] titles = {"订单编号","所属场馆","所属项目","时间区间","订单金额","应收金额","优惠金额","实收金额","支付类型","预定人","操作人","授权人","订单时间","操作时间"};
        List<String[]> contentList = new ArrayList<>();
        for(SaleVenueLog log :sellLogs){
            String[] o = new String[14];
            o[0] = log.getId();
            o[1] = log.getVenue().getName();
            o[2] = log.getProjectName();
            o[3] = log.getStartTime()+"-"+log.getEndTime();
            o[4] = String.valueOf(log.getOrderPrice());
            o[5] = String.valueOf(log.getShouldPrice());
            o[6] = String.valueOf(log.getDiscountPrice());
            o[7] = String.valueOf(log.getConsPrice());
            o[8] = log.getPayType();
            o[9] = log.getMember().getName();
            o[10] = log.getCreateBy().getName();
            o[11] = log.getCheckoutName();
            SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
            o[12] = String.valueOf(format.format(log.getConsDate()));
            SimpleDateFormat myFmt=new SimpleDateFormat("yyyy-MM-dd HH:mm");
            o[13] =String.valueOf(myFmt.format(log.getUpdateDate()));
            contentList.add(o);
        }
        Date now = new Date();
        ExcelInfo info = new ExcelInfo(response,"场地售卖报表"+ DateUtils.formatDate(now),titles,contentList);
        info.export();
    }
    private String getType(String typeCode){
        if("1".equals(typeCode)){
            return "(场次)";
        }else if("2".equals(typeCode)){
            return "(人次)";
        }else{
            return "";
        }
    }

}
