package com.bra.plugin.migration.service.impl.venue;

import com.bra.common.config.Global;
import com.bra.common.utils.JsonUtils;
import com.bra.common.utils.MyBeanUtils;
import com.bra.common.utils.SpringContextHolder;
import com.bra.modules.cms.eneity.Comment;
import com.bra.modules.reserve.entity.ReserveField;
import com.bra.modules.reserve.entity.ReserveVenue;
import com.bra.modules.reserve.service.FieldService;
import com.bra.modules.reserve.service.VenueService;
import com.bra.plugin.migration.Utils;
import com.bra.plugin.migration.entity.MobileHead;
import com.bra.plugin.migration.service.TransmitsService;
import com.bra.plugin.migration.service.assembly.CommentList;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.RandomUtils;

import java.util.List;
import java.util.Map;

/**
 * 场地查看
 * Created by dell on 2016/2/23.
 */
public class ViewVenueApi implements TransmitsService {

    private VenueService getVenueService(){
        return SpringContextHolder.getBean(VenueService.class);
    }

    private FieldService getFieldService(){
        return SpringContextHolder.getBean(FieldService.class);
    }

    @Override
    public String executeTodo(MobileHead mobileHead, Map<String, Object> request) {
        Map<String,Object> json = Utils.headMap(mobileHead);
        String id = MapUtils.getString(request,"id");
        ReserveVenue venue = getVenueService().get(new ReserveVenue(id));
        Map<String,Object> d = MyBeanUtils.describe(venue, "id", "name", "address", "tel","venueMoreService","remarks");
        d.put("imgSrc", Global.getConfig("system.url") + "mechanism/file/imageMobile/" + venue.getId() + "/ReserveVenue/venuePic?width=230&height=130&random=" + RandomUtils.nextInt(1, 100));

        ReserveField field = new ReserveField();
        field.setReserveVenue(venue);
        List<ReserveField> fieldList = getFieldService().findList(field);
        List<Map<String,Object>> fieldMapList = Lists.newArrayList();
        for(ReserveField f : fieldList){
            Map<String,Object> map = Maps.newConcurrentMap();
            map.put("id",f.getId());
            map.put("projectName",f.getReserveProject().getName());
            fieldMapList.add(map);
        }
        json.put("fields",fieldMapList);

        CommentList commentList = new CommentList();
        commentList.list(json,request, Comment.MODEL_VENUE);
        return JsonUtils.writeObjectToJson(json);
    }

    @Override
    public String getName() {
        return "View_Venue_Api";
    }
}
