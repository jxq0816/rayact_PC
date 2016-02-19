package com.bra.api.web;

import com.bra.modules.cms.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by xiaobin on 16/2/19.
 */
@Controller
@RequestMapping("/article")
public class ArticleViewController {

    @Autowired
    private ArticleService articleService;

    //新闻查看
    @RequestMapping("/view/{id}")
    public String view(@PathVariable String id) {
        return null;
    }
}
