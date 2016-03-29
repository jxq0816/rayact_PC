package com.bra.modules.reserve.listener.venue;

import com.bra.common.utils.Collections3;
import com.bra.common.utils.JsonUtils;
import com.bra.common.utils.StringUtils;
import com.bra.modules.reserve.entity.ReserveProject;
import com.bra.modules.reserve.entity.ReserveVenueCons;
import com.bra.modules.reserve.entity.ReserveVenueOrder;
import com.bra.modules.reserve.event.main.MainControlerEvent;
import com.bra.modules.reserve.service.ReserveProjectService;
import com.bra.modules.reserve.service.ReserveVenueConsService;
import com.bra.modules.reserve.service.ReserveVenueOrderService;
import com.bra.modules.reserve.utils.AuthorityUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;


/**
 * 从项目纬度查询
 * Created by xiaobin on 16/1/26.
 */
@Component
public class FieldProjectListener {

    @Autowired
    private ReserveVenueConsService reserveVenueConsService;
    @Autowired
    private ReserveVenueOrderService reserveVenueOrderService;
    @Autowired
    private ReserveProjectService reserveProjectService;

    @EventListener
    public void onEvent(MainControlerEvent event) {
        Map<String, Object> data = event.getMainBean().getMap();
        List<String> venueIds = (List<String>) data.get("venueIds");
        if (Collections3.isEmpty(venueIds)) {
            return;
        }
        ReserveVenueCons venueCons = new ReserveVenueCons();
        venueCons.getSqlMap().put("dsf", AuthorityUtils.getVenueIds(venueIds));
        venueCons.setReserveType("4");//查询已经结算得信息
        List<Map<String, Object>> venueConsMap = reserveVenueConsService.findPriceGroupProject(venueCons);

        ReserveVenueOrder venueOrder = new ReserveVenueOrder();
        venueOrder.getSqlMap().put("dsf", AuthorityUtils.getVenueIds(venueIds));

        List<Map<String, Object>> venueOrderMap = reserveVenueOrderService.findPriceGroupProject(venueOrder);
        venueConsMap.addAll(venueOrderMap);

        List<Map<String, Object>> mapList = Lists.newArrayList();
        Map<String, Object> value;
        List<ReserveProject> projectList = reserveProjectService.findList(new ReserveProject());
        List<String> names = Lists.newArrayList();
        for (ReserveProject project : projectList) {
            Double total = 0D;
            value = Maps.newConcurrentMap();
            for (Map<String, Object> map : venueConsMap) {
                if (project.getName().equals(map.get("projectName"))) {
                    if(map.get("orderPrice")!=null){
                        total += NumberUtils.toDouble(map.get("orderPrice").toString());
                    }
                }
            }
            value.put("name", project.getName());
            value.put("value", total);
            names.add("'" + project.getName() + "'");
            mapList.add(value);
        }
        data.put("projectFieldChart", JsonUtils.writeObjectToJson(mapList));
        data.put("projectFieldNameList", StringUtils.join(names, ','));

    }

    public static void main(String[] args) {
        Map<String, Object> map = Maps.newConcurrentMap();
        map.put("ss", 11);
        map.put("dd", 11);
        System.out.println(JsonUtils.writeObjectToJson(map));
    }
}
