package com.bra.plugin.migration.service.impl.venue;

import com.bra.common.config.Global;
import com.bra.common.utils.*;
import com.bra.modules.cms.eneity.Comment;
import com.bra.modules.mechanism.entity.MCollection;
import com.bra.modules.mechanism.service.CollectionService;
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
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 场地查看
 * Created by dell on 2016/2/23.
 */
public class ViewVenueApi implements TransmitsService {

    private VenueService getVenueService() {
        return SpringContextHolder.getBean(VenueService.class);
    }

    private FieldService getFieldService() {
        return SpringContextHolder.getBean(FieldService.class);
    }

    private CollectionService getCollectionService() {
        return SpringContextHolder.getBean(CollectionService.class);
    }

    @Override
    public String executeTodo(MobileHead mobileHead, Map<String, Object> request) {
        Map<String, Object> json = Utils.headMap(mobileHead);
        String id = MapUtils.getString(request, "id");//场馆Id
        String projectId = MapUtils.getString(request, "projectId");

        ReserveVenue venue = getVenueService().get(new ReserveVenue(id));
        Map<String, Object> d = MyBeanUtils.describe(venue, "id", "name", "address", "tel", "", "remarks");
        //d.put("imgSrc", Global.getConfig("system.url") + "mechanism/file/imageMobile/" + venue.getId()venueMoreService + "/ReserveVenue/venuePic?width=230&height=130&random=" + RandomUtils.nextInt(1, 100));
        String userId = MapUtils.getString(request, "userId");
        if (StringUtils.isNotBlank(userId)) {
            MCollection collection = getCollectionService().findByModelIdKeyUser(userId, id, MCollection.MODEL_VENUE);
            if (collection != null) {
                d.put("isCollection", "1");
            }
        }
        if (!d.containsKey("isCollection")) {
            d.put("isCollection", "0");
        }

        ReserveField field = new ReserveField();
        field.setReserveVenue(venue);
        List<Map<String, Object>> projectList = getFieldService().findProjectByField(field);
        int index = 0;
        for (Map<String, Object> map : projectList) {
            if (index == 0) {
                List<Map<String,Object>> fields = getFieldService().findFieldByProjectId(MapUtils.getString(map,"id"),new ReserveVenue(id));
                List<String> fieldPicList = Lists.newArrayList();
                for(Map<String,Object> fieldPic : fields){
                    fieldPicList.add(Global.getConfig("system.url") + "mechanism/file/imageMobile/" + fieldPic.get("id") + "/reserveField/fieldPic?width=230&height=130&random=" + RandomUtils.nextInt(1, 100));
                }
                d.put("imgSrcs",fieldPicList);
            }
            index++;
            map.put("days", getNextDaysMap(new Date(), 7));
        }
        d.put("projects", projectList);
        json.putAll(d);
        CommentList commentList = new CommentList();
        commentList.list(json, request, Comment.MODEL_VENUE);
        return JsonUtils.writeObjectToJson(json);
    }

    public static List<Date> getNextDays(Date now, int next) {
        List<Date> dateList = Lists.newLinkedList();
        Date date;
        for (int i = 0; i < next; i++) {
            date = DateUtils.addDays(now, i);
            dateList.add(date);
        }
        return dateList;
    }

    public static Map<String,String> getNextDaysMap(Date now, int next) {
        List<Date> dateList = getNextDays(now, next);
        Map<String,String> dates = Maps.newLinkedHashMap();
        for (Date date : dateList) {
            if(date.equals(now)){
                dates.put(DateUtils.formatDate(date, "MM月dd日"), "今天");
            }else{
                dates.put(DateUtils.formatDate(date, "MM月dd日"), com.bra.modules.reserve.utils.TimeUtils.getWeekOfDate(date));
            }
        }
        return dates;
    }

    @Override
    public String getName() {
        return "View_Venue_Api";
    }
}
