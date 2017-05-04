package com.guonima.wxapp.util.map;

import com.alibaba.fastjson.JSONObject;
import com.guonima.wxapp.config.Environment;
import com.guonima.wxapp.util.HttpUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

/**
 * 高德地图解析工具类
 *
 * @author guonima
 * @create 2017-03-22 17:11
 */
public class GaoDeMapUtil {

    private final static Logger log = Logger.getLogger(BaiDuMapUtil.class);

    /**
     * 通过经纬度解析出来城市名称
     *
     * @param longitude 经度
     * @param latitude  维度
     * @return 地址
     */
    public static String getLocation(String longitude, String latitude) {

        String website = String.format("http://restapi.amap.com/v3/geocode/regeo?key=%s&location=%s&" +
                "output=json&radius=1000&extensions=all", Environment.GAODE_KEY, longitude + "," + latitude);

        String str = HttpUtil.getResponseByGet(website);
        if (str == null) {
            log.error("高德地图解析经纬度 Failed ");
            return str;
        }
        JSONObject mapData = JSONObject.parseObject(str);
        String status = mapData.getString("status"); // 状态码
        String result = null;
        if ("1".equals(status)) { // 请求正常并得到需要结果
            result = mapData.getJSONObject("regeocode").getJSONObject("addressComponent").getString("city");
            if (StringUtils.isNotEmpty(result)) {
                result = result.substring(0, result.length() - 1);
            }
        } else {
            log.error("高德地图解析经纬度高德返回错误 : " + mapData.get("info"));
        }
        return result;
    }

    /**
     * 通过城市名称解析出来经纬度
     *
     * @param address 地址
     * @return 经纬度
     */
    public static Map<String, Double> getLngAndLat(String address) {

        String website = String.format("http://restapi.amap.com/v3/geocode/geo?address=%s" +
                "&key=%s&output=json", address, Environment.GAODE_KEY);

        String str = HttpUtil.getResponseByGet(website);
        if (str == null) {
            log.error("高德地图解析地址 Failed ");
            return null;
        }
        Map<String, Double> map = new HashMap<String, Double>();
        JSONObject obj = JSONObject.parseObject(str);
        if (obj.get("status").toString().equals("1")) {
            String[] temp = obj.getJSONArray("geocodes").getJSONObject(0).getString("location").split(",");
            map.put("lng", Double.parseDouble(temp[0]));
            map.put("lat", Double.parseDouble(temp[1]));
            return map;
        } else {
            return null;
        }
    }

    public static void main(String[] args) {
//        System.out.println(GaoDeMapUtil.getLocation("120.168017", "30.27981"));
//        System.out.println(BaiDuMapUtil.getLocation("104.04701", "30.548397"));
//        System.out.println(BaiDuMapUtil.getLocation("116.410593", "39.912943"));
//        System.out.println(BaiDuMapUtil.getLocation("102.723534", "25.05026"));
//        System.out.println(BaiDuMapUtil.getLocation("101.997692", "24.069254"));

        Map<String, Double> map = GaoDeMapUtil.getLngAndLat("杭州市长河路");
        System.out.println("经度：" + map.get("lng") + "---纬度：" + map.get("lat"));
    }

}
