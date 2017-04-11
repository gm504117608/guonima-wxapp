package com.guonima.wxapp.config;

import org.apache.log4j.Logger;

import java.io.Serializable;
import java.util.ResourceBundle;

/**
 * 系统使用的一些公共配置文件，在启动项目的时候加载配置文件（sysconfig.properties）信息到Environment类中
 * 提供整个项目使用
 */
public class Environment implements Serializable {

    private static final Logger LOGGER = Logger.getLogger(Environment.class);

    /**
     * url pms group数据获取URL
     */
    public static String PMS_GROUP_URL;

    /**
     * url pms 会员数据获取URL
     */
    public static String PMS_MEMBER_URL;

    /**
     * 百度地图ak的值
     */
    public static String BAIDU_AK;
    /**
     * 高德地图key的值
     */
    public static String GAODE_KEY;

    /**
     * 地图定位解析地址失败之后默认查询的城市
     */
    public static String LOCATION_CITY_DESCRIPTION;

    /**
     * 应用密钥，支付网关分配
     */
    public static String PAY_APP_KEY;
    /**
     * 支付网关的地址
     */
    public static String PAY_URL;

    static {
        //读取属性文件 sysconfig.properties
        ResourceBundle resourceBundle = ResourceBundle.getBundle("sysconfig");
        PMS_GROUP_URL = resourceBundle.getString("pms.group.url");
        PMS_MEMBER_URL = resourceBundle.getString("pms.member.url");
        BAIDU_AK = resourceBundle.getString("baiDu.ak");
        GAODE_KEY = resourceBundle.getString("gaoDe.key");
        LOCATION_CITY_DESCRIPTION = resourceBundle.getString("location.city.description");
        PAY_APP_KEY = resourceBundle.getString("pay.app.key");
        PAY_URL = resourceBundle.getString("pay.url");
    }

    public Environment() {
    }
}

