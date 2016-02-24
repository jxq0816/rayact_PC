package com.bra.plugin.migration.service.impl.article;

import com.bra.common.utils.JsonUtils;
import com.bra.plugin.migration.Utils;
import com.bra.plugin.migration.entity.MobileHead;
import com.bra.plugin.migration.service.TransmitsService;
import com.bra.plugin.migration.service.assembly.CommentList;
import java.util.Map;

/**
 * Created by dell on 2016/2/23.
 */
public class ListCommentApi implements TransmitsService{

    @Override
    public String executeTodo(MobileHead mobileHead, Map<String, Object> request) {
        Map<String,Object> json = Utils.headMap(mobileHead);
        CommentList commentList = new CommentList();
        commentList.list(json,request,null);
        return JsonUtils.writeObjectToJson(json);
    }

    @Override
    public String getName() {

        return "List_Comment_Api";
    }
}
