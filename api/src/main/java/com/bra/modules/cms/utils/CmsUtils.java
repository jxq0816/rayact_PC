package com.bra.modules.cms.utils;

import com.bra.common.config.Global;
import com.bra.modules.cms.eneity.Article;
import com.bra.modules.cms.eneity.Category;
import org.apache.commons.lang3.RandomUtils;

/**
 * Created by xiaobin on 16/2/19.
 */
public class CmsUtils {

    private static final String ARTICLE_MODEL = "com.bra.modules.cms.entity.Article";

    public static String getImgSrc(Article article) {
        if(article.getCategory().getId().equals(Category.MODEL_PICTURE))
            return Global.getConfig("system.url") + "mechanism/file/imageMobile/" + article.getId() + "/" + ARTICLE_MODEL + "/pic?width=750&height=200&random=" + RandomUtils.nextInt(1, 100);
        else if(article.getCategory().getId().equals(Category.MODEL_PICTURE))
            return Global.getConfig("system.url") + "mechanism/file/imageMobile/" + article.getId() + "/" + ARTICLE_MODEL + "/pic?width=188&height=188&random=" + RandomUtils.nextInt(1, 100);
        return Global.getConfig("system.url") + "mechanism/file/imageMobile/" + article.getId() + "/" + ARTICLE_MODEL + "/pic?width=188&height=188&random=" + RandomUtils.nextInt(1, 100);
    }

    public static String getImgSrc(Article article,int width,int height) {
        return Global.getConfig("system.url") + "mechanism/file/imageMobile/" + article.getId() + "/" + ARTICLE_MODEL + "/pic?width="+width+"&height="+height+"&random=" + RandomUtils.nextInt(1, 100);
    }
}
