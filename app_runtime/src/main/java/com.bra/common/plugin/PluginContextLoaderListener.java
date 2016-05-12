package com.bra.common.plugin;

import com.bra.common.config.Global;
import com.bra.common.scanner.FileScanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import java.util.List;

/**
 * Created by xiaobin268 on 2014-9-23.
 */
public class PluginContextLoaderListener extends ContextLoaderListener {

    private static final Logger LOG = LoggerFactory.getLogger(PluginContextLoaderListener.class);

    public PluginContextLoaderListener() {
    }


    public PluginContextLoaderListener(WebApplicationContext context) {
        super(context);
    }

    @Override
    public WebApplicationContext initWebApplicationContext(ServletContext servletContext) {
        /*if (!SystemService.printKeyLoadMessage()) {
            return null;
        }*/
        return super.initWebApplicationContext(servletContext);
    }

    @Override
    public void contextInitialized(ServletContextEvent event) {
        initWebApplicationContext(event.getServletContext());
        //--------------plugin start---------------------------
        PluginFilter pluginFilter = new PluginFilter();
        FileScanner fileScanner = new FileScanner(pluginFilter);
        List<Class> entityClassList = pluginFilter.getClassList();
        //todo 暂时固定包名，所有插件都应该在这个包下
        fileScanner.find(Global.getConfig("plugin.package"));
        for (Class clazz : entityClassList) {
            try {
                IPlugin plugin = (IPlugin) clazz.newInstance();
                if ("true".equals(Global.getConfig(plugin.getKey() + "-start"))) {
                    LOG.info("开始启动-" + plugin.getKey());
                    plugin.init();
                }
            } catch (InstantiationException e) {
                LOG.error("实例化插件报错：" + e.getLocalizedMessage());
            } catch (IllegalAccessException e) {
                LOG.error("实例化插件报错：" + e.getLocalizedMessage());
            }

        }
    }


    /**
     * Close the root web application context.
     */
    @Override
    public void contextDestroyed(ServletContextEvent event) {
        super.contextDestroyed(event);
        PluginFilter pluginFilter = new PluginFilter();
        FileScanner fileScanner = new FileScanner(pluginFilter);
        List<Class> entityClassList = pluginFilter.getClassList();
        //todo 暂时固定包名，所有插件都应该在这个包下
        fileScanner.find(Global.getConfig("plugin.package"));
        for (Class clazz : entityClassList) {
            try {
                IPlugin plugin = (IPlugin) clazz.newInstance();
                plugin.destroyed();
            } catch (InstantiationException e) {
                LOG.error("插件注销报错：" + e.getLocalizedMessage());
            } catch (IllegalAccessException e) {
                LOG.error("插件注销报错：" + e.getLocalizedMessage());
            }

        }
    }
}
