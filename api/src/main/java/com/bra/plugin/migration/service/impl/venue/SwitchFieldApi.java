package com.bra.plugin.migration.service.impl.venue;

import com.bra.common.config.Global;
import com.bra.common.utils.JsonUtils;
import com.bra.common.utils.SpringContextHolder;
import com.bra.modules.reserve.entity.ReserveVenue;
import com.bra.modules.reserve.service.FieldService;
import com.bra.plugin.migration.Utils;
import com.bra.plugin.migration.entity.MobileHead;
import com.bra.plugin.migration.service.TransmitsService;
import com.google.common.collect.Lists;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.RandomUtils;

import java.util.List;
import java.util.Map;

/**
 * 项目切换
 * Created by dell on 2016/2/24.
 */
public class SwitchFieldApi implements TransmitsService {

    private FieldService getFieldService() {
        return SpringContextHolder.getBean(FieldService.class);
    }

    @Override
    public String executeTodo(MobileHead mobileHead, Map<String, Object> request) {
        Map<String, Object> json = Utils.headMap(mobileHead);
        String id = MapUtils.getString(request,"id");//场馆Id
        String projectId = MapUtils.getString(request,"projectId");//项目Id
        List<Map<String,Object>> fields = getFieldService().findFieldByProjectId(projectId,new ReserveVenue(id));
        List<String> smallImgSrcs = Lists.newArrayList();
        List<String> bigImgSrcs = Lists.newArrayList();
        for(Map<String,Object> fieldPic : fields){
            smallImgSrcs.add(Global.getConfig("system.url") + "mechanism/file/imageMobile/" + fieldPic.get("id") + "/reserveField/fieldPic?width=230&height=130&random=" + RandomUtils.nextInt(1, 100));
            bigImgSrcs.add(Global.getConfig("system.url") + "mechanism/file/imageMobile/" + fieldPic.get("id") + "/reserveField/fieldPic?random=" + RandomUtils.nextInt(1, 100));
        }
        json.put("smallImgSrcs",smallImgSrcs);
        json.put("bigImgSrcs",bigImgSrcs);
        json.put("imgCount",smallImgSrcs.size());
        return JsonUtils.writeObjectToJson(json);
    }

    @Override
    public String getName() {
        return "Switch_Field_Api";
    }
}
