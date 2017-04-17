package com.guonima.wxapp.config;

import org.apache.log4j.Logger;

import java.io.Serializable;
import java.util.ResourceBundle;

/**
 * 系统使用的一些公共配置文件，在启动项目的时候加载配置文件（sysconfig.properties）信息到Environment类中
 * 提供整个项目使用
 */
public class Environment implements Serializable {

    private static final Logger log = Logger.getLogger(Environment.class);

    /**
     * 微信获取openid URL地址
     */
    public static String WX_CODE_URL;

    /**
     * 小程序唯一标识
     */
    public static String WX_APPID;

    /**
     * 小程序的密码
     */
    public static String WX_SECRET;

    static {
        //读取属性文件 sysconfig.properties

        ResourceBundle resourceBundle = ResourceBundle.getBundle("sysconfig");
        WX_CODE_URL = resourceBundle.getString("wx.code.url");
        WX_APPID = resourceBundle.getString("wx.appid");
        WX_SECRET = resourceBundle.getString("wx.secret");
    }

    public Environment() {
    }
}

