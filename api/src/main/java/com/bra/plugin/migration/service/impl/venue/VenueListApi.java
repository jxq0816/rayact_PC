package com.bra.plugin.migration.service.impl.venue;

import com.bra.common.config.Global;
import com.bra.common.persistence.Page;
import com.bra.common.utils.JsonUtils;
import com.bra.common.utils.MyBeanUtils;
import com.bra.common.utils.SpringContextHolder;
import com.bra.modules.reserve.entity.ReserveVenue;
import com.bra.modules.reserve.entity.json.VenueMoreService;
import com.bra.modules.reserve.service.VenueService;
import com.bra.modules.reserve.utils.GpsUtils;
import com.bra.plugin.migration.Utils;
import com.bra.plugin.migration.entity.MobileHead;
import com.bra.plugin.migration.service.TransmitsService;
import com.google.common.collect.Lists;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.RandomUtils;

import java.util.List;
import java.util.Map;

/**
 * 场馆列表
 * Created by dell on 2016/2/23.
 */
public class VenueListApi implements TransmitsService {

    private VenueService getVenueService(){
        return SpringContextHolder.getBean(VenueService.class);
    }

    @Override
    public String executeTodo(MobileHead mobileHead, Map<String, Object> request) {
        Map<String,Object> json = Utils.headMap(mobileHead);
        String pageNo = MapUtils.getString(request,"pageNo");
        String lat = MapUtils.getString(request,"lat");//经度
        String lng = MapUtils.getString(request,"lng");//维度
        ReserveVenue search = new ReserveVenue();
        Page<ReserveVenue> page = getVenueService().findPage(pageNo,search);
        List<ReserveVenue> venueList = page.getList();
        List<Map<String,Object>> values = Lists.newArrayList();
        for(ReserveVenue venue : venueList){
            Map<String,Object> d = MyBeanUtils.describe(venue, "id", "name", "address", "tel");
            d.put("imgSrc",Global.getConfig("system.url") + "mechanism/file/imageMobile/" + venue.getId() + "/ReserveVenue/venuePic?width=230&height=130&random=" + RandomUtils.nextInt(1, 100));
            d.put("distance", GpsUtils.getDistance(lat,lng,venue.getAddressX(),venue.getAddressY()));
            d.put("price",50);
            d.put("projectId",1);
            d.put("fraction",3);//场馆评分
            d.put("hot","1");
            values.add(d);
        }
        json.put("data",values);
        return JsonUtils.writeObjectToJson(json);
    }

    @Override
    public String getName() {
        return "Venue_List_Api";
    }
}
