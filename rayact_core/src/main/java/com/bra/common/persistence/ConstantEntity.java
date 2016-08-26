/**
 * Copyright &copy; 2012-2014 <a href="https://github.com.bra.>JeeSite</a> All rights reserved.
 */
package com.bra.common.persistence;

/**
 * Entity支持类
 *
 * @version 2014-05-16
 */
public abstract class ConstantEntity{
    /**
     * 删除标记（0：正常；1：删除；2：审核；）
     */
    public static final String DEL_FLAG_NORMAL = "0";
    public static final String DEL_FLAG_DELETE = "1";
    public static final String DEL_FLAG_AUDIT = "2";


    public static final String YES = "1";
    public static final String NO = "0";
}
