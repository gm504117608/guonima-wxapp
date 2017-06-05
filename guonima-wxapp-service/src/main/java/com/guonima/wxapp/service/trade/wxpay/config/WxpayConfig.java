package com.guonima.wxapp.service.trade.wxpay.config;


import java.util.ResourceBundle;

/**
 * 微信支付配置信息
 *
 * @author guonima
 * @create 2017-04-17 14:49
 */
public class WxpayConfig {

    static {
        //读取属性文件 wxpay.properties
        ResourceBundle resourceBundle = ResourceBundle.getBundle("wxpay");
        APP_ID = resourceBundle.getString("wx.pay.appid");
        MCH_ID = resourceBundle.getString("wx.pay.mchid");
        NOTIFY_URL = resourceBundle.getString("wx.pay.notify.url");
        UNIFIED_ORDER_URL = resourceBundle.getString("wx.pay.unified.order.url");
        REFUND_URL = resourceBundle.getString("wx.pay.refund.url");
        KEY = resourceBundle.getString("wx.pay.key");
        TIMEOUT_EXPRESS = Integer.valueOf(resourceBundle.getString("wx.pay.timeout.express"));
    }

    /**
     * 微信开放平台审核通过的应用APPID
     */
    public static String APP_ID;
    /**
     * 微信支付分配的商户号
     */
    public static String MCH_ID;
    /**
     * 接收微信支付异步通知回调地址，通知url必须为直接可访问的url，不能携带参数。
     */
    public static String NOTIFY_URL;
    /**
     * 统一下单地址（向微信服务器获取预支付交易单号）
     */
    public static String UNIFIED_ORDER_URL;
    /**
     * 微信申请退款地址
     */
    public static String REFUND_URL;
    /**
     * 微信商户平台API安全的密钥
     * key设置路径： 微信商户平台(pay.weixin.qq.com)-->账户设置-->API安全-->密钥设置
     */
    public static String KEY;
    /**
     * 设置未付款微信交易的超时时间，单位分钟，当前时间加上设置的分钟数
     */
    public static int TIMEOUT_EXPRESS;

}
