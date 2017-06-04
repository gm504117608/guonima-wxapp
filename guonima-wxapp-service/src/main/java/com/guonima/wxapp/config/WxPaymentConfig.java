package com.guonima.wxapp.config;

import java.util.ResourceBundle;

/**
 * @author : guonima
 * @create : 2017-05-20-16:41
 */
public class WxPaymentConfig {


    /**
     * 微信统一下单 URL地址
     */
    public static String UNIFIED_ORDER_URL;

    /**
     * 小程序唯一标识
     */
    public static String WX_PAY_APPID;

    /**
     * 微信支付分配的商户号
     */
    public static String WX_PAY_MCHID;


    static {
        //读取属性文件 wxpay.properties

        ResourceBundle resourceBundle = ResourceBundle.getBundle("wxpay");
        UNIFIED_ORDER_URL = resourceBundle.getString("wx.pay.unified.order.url");
        WX_PAY_APPID = resourceBundle.getString("wx.pay.appid");
        WX_PAY_MCHID = resourceBundle.getString("wx.pay.mchid");
    }

}
