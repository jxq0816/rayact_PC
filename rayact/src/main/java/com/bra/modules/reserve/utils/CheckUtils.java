package com.bra.modules.reserve.utils;

import com.bra.common.utils.DateUtils;
import com.bra.common.utils.SpringContextHolder;
import com.bra.modules.reserve.entity.ReserveCheckDetail;
import com.bra.modules.reserve.entity.ReserveVenue;
import com.bra.modules.reserve.service.ReserveCheckDetailService;
import com.bra.modules.reserve.service.ReserveVenueService;
import jodd.util.StringUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * Created by DDT on 2016/4/22.
 */
public class CheckUtils {
    public static void saveCheckRecord(HttpServletRequest request,String itemName){
        String venueId = request.getParameter("reserveVenue.id");
        String startDate = request.getParameter("startDate");
        Date start = DateUtils.parseDate(startDate);
        String endDate = request.getParameter("endDate");
        Date end = DateUtils.parseDate(endDate);
        StringBuffer url = request.getRequestURL();
        ReserveCheckDetail check = new ReserveCheckDetail();
        ReserveVenueService venueService = SpringContextHolder.getBean("reserveVenueService");
        ReserveVenue v = venueService.get(venueId);
        check.setVenue(v);
        check.setEndDate(end);
        check.setStartDate(start);
        check.setUrl(url.toString());
        check.setItemName(itemName);
        ReserveCheckDetailService checkService = SpringContextHolder.getBean("reserveCheckDetailService");
        checkService.save(check);
    }

    public static String hasCheckRecord(HttpServletRequest request,String itemName){
        if (!"6".equals(AuthorityUtils.getUserType())){
            return "2";//不是出纳不展示按钮和水印
        }
        String venueId = request.getParameter("reserveVenue.id");
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        if(!StringUtil.isBlank(venueId)&&!StringUtil.isBlank(startDate)&&!StringUtil.isBlank(endDate)){
            Date start = DateUtils.parseDate(startDate);
            Date end = DateUtils.parseDate(endDate);
            StringBuffer url = request.getRequestURL();
            ReserveCheckDetail check = new ReserveCheckDetail();
            ReserveVenueService venueService = SpringContextHolder.getBean("reserveVenueService");
            ReserveVenue v = venueService.get(venueId);
            check.setVenue(v);
            check.setEndDate(end);
            check.setStartDate(start);
            check.setUrl(url.toString());
            check.setItemName(itemName);
            ReserveCheckDetailService checkService = SpringContextHolder.getBean("reserveCheckDetailService");
            List<ReserveCheckDetail> list = checkService.findList(check);
            if(list!=null&&list.size()>0){
                return "3";//条件全，已审
            }else{
                return "1";//条件全，未审
            }
        }else{
            return "2";//条件不全
        }
    }
}
