package com.bra.modules.reserve.web;

import com.bra.common.utils.DateUtils;
import com.bra.common.web.BaseController;
import com.bra.modules.reserve.entity.ReserveVenue;
import com.bra.modules.reserve.service.ReserveUserService;
import com.bra.modules.reserve.service.ReserveVenueConsService;
import com.bra.modules.reserve.service.ReserveVenueService;
import com.bra.modules.reserve.utils.AuthorityUtils;
import com.bra.modules.reserve.utils.ExcelInfo;
import com.bra.modules.reserve.utils.VenueOrderUtils;
import com.bra.modules.reserve.web.form.SaleVenueLog;
import com.bra.modules.sys.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
    public String list(Model model, SaleVenueLog venueLog) {

        model.addAttribute("userList",reserveUserService.findList(new User()));
        ReserveVenue venue = new ReserveVenue();
        venue.getSqlMap().put("dsf",AuthorityUtils.getVenueIdSql("a.id"));
        model.addAttribute("venueList",reserveVenueService.findList(venue));
        model.addAttribute("venueLog",venueLog);//参数返回

        venueLog.setDsf(AuthorityUtils.getVenueIdSql("a.venue_id"));
        model.addAttribute("venueLogList", reserveVenueConsService.findOrderLog(venueLog));

        return "/reserve/saleField/saleVenueLog";
    }

    @RequestMapping(value = "listExport")
    public void listExport(SaleVenueLog venueLog, HttpServletResponse response)throws Exception {
        ReserveVenue venue = new ReserveVenue();
        venue.getSqlMap().put("dsf",AuthorityUtils.getVenueIdSql("a.id"));
        venueLog.setDsf(AuthorityUtils.getVenueIdSql("a.venue_id"));
        List<Map<String, Object>> sellLog = reserveVenueConsService.findOrderLog(venueLog);
        String[] titles = {"所属场馆","所属项目","订单金额","应收金额","优惠金额","实收金额","支付类型","预定人","操作人","授权人","订单时间","操作时间"};
        List<String[]> contentList = new ArrayList<>();
        for(Map<String,Object> map :sellLog){
            String[] o = new String[12];
            o[0] = String.valueOf(map.get("name"));
            o[1] = String.valueOf(map.get("projectName")+getType(String.valueOf(map.get("reserve_type"))));
            o[2] = map.get("order_price")!=null?String.valueOf( map.get("order_price")):"";
            o[3] = map.get("should_price")!=null?String.valueOf(map.get("should_price")):"";
            o[4] = map.get("discount_price")!=null?String.valueOf(map.get("discount_price")):"";
            o[5] = map.get("cons_price")!=null?String.valueOf(map.get("cons_price")):"";
            o[6] =  map.get("pay_type")!=null? VenueOrderUtils.getPayType(String.valueOf(map.get("pay_type"))):"";
            o[7] =  map.get("user_name")!=null?String.valueOf(map.get("user_name")):"";
            o[8] =  map.get("create_user")!=null?String.valueOf(map.get("create_user")):"";
            o[9] =  map.get("checkout_name")!=null?String.valueOf(map.get("checkout_name")):"";
            o[10] =  map.get("cons_date")!=null?String.valueOf(map.get("cons_date")):"";
            o[11] =  map.get("update_date")!=null?String.valueOf(map.get("update_date")):"";
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
