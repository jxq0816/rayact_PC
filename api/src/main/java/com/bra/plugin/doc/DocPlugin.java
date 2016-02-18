package com.bra.plugin.doc;

import com.bra.common.config.Global;
import com.bra.common.plugin.IPlugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by xiaobin268 on 2014-9-23.
 */
public class DocPlugin implements IPlugin {

    public static final String PLUGIN_KEY = "doc";

    private static final Logger LOGGER = LoggerFactory.getLogger(DocPlugin.class);


    @Override
    public void init() {
        try {
            Global.putProperties("docviewer", "properties/docviewer.properties");
        } catch (Exception e) {
            LOGGER.error(e.getLocalizedMessage());
        }
    }

    @Override
    public void destroyed() {
    }

    @Override
    public String getKey() {
        return PLUGIN_KEY;
    }
}
