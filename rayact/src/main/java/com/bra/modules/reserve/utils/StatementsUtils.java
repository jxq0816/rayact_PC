package com.bra.modules.reserve.utils;

/**
 * Created by jiangxingqi on 16/1/20.
 */
public class StatementsUtils {
    public static String getTransactionType(String transactionType){
        if ("1".equals(transactionType)) {
            return "储值卡充值";
        } else if ("2".equals(transactionType)) {
            return "退费";
        } else if ("33".equals(transactionType)) {
            return "商品";
        } else if ("3".equals(transactionType)) {
            return "商品";
        } else if ("4".equals(transactionType)) {
            return "超级管理员修改余额";
        } else if ("5".equals(transactionType)) {
            return "销户退还用户的金额";
        } else if ("6".equals(transactionType)) {
            return "销户违约金";
        }else if ("7".equals(transactionType)) {
            return "次卡充值";
        }else if ("8".equals(transactionType)) {
            return "包场消费";
        }else if ("9".equals(transactionType)) {
            return "场次票";
        }else if ("10".equals(transactionType)) {
            return "教练费用";
        }
        return "";
    }
}
