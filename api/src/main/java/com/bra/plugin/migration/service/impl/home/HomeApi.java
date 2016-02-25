package com.bra.plugin.migration.service.impl.home;

import com.bra.common.utils.JsonUtils;
import com.bra.common.utils.SpringContextHolder;
import com.bra.modules.cms.json.HomeArticle;
import com.bra.modules.cms.service.ArticleService;
import com.bra.plugin.migration.Utils;
import com.bra.plugin.migration.entity.MobileHead;
import com.bra.plugin.migration.service.TransmitsService;
import java.util.List;
import java.util.Map;

/**
 * APP-首页
 * Created by xiaobin on 16/2/19.
 */
public class HomeApi implements TransmitsService {

    private ArticleService getArticleService(){
        return SpringContextHolder.getBean(ArticleService.class);
    }

    @Override
    public String executeTodo(MobileHead mobileHead, Map<String, Object> request) {
        Map<String,Object> json = Utils.headMap(mobileHead);

        List<HomeArticle> home = getArticleService().loadHomeArticle();
        //资讯相关
        //home.putAll(getArticleService().loadHomeArticle());

        json.put("data",home);

        return JsonUtils.writeObjectToJson(json);
    }

    @Override
    public String getName() {
        return "Home_Api";
    }
}
