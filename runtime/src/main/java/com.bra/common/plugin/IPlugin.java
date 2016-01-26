package com.bra.common.plugin;

/**
 * Created by xiaobin268 on 2014-9-23.
 * 插件机制
 */
public interface IPlugin {

    /**
     * 插件初始化
     */
    public void init();

    /**
     * 插件注销
     */
    public void destroyed();

    public String getKey();
}
