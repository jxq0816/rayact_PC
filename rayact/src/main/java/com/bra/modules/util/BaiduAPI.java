package com.bra.modules.util;

/**
 * Created by lenovo on 2016/5/19.
 */

import java.text.DecimalFormat;
public class BaiduAPI {
    /**
     * 计算两点之间距离
     * @param
     * @param
     * @return 米
     */
    public static String getDistance(Double longitude,Double latitude,Double addressX, Double addressY){
        double lon1 = (Math.PI/180)*longitude;
        double lon2 = (Math.PI/180)*addressX;//经度

        double lat1 = (Math.PI/180)*latitude;
        double lat2 = (Math.PI/180)*addressY;//维度

        //地球半径
        double R = 6371;

        //两点间距离 km，如果想要米的话，结果*1000就可以了
        double d =  Math.acos(Math.sin(lat1)*Math.sin(lat2)+Math.cos(lat1)*Math.cos(lat2)*Math.cos(lon2-lon1))*R;
/*        d=d*1000;*/
        DecimalFormat df=new DecimalFormat("0.00");
        return  df.format(d);
    }
}

