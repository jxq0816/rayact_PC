package com.bra.modules.cms.service;

import com.bra.common.persistence.Page;
import com.bra.common.service.CrudService;
import com.bra.common.utils.MyBeanUtils;
import com.bra.common.utils.StringUtils;
import com.bra.modules.cms.dao.ArticleDao;
import com.bra.modules.cms.eneity.Article;
import com.bra.modules.cms.eneity.Category;
import com.bra.modules.cms.json.HomeArticle;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by xiaobin on 16/2/17.
 */
@Service
@Transactional(readOnly = true)
public class ArticleService extends CrudService<ArticleDao, Article> {

    public Page<Article> listArticle(String pNo) {
        Integer pageNo = StringUtils.isBlank(pNo) ? 1 : NumberUtils.toInt(pNo, 1);
        Page<Article> page = new Page<>();
        page.setPageNo(pageNo);
        page.setPageSize(10);

        Category category = new Category(Category.MODEL_PICTURE);
        Article article = new Article();
        article.setCategory(category);
        return super.findPage(page, article);
    }

    //首页资讯
    @Transactional(readOnly = false)
    public List<Map<String, Object>> homeArticle() {
        Article article = new Article();
        Category category = new Category(Category.MODEL_ARTICLE);
        article.getSqlMap().put("pageSize", 10);
        article.setCategory(category);
        List<Map<String, Object>> list = Lists.newArrayList();

        List<Article> articleList = dao.homeArticle(article);

        list.addAll(articleList.stream().map(data -> MyBeanUtils.describe(data, "id", "title", "description", "imageSrc"))
                .collect(Collectors.toList()));
        return list;
    }

    //首页图片新闻
    @Transactional(readOnly = false)
    public List<Map<String, Object>> homePicArticle() {
        Article article = new Article();
        Category category = new Category(Category.MODEL_PICTURE);
        article.getSqlMap().put("pageSize", 3);
        article.setCategory(category);
        List<Article> articleList = dao.homeArticle(article);

        List<Map<String, Object>> list = Lists.newArrayList();
        list.addAll(articleList.stream().map(data -> MyBeanUtils.describe(data, "id", "title", "description", "imageSrc"))
                .collect(Collectors.toList()));
        return list;
    }

    public Map<String,HomeArticle> loadHomeArticle() {

        Map<String, HomeArticle> data = Maps.newLinkedHashMap();
        //资讯
        HomeArticle picArticle = new HomeArticle();
        picArticle.setId(Category.MODEL_PICTURE);
        picArticle.setTitle("图片广告");

        List<Map<String, Object>> picArticleList = homeArticle();
        picArticle.setArticles(picArticleList);
        data.put("图片广告", picArticle);

        //资讯
        HomeArticle article = new HomeArticle();
        article.setId(Category.MODEL_ARTICLE);
        article.setTitle("咨询");
        List<Map<String, Object>> articleList = homeArticle();
        article.setArticles(articleList);
        data.put("资讯", article);

        return data;

    }


    public Article viewArticle(Article article) {
        Article a = dao.viewArticle(article);
        return a;
    }
}
