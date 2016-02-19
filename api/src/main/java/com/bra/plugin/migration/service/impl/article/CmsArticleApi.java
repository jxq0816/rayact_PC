package com.bra.plugin.migration.service.impl.article;

import com.bra.common.utils.JsonUtils;
import com.bra.common.utils.MyBeanUtils;
import com.bra.common.utils.SpringContextHolder;
import com.bra.modules.cms.eneity.Article;
import com.bra.modules.cms.service.ArticleService;
import com.bra.plugin.migration.Utils;
import com.bra.plugin.migration.entity.MobileHead;
import com.bra.plugin.migration.service.TransmitsService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.util.List;
import java.util.Map;

/**
 * 咨询首页
 * Created   on 16/2/19.
 */
public class CmsArticleApi implements TransmitsService {

    private ArticleService getArticleService(){
        return SpringContextHolder.getBean(ArticleService.class);
    }

    @Override
    public String executeTodo(MobileHead mobileHead, Map<String, Object> request) {
        Map<String,Object> json = Utils.headMap(mobileHead);

        List<Article> articleList = getArticleService().homeArticle(new Article());
        List<Map<String,Object>> jsonList = Lists.newArrayList();
        Map<String,Object> bean ;
        for(Article article : articleList){
            bean = Maps.newConcurrentMap();
            bean.put("id",article.getId());
            //添加其余字段
            bean.put("title",article.getTitle());
            bean.put("description",article.getDescription());
            bean.put("imgSrc",article.getImageSrc());
            jsonList.add(bean);
        }
        json.put("data",jsonList);
        return JsonUtils.writeObjectToJson(json);
    }

    @Override
    public String getName() {
        return "Home_Article_Api";
    }
}
