package com.bra.plugin.migration.service.assembly;

import com.bra.common.config.Global;
import com.bra.common.persistence.Page;
import com.bra.common.utils.DateUtils;
import com.bra.common.utils.MyBeanUtils;
import com.bra.common.utils.SpringContextHolder;
import com.bra.common.utils.StringUtils;
import com.bra.modules.cms.eneity.Comment;
import com.bra.modules.cms.service.CommentService;
import com.google.common.collect.Lists;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.RandomUtils;

import java.util.List;
import java.util.Map;

/**
 * Created by dell on 2016/2/23.
 */
public class CommentList {

    private static final String modelName = "com.world.think.modules.sys.entity.User";//实体类路径

    private CommentService getCommentService(){
        return SpringContextHolder.getBean(CommentService.class);
    }

    public CommentList(){}

    public void list(Map<String,Object> json,Map<String,Object> request,String modelKey){
        String id = MapUtils.getString(request,"id");
        if(StringUtils.isBlank(modelKey)){
            modelKey = MapUtils.getString(request,"modelKey");
        }
        Page<Comment> page = getCommentService().listComment(modelKey,id,"1");
        List<Comment> commentList = page.getList();
        List<Map<String, Object>> list = Lists.newArrayList();
        for(Comment comment : commentList){
            Map<String,Object> commentMap = MyBeanUtils.describe(comment, "id", "content");
            commentMap.put("createDate", DateUtils.formatDate(comment.getCreateDate(),"yyyy-MM-dd HH:mm"));
            String filePath = Global.getConfig("system.url") + "mechanism/file/imageMobile/" + comment.getMember().getId() + "/" + modelName +"/_userPhoto?random="+ RandomUtils.nextInt(1, 100);
            commentMap.put("imgSrc",filePath);
            if(StringUtils.isBlank(comment.getMember().getName())){
                commentMap.put("username","匿名用户");
            }else{
                commentMap.put("username",comment.getMember().getName());
            }
            list.add(commentMap);
        }
        json.put("commentList",list);
    }
}
