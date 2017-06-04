package com.guonima.wxapp.service;

import com.guonima.wxapp.config.WxPaymentConfig;
import com.guonima.wxapp.dao.DaoSupport;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * @author : guonima
 * @create : 2017-05-20-13:53
 */
@Service("paymentServiceImpl")
public class PaymentServiceImpl implements PaymentService {

    private final static Logger log = Logger.getLogger(PaymentServiceImpl.class);

    @Resource(name = "daoSupport")
    private DaoSupport dao;

    @Override
    public void unifiedOrder(){

        SortedMap<String, String> param = new TreeMap<String, String>();
        param.put("appid", WxPaymentConfig.WX_PAY_APPID); // 微信分配的小程序ID
        param.put("mch_id", WxPaymentConfig.WX_PAY_MCHID); // 微信支付分配的商户号
        param.put("device_info", ""); // 终端设备号
        param.put("nonce_str", RandomStringUtils.randomAlphanumeric(28)); // 随机字符串
        param.put("sign", ""); // 签名
        param.put("sign_type", "MD5"); // 签名类型
        param.put("body", ""); // 商品描述
        param.put("detail", ""); // 商品详情
        param.put("attach", ""); // 附加数据
        param.put("out_trade_no", ""); // 商户订单号
        param.put("fee_type", ""); // 货币类型
        param.put("total_fee", ""); // 总金额 （单位 分）
        param.put("spbill_create_ip", ""); // 终端IP
        param.put("time_start", ""); // 交易起始时间
        param.put("time_expire", ""); // 交易结束时间





//        		否	String(14)	20091225091010	订单生成时间，格式为yyyyMMddHHmmss，如2009年12月25日9点10分10秒表示为20091225091010。其他详见时间规则
//        		否	String(14)	20091227091010
//        订单失效时间，格式为yyyyMMddHHmmss，如2009年12月27日9点10分10秒表示为20091227091010。其他详见时间规则
//        注意：最短失效时间间隔必须大于5分钟
//        商品标记	goods_tag	否	String(32)	WXG	商品标记，代金券或立减优惠功能的参数，说明详见代金券或立减优惠
//        通知地址	notify_url	是	String(256)	http://www.weixin.qq.com/wxpay/pay.php	接收微信支付异步通知回调地址，通知url必须为直接可访问的url，不能携带参数。
//        交易类型	trade_type	是	String(16)	JSAPI	小程序取值如下：JSAPI，详细说明见参数规定
//        指定支付方式	limit_pay	否	String(32)	no_credit	no_credit--指定不能使用信用卡支付
//        用户标识	openid	否	String(128)	oUpF8uMuAJO_M2pxb1Q9zNjWeS6o	trade_type=JSAPI，此参数必传，用户在商户appid下的唯一标识。openid如何获取，可参考【获取openid】。

    }

}
