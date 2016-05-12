package com.bra.common.upload;

/**
 * 文件存储类型
 *
 * @author xiaobin
 *
 */
public enum StoreType {

    /**
     * 返回项目路径
     */
    FOLDER {
        @Override
        public int getValue() {
            return 1;
        }

        @Override
        public String getName() {
            return "Folder";
        }
    },

    /**
     * 返回系统路径,本系统默认
     */
    SYSTEM {

        @Override
        public int getValue() {
            return 2;
        }

        @Override
        public String getName() {
            return "System";
        }

    },

    /**
     * FileNet存储
     */
    FILEET {

        @Override
        public int getValue() {
            return 3;
        }

        @Override
        public String getName() {
            return "FileNet";
        }

    };

    public abstract int getValue();

    public abstract String getName();
}