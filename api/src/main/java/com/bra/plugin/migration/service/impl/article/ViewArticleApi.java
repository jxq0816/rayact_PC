package com.bra.plugin.migration.service.impl.article;

import com.bra.common.config.Global;
import com.bra.common.persistence.Page;
import com.bra.common.utils.JsonUtils;
import com.bra.common.utils.MyBeanUtils;
import com.bra.common.utils.SpringContextHolder;
import com.bra.modules.cms.eneity.Article;
import com.bra.modules.cms.eneity.Comment;
import com.bra.modules.cms.service.ArticleService;
import com.bra.modules.cms.service.CommentService;
import com.bra.plugin.migration.Utils;
import com.bra.plugin.migration.entity.MobileHead;
import com.bra.plugin.migration.service.TransmitsService;
import com.google.common.collect.Lists;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.RandomUtils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 新闻查看
 * Created by xiaobin on 16/2/19.
 */
public class ViewArticleApi implements TransmitsService {

    private static final String modelName = "com.world.think.modules.sys.entity.User";//实体类路径

    private ArticleService getArticleService() {
        return SpringContextHolder.getBean(ArticleService.class);
    }

    private CommentService getCommentService(){
        return SpringContextHolder.getBean(CommentService.class);
    }

    @Override
    public String executeTodo(MobileHead mobileHead, Map<String, Object> request) {
        Map<String,Object> json = Utils.headMap(mobileHead);
        String id = MapUtils.getString(request,"id");
        Article article = getArticleService().viewArticle(new Article(id));
        Map<String,Object> map = MyBeanUtils.describe(article,"id","description","title","updateDate");
        map.put("copyfrom",article.getArticleData().getCopyfrom());
        map.put("content",article.getArticleData().getContent());
        map.put("share", Global.getConfig("system.url")+"article/view"+map.get("id"));
        Page<Comment> page = getCommentService().listComment(id,"1");

        List<Comment> commentList = page.getList();
        List<Map<String, Object>> list = Lists.newArrayList();
        for(Comment comment : commentList){
            Map<String,Object> commentMap = MyBeanUtils.describe(comment, "id", "content","createDate");
            String filePath = Global.getConfig("system.url") + "mechanism/file/imageMobile/" + comment.getMember().getId() + "/" + modelName +"/_userPhoto?random="+ RandomUtils.nextInt(1, 100);
            commentMap.put("imgSrc",filePath);
            commentMap.put("username",comment.getMember().getName());
            list.add(commentMap);
        }
        json.put("commentList",list);
        json.put("data",map);
        return JsonUtils.writeObjectToJson(json);
    }

    @Override
    public String getName() {
        return "View_Article_Api";
    }
}
