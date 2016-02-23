package com.bra.plugin.migration.service.filter;

import com.bra.common.scanner.filter.IScannerFilter;
import com.bra.plugin.migration.service.TransmitsService;
import javolution.util.FastList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.List;

/**
 * Created by xiaobin on 16/2/16.
 */
public class TransmitsFilter implements IScannerFilter {
    private static final Logger LOGGER = LoggerFactory.getLogger(TransmitsFilter.class);

    private List<Class> classList = new FastList<>();

    @Override
    public boolean filter(String urlStr, URL url, ClassLoader classLoader) {
        String classPathStr = urlStr.replace("/", ".");
        classPathStr = classPathStr.substring(0, classPathStr.length() - 6);
        try {
            Class clazz = classLoader.loadClass(classPathStr);
            if (TransmitsService.class.isAssignableFrom(clazz) ) {
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
