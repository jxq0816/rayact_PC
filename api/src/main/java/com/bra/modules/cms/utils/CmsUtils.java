package com.bra.modules.cms.utils;

import com.bra.common.config.Global;
import com.bra.modules.cms.eneity.Article;
import org.apache.commons.lang3.RandomUtils;

/**
 * Created by xiaobin on 16/2/19.
 */
public class CmsUtils {

    private static final String ARTICLE_MODEL = "com.bra.modules.cms.entity.Article";

    public static String getImgSrc(Article article) {
        return Global.getConfig("system.url") + "mechanism/file/imageMobile/" + article.getId() + "/" + ARTICLE_MODEL + "/pic?width=188&height=188&random=" + RandomUtils.nextInt(1, 100);
    }
}
