package com.bra.common.plugin;

import com.bra.common.scanner.filter.IScannerFilter;
import javolution.util.FastList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.List;

/**
 * Created by xiaobin268 on 2014-9-23.
 */
public class PluginFilter implements IScannerFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(PluginFilter.class);

    private List<Class> classList = new FastList<Class>();

    @Override
    public boolean filter(String urlStr, URL url, ClassLoader classLoader) {
        String classPathStr = urlStr.replace("/", ".");
        classPathStr = classPathStr.substring(0, classPathStr.length() - 6);
        try {
            Class clazz = classLoader.loadClass(classPathStr);
            if (IPlugin.class.isAssignableFrom(clazz) ) {
                classList.add(clazz);
                return true;
            } else {
                return false;
            }
        } catch (ClassNotFoundException e) {
            LOGGER.error(e.getMessage());
        }
        return false;
    }

    public List<Class> getClassList() {
        return classList;
    }
}
