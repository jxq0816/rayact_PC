package com.bra.plugin.migration;

import com.bra.common.config.Global;
import com.bra.common.plugin.IPlugin;
import com.bra.common.scanner.FileScanner;
import com.bra.plugin.migration.service.TransmitsService;
import com.bra.plugin.migration.service.filter.TransmitsFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by xiaobin on 16/2/16.
 */
public class MigrationPlugin implements IPlugin {

    private static final Logger LOG = LoggerFactory.getLogger(MigrationPlugin.class);

    public static Map<String,TransmitsService> CACHE_TRANSMITS = new ConcurrentHashMap<>();

    @Override
    public void init() {
        Global.putProperties("migration", "properties/migration.properties");
        TransmitsFilter transmitsFilter = new TransmitsFilter();
        FileScanner fileScanner = new FileScanner(transmitsFilter);
        List<Class> entityClassList = transmitsFilter.getClassList();
        fileScanner.find(Global.getConfig("migration.service.package"));
        for (Class clazz : entityClassList) {
            try {
                TransmitsService plugin = (TransmitsService) clazz.newInstance();
                CACHE_TRANSMITS.put(plugin.getName(),plugin);
            } catch (InstantiationException e) {
                LOG.error("实例化移动端接口报错：" + e.getLocalizedMessage());
            } catch (IllegalAccessException e) {
                LOG.error("实例化移动端报错：" + e.getLocalizedMessage());
            }
        }
    }

    @Override
    public void destroyed() {
        CACHE_TRANSMITS.clear();
        CACHE_TRANSMITS = null;
    }

    @Override
    public String getKey() {
        return "migration";
    }
}
