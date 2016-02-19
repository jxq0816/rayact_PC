package com.bra.plugin.migration.service.impl.article;

import com.bra.common.persistence.Page;
import com.bra.common.utils.JsonUtils;
import com.bra.common.utils.MyBeanUtils;
import com.bra.common.utils.SpringContextHolder;
import com.bra.modules.cms.eneity.Article;
import com.bra.modules.cms.service.ArticleService;
import com.bra.plugin.migration.Utils;
import com.bra.plugin.migration.entity.MobileHead;
import com.bra.plugin.migration.service.TransmitsService;
import com.google.common.collect.Lists;
import org.apache.commons.collections.MapUtils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 资讯二级页面
 * Created by xiaobin on 16/2/19.
 */
public class ListArticleApi implements TransmitsService {

    private ArticleService getArticleService() {
        return SpringContextHolder.getBean(ArticleService.class);
    }

    @Override
    public String executeTodo(MobileHead mobileHead, Map<String, Object> request) {
        Map<String, Object> json = Utils.headMap(mobileHead);
        String pageNo = MapUtils.getString(request, "pageNo");
        Page<Article> page = getArticleService().listArticle(pageNo);
        json.put("count", page.getCount());
        List<Article> articleList = page.getList();
        List<Map<String, Object>> list = Lists.newArrayList();
        list.addAll(articleList.stream().map(article -> MyBeanUtils.describe(article, "id", "title", "description", "imageSrc")).collect(Collectors.toList()));
        json.put("data", list);
        return JsonUtils.writeObjectToJson(json);
    }

    @Override
    public String getName() {
        return "List_Article_Api";
    }
}
