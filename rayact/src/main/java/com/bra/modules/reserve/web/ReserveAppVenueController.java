package com.bra.modules.reserve.web;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.bra.common.web.BaseController;
import com.bra.modules.reserve.entity.ReserveVenue;
import com.bra.modules.reserve.service.ReserveVenueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * 场馆管理Controller
 *
 * @author jiangxingqi
 * @version 2016-5-12
 */
@Controller
@RequestMapping(value = "${adminPath}/app/reserve/reserveVenue")
public class ReserveAppVenueController extends BaseController {
    @Autowired
    private ReserveVenueService reserveVenueService;

    @RequestMapping(value = {"list", ""})
    @ResponseBody
    public String list(ReserveVenue reserveVenue) {
        List<Map> list = reserveVenueService.findListForApp( reserveVenue);
        String json=JSONArray.toJSONString(list, SerializerFeature.WriteMapNullValue);
        return json;
    }
    @RequestMapping(value = {"detail", ""})
    @ResponseBody
    public String get(String venueId) {
        ReserveVenue reserveVenue=new ReserveVenue(venueId);
        Map venue = reserveVenueService.getForApp( reserveVenue);
        String json=JSONArray.toJSONString(venue,SerializerFeature.WriteMapNullValue);
        return json;
    }
    @RequestMapping(value = {"imgList", ""})
    @ResponseBody
    public String imgList(String venueId) {
        ReserveVenue reserveVenue=new ReserveVenue(venueId);
        List<Map> list = reserveVenueService.findImgList( reserveVenue);
        String json=JSONArray.toJSONString(list,SerializerFeature.WriteMapNullValue);
        return json;
    }
}