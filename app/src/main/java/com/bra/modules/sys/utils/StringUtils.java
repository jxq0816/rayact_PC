package com.bra.modules.sys.utils;

import com.bra.common.utils.MD5Util;

/**
 * Created by DDT on 2016/5/9.
 */
public class StringUtils {
    public static String CURRENT_VERSION = "1.4";
    public  static String OFFICECOZE = "coze";
    //public static String ROOTPATH = "http://192.168.3.199:8080/app";
    public static String ROOTPATH = "http://101.200.148.75:8081/app";
    public static String ATTPATH = ROOTPATH + "/mechanism/file/image/";

    public static boolean isNull(String word){
        if(word != null)
            word = word.trim();
        if("".equals(word)||word == null){
            return true;
        }else{
            return false;
        }
    }

    /**
     * 生成安全的密码，生成随机的16位salt并经过1024次 sha-1 hash
     */
    public static String entryptPassword(String plainPassword) {
        return MD5Util.getMD5String(plainPassword);
    }
}
