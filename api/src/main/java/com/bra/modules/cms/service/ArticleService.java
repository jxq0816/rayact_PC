package com.bra.modules.cms.service;

import com.bra.common.persistence.Page;
import com.bra.common.service.CrudService;
import com.bra.common.utils.MyBeanUtils;
import com.bra.common.utils.StringUtils;
import com.bra.modules.cms.dao.ArticleDao;
import com.bra.modules.cms.dao.CategoryDao;
import com.bra.modules.cms.eneity.Article;
import com.bra.modules.cms.eneity.Category;
import com.bra.modules.cms.json.HomeArticle;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private CategoryDao categoryDao;

    public Page<Article> listArticle(String pNo) {
        Integer pageNo = StringUtils.isBlank(pNo) ? 1 : NumberUtils.toInt(pNo, 1);
        Page<Article> page = new Page<>();
        page.setPageNo(pageNo);
        page.setPageSize(10);

        Category category = new Category(Category.MODEL_ARTICLE);
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
        list.addAll(articleList.stream().map(data -> MyBeanUtils.describe(data, "id", "title", "description", "imageSrc", "url"))
                .collect(Collectors.toList()));
        return list;
    }

    //首页图片新闻
    @Transactional(readOnly = false)
    public List<Map<String, Object>> homeArticle(String categoryId, int pageSize) {
        Article article = new Article();
        Category category = new Category(categoryId);
        article.getSqlMap().put("pageSize", pageSize);
        article.setCategory(category);
        List<Article> articleList = dao.homeArticle(article);

        List<Map<String, Object>> list = Lists.newArrayList();
        list.addAll(articleList.stream().map(data -> MyBeanUtils.describe(data, "id", "title", "description", "imageSrc", "url"))
                .collect(Collectors.toList()));
        return list;
    }

    public Map<String, HomeArticle> loadHomeArticle() {

        Map<String, HomeArticle> data = Maps.newLinkedHashMap();

        Category category = new Category();
        category.setParent(new Category("2"));
        List<Category> categoryList = categoryDao.findList(category);
        for (Category cate : categoryList) {
            //资讯
            HomeArticle article = new HomeArticle();
            article.setId(cate.getId());
            article.setTitle(cate.getName());
            List<Map<String, Object>> picArticleList = homeArticle(cate.getId(), "tupian".equals(cate.getKeywords()) ? 3 : 10);
            article.setValue(picArticleList);
            data.put(cate.getKeywords(), article);
        }

        return data;

    }


    public Article viewArticle(Article article) {
        Article a = dao.viewArticle(article);
        return a;
    }
}
