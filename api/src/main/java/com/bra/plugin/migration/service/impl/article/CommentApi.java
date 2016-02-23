package com.bra.plugin.migration.service.impl.article;

import com.bra.common.utils.JsonUtils;
import com.bra.common.utils.SpringContextHolder;
import com.bra.common.utils.StringUtils;
import com.bra.modules.cms.eneity.Category;
import com.bra.modules.cms.eneity.Comment;
import com.bra.modules.cms.service.CommentService;
import com.bra.modules.reserve.entity.ReserveMember;
import com.bra.modules.reserve.service.MemberService;
import com.bra.plugin.migration.Utils;
import com.bra.plugin.migration.entity.MobileHead;
import com.bra.plugin.migration.service.TransmitsService;
import org.apache.commons.collections.MapUtils;

import java.util.Date;
import java.util.Map;

/**
 * 评论
 *
 */
public class CommentApi implements TransmitsService {

    private CommentService getCommentService()
    {
        return SpringContextHolder.getBean(CommentService.class);
    }

    private MemberService getMemberService(){
        return SpringContextHolder.getBean(MemberService.class);
    }

    @Override
    public String executeTodo(MobileHead mobileHead, Map<String, Object> map) {
        Map<String,Object> json = Utils.headMap(mobileHead);
        String title= MapUtils.getString(map,"title");
        String content=MapUtils.getString(map,"content");
        String contentId=MapUtils.getString(map,"contentId");
        String ip=MapUtils.getString(map,"ip");
        String userId=MapUtils.getString(map,"userId");
        if(StringUtils.isBlank(content)){
            json.put("status_code","301");
            json.put("message","评论内容为空");
            return JsonUtils.writeObjectToJson(json);
        }
        if(StringUtils.isBlank(ip)){
            json.put("status_code","301");
            json.put("message","评论者的IP");
            return JsonUtils.writeObjectToJson(json);
        }
        if(StringUtils.isBlank(userId)){
            json.put("status_code","301");
            json.put("message","评论者为空");
            return JsonUtils.writeObjectToJson(json);
        }
        if(StringUtils.isBlank(contentId)){
            json.put("status_code","301");
            json.put("message","contentId:所属资讯为空");
            return JsonUtils.writeObjectToJson(json);
        }
        ReserveMember member = getMemberService().get(userId);
        Comment comment=new Comment();
        comment.setContent(content);
        comment.setTitle(title);
        comment.setContentId(contentId);
        comment.setCategory(new Category(Category.MODEL_ARTICLE));
        comment.setIp(ip);
        comment.setName(member.getName());
        comment.setCreateDate(new Date());
        getCommentService().save(comment);//保存评论
        json.put("status_code","300");
        json.put("message","保存评论成功");
        return JsonUtils.writeObjectToJson(json);
    }

    @Override
    public String getName() {
        return "Comment_Api";
    }
}
