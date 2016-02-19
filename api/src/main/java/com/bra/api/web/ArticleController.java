package com.bra.api.web;

import com.bra.modules.cms.eneity.Article;
import com.bra.modules.cms.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by xiaobin on 16/2/19.
 */
@Controller
@RequestMapping("article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    //新闻查看
    @RequestMapping("/view/{id}")
    public String view(@PathVariable String id, Model model) {
        model.addAttribute("article",articleService.viewArticle(new Article(id)));
        return "/article/view";
    }
}
