package com.bra.plugin.migration.service.impl.venue;

import com.bra.common.utils.DateUtils;
import com.bra.common.utils.JsonUtils;
import com.bra.common.utils.SpringContextHolder;
import com.bra.common.utils.StringUtils;
import com.bra.modules.reserve.entity.form.FieldPrice;
import com.bra.modules.reserve.service.FieldPriceService;
import com.bra.modules.reserve.utils.TimeUtils;
import com.bra.plugin.migration.Utils;
import com.bra.plugin.migration.entity.MobileHead;
import com.bra.plugin.migration.service.TransmitsService;
import org.apache.commons.collections.MapUtils;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by dell on 2016/2/25.
 */
public class SelectFieldApi implements TransmitsService {

    private FieldPriceService getFieldPriceService(){
        return SpringContextHolder.getBean(FieldPriceService.class);
    }

    @Override
    public String executeTodo(MobileHead mobileHead, Map<String, Object> request) {
        Map<String,Object> json = Utils.headMap(mobileHead);
        String venueId = MapUtils.getString(request,"venueId");
        String projectId = MapUtils.getString(request,"projectId");
        String date = MapUtils.getString(request,"date");
        Date dateTime = new Date();
        if(StringUtils.isBlank(venueId)){
            json.put("status_code","201");
            json.put("message","所属场馆不能为空");
            return JsonUtils.writeObjectToJson(json);
        }
        if(StringUtils.isBlank(projectId)){
            json.put("status_code","202");
            json.put("message","所属项目不能为空");
            return JsonUtils.writeObjectToJson(json);
        }
        if(StringUtils.isNotBlank(date)){
            dateTime = DateUtils.parseDate(date);
        }
        List<String> times = TimeUtils.getTimeSpacListValue("09:00:00", "23:00:00", 60);
        List<FieldPrice> fieldPriceList = getFieldPriceService().findByDate(venueId,projectId,"1",dateTime,times);
        json.put("fieldPriceList",fieldPriceList);
        return JsonUtils.writeObjectToJson(json);
    }

    @Override
    public String getName() {
        return "Select_Field_Api";
    }
}
