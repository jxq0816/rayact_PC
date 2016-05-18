package com.bra.modules.server;

import com.bra.common.config.Global;

/**
 * Created by lenovo on 2016/5/18.
 */
public class ServerConfig {
    static public String ip= Global.getConfig("server.ip");
    static public String port=Global.getConfig("server.port");
}
