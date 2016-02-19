package com.bra.modules.cms.dao;

import com.bra.common.persistence.CrudDao;
import com.bra.common.persistence.annotation.MyBatisDao;
import com.bra.modules.cms.eneity.Article;

import java.util.List;

/**
 * Created by dell on 2016/2/19.
 */
@MyBatisDao
public interface ArticleDao extends CrudDao<Article> {

    List<Article> homeArticle(Article article);

    Article viewArticle(Article article);
}
