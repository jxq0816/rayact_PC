package com.bra.plugin.migration.service.impl.article;

import com.bra.common.config.Global;
import com.bra.common.utils.JsonUtils;
import com.bra.common.utils.MyBeanUtils;
import com.bra.common.utils.SpringContextHolder;
import com.bra.modules.cms.eneity.Article;
import com.bra.modules.cms.eneity.Comment;
import com.bra.modules.cms.service.ArticleService;
import com.bra.plugin.migration.Utils;
import com.bra.plugin.migration.entity.MobileHead;
import com.bra.plugin.migration.service.TransmitsService;
import com.bra.plugin.migration.service.assembly.CommentList;
import org.apache.commons.collections.MapUtils;

import java.util.Map;

/**
 * 新闻查看
 * Created by xiaobin on 16/2/19.
 */
public class ViewArticleApi implements TransmitsService {

    private static final String modelName = "com.world.think.modules.sys.entity.User";//实体类路径

    private ArticleService getArticleService() {
        return SpringContextHolder.getBean(ArticleService.class);
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

        CommentList commentList = new CommentList();
        commentList.list(json,request, Comment.MODEL_ARTICLE);

        json.put("data",map);
        return JsonUtils.writeObjectToJson(json);
    }

    @Override
    public String getName() {
        return "View_Article_Api";
    }
}
