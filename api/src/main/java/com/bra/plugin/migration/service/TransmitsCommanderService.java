package com.bra.plugin.migration.service;

import com.bra.plugin.migration.MigrationPlugin;
import com.bra.plugin.migration.entity.MobileHead;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 * Created by xiaobin on 16/2/16.
 */
@Service
@Transactional(readOnly = true)
public class TransmitsCommanderService {

    private static final Logger logger = LoggerFactory.getLogger(TransmitsCommanderService.class);

    @Transactional(readOnly = false)
    public String executeTodo(MobileHead mobileHead, Map<String, Object> map) {
        logger.info("requestType="+mobileHead.getRequestType()+",userId="+mobileHead.getUserId());
        if (!MigrationPlugin.CACHE_TRANSMITS.containsKey(mobileHead.getRequestType())) {
            throw new RuntimeException("（" + this.getClass().getName() + "）" + mobileHead.getRequestType() + "未找到。。。。。");
        }
        TransmitsService transmitsService = MigrationPlugin.CACHE_TRANSMITS.get(mobileHead.getRequestType());
        if (transmitsService == null) {
            throw new RuntimeException("（" + this.getClass().getName() + "）没找到执行文件");
        }
        return transmitsService.executeTodo(mobileHead, map);
    }
}
