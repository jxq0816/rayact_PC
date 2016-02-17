package com.bra.plugin.migration.service;

import com.bra.plugin.migration.entity.MobileHead;

import java.util.Map;

/**
 * Created by xiaobin on 16/2/16.
 */
public interface TransmitsService {

    String executeTodo(MobileHead mobileHead, Map<String,Object> map) ;

    String getName();
}
