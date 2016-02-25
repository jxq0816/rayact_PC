package com.bra.plugin.migration.service.impl.mechanism;

import com.bra.common.utils.JsonUtils;
import com.bra.common.utils.SpringContextHolder;
import com.bra.common.utils.StringUtils;
import com.bra.modules.mechanism.entity.MCollection;
import com.bra.modules.mechanism.service.CollectionService;
import com.bra.modules.reserve.entity.ReserveMember;
import com.bra.modules.reserve.service.MemberService;
import com.bra.plugin.migration.Utils;
import com.bra.plugin.migration.entity.MobileHead;
import com.bra.plugin.migration.service.TransmitsService;
import org.apache.commons.collections.MapUtils;

import java.util.Map;

/**
 * Created by dell on 2016/2/24.
 */
public class AddCollectionApi implements TransmitsService {

    private MemberService getMemberService(){
        return SpringContextHolder.getBean(MemberService.class);
    }

    private CollectionService getCollectionService(){
        return SpringContextHolder.getBean(CollectionService.class);
    }

    @Override
    public String executeTodo(MobileHead mobileHead, Map<String, Object> request) {
        Map<String,Object> json = Utils.headMap(mobileHead);
        String memberId = MapUtils.getString(request,"userId");
        if(StringUtils.isBlank(memberId)){
            json.put("status_code","201");
            json.put("message","收藏人不能为空");
            return JsonUtils.writeObjectToJson(json);
        }
        ReserveMember member = getMemberService().get(memberId);
        if(StringUtils.isBlank(memberId)){
            json.put("status_code","202");
            json.put("message","收藏人不能为空");
            return JsonUtils.writeObjectToJson(json);
        }
        String modelId = MapUtils.getString(request,"modelId");
        String modelKey = MapUtils.getString(request,"modelKey");
        if(StringUtils.isBlank(modelId)){
            json.put("status_code","203");
            json.put("message","收藏主体不能为空");
            return JsonUtils.writeObjectToJson(json);
        }
        if(StringUtils.isBlank(modelKey)){
            json.put("status_code","204");
            json.put("message","业务模型不能为空");
            return JsonUtils.writeObjectToJson(json);
        }
        MCollection collection = new MCollection();
        collection.setMember(member);
        collection.setModelId(modelId);
        collection.setModelKey(modelKey);
        getCollectionService().add(collection);
        return JsonUtils.writeObjectToJson(json);
    }

    @Override
    public String getName() {
        return "Add_Collection_Api";
    }
}
