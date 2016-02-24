package com.bra.modules.reserve.utils;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by dell on 2016/2/23.
 */
public class GpsUtils {
    private static double EARTH_RADIUS = 6378.137;

    private static double rad(double d) {
        return d * Math.PI / 180.0;
    }

    /**
     * 计算两点之间距离
     * @param _lat1 - start纬度
     * @param _lon1 - start经度
     * @param _lat2 - end纬度
     * @param _lon2 - end经度
     * @return km(四舍五入)
     */
    public static double getDistance(double _lat1,double _lon1, double _lat2,double _lon2){
        double lat1 = (Math.PI/180)*_lat1;
        double lat2 = (Math.PI/180)*_lat2;

        double lon1 = (Math.PI/180)*_lon1;
        double lon2 = (Math.PI/180)*_lon2;

        //地球半径
        double R = 6378.1;

        double d =  Math.acos(Math.sin(lat1)*Math.sin(lat2)+Math.cos(lat1)*Math.cos(lat2)*Math.cos(lon2-lon1))*R;

        return new BigDecimal(d).setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
    }


    /**
     * 根据两个位置的经纬度，来计算两地的距离（单位为KM）
     * 参数为String类型
     * @param lat1Str 用户经度
     * @param lng1Str 用户纬度
     * @param lat2Str 商家经度
     * @param lng2Str 商家纬度
     * @return
     */
    public static String getDistance(String lat1Str, String lng1Str, String lat2Str, String lng2Str) {
        Double lat1 = Double.parseDouble(lat1Str);
        Double lng1 = Double.parseDouble(lng1Str);
        Double lat2 = Double.parseDouble(lat2Str);
        Double lng2 = Double.parseDouble(lng2Str);
        return getDistance(lat1,lng1,lat2,lng2)+"";
    }

    /**
     * 获取当前用户一定距离以内的经纬度值
     * 单位米 return minLat
     * 最小经度 minLng
     * 最小纬度 maxLat
     * 最大经度 maxLng
     * 最大纬度 minLat
     */
    public static Map getAround(String latStr, String lngStr, String raidus) {
        Map map = new HashMap();

        Double latitude = Double.parseDouble(latStr);// 传值给经度
        Double longitude = Double.parseDouble(lngStr);// 传值给纬度

        Double degree = (24901 * 1609) / 360.0; // 获取每度
        double raidusMile = Double.parseDouble(raidus);

        Double mpdLng = Double.parseDouble((degree * Math.cos(latitude * (Math.PI / 180))+"").replace("-", ""));
        Double dpmLng = 1 / mpdLng;
        Double radiusLng = dpmLng * raidusMile;
        //获取最小经度
        Double minLat = longitude - radiusLng;
        // 获取最大经度
        Double maxLat = longitude + radiusLng;

        Double dpmLat = 1 / degree;
        Double radiusLat = dpmLat * raidusMile;
        // 获取最小纬度
        Double minLng = latitude - radiusLat;
        // 获取最大纬度
        Double maxLng = latitude + radiusLat;

        map.put("minLat", minLat+"");
        map.put("maxLat", maxLat+"");
        map.put("minLng", minLng+"");
        map.put("maxLng", maxLng+"");

        return map;
    }

    public static void main(String[] args) {
        //济南国际会展中心经纬度：117.11811  36.68484
        //趵突泉：117.00999000000002  36.66123
        //System.out.println(getDistance("117.11811","36.68484","117.00999000000002","36.66123"));

        //System.out.println(getAround("117.11811", "36.68484", "13000"));
        System.out.println(getDistance(45.73990, 126.55893,45.73876, 126.55037));
        //117.01028712333508(Double), 117.22593287666493(Double),
        //36.44829619896034(Double), 36.92138380103966(Double)

    }
}
