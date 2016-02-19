package com.bra.modules.cms.service;
import com.bra.common.service.CrudService;
import com.bra.modules.cms.dao.ArticleDao;
import com.bra.modules.cms.eneity.Article;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by xiaobin on 16/2/17.
 */
@Service
@Transactional(readOnly = true)
public class ArticleService extends CrudService<ArticleDao,Article> {

    @Transactional(readOnly = false)
    public List<Article> homeArticle(Article article) {
        return dao.homeArticle(article);
    }

    public Article viewArticle(Article article){
        return dao.viewArticle(article);
    }
}
