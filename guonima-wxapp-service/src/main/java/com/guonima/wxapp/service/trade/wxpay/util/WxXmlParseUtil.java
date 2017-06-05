package com.guonima.wxapp.service.trade.wxpay.util;

import org.apache.commons.collections.MapUtils;

import java.util.SortedMap;

/**
 * @author : guonima
 * @create : 2017-05-20-15:48
 */
public class WxXmlParseUtil {


    /**
     * 获取微信统一下单所需要的xml格式参数
     *
     * @param param 微信统一下单参数集合
     * @return
     */
    public static String getUnifiedOrderParam(SortedMap<String, String> param) {
        if (MapUtils.isEmpty(param)) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("<xml>");

        sb.append("<appid><![CDATA[");
        sb.append(param.get("appid"));
        sb.append("]]></appid>");

        sb.append("<mch_id><![CDATA[");
        sb.append(param.get("mch_id"));
        sb.append("]]></mch_id>");

        sb.append("<device_info><![CDATA[");
        sb.append(param.get("device_info"));
        sb.append("]]></device_info>");

        sb.append("<nonce_str><![CDATA[");
        sb.append(param.get("nonce_str"));
        sb.append("]]></nonce_str>");

        sb.append("<sign><![CDATA[");
        sb.append(param.get("sign"));
        sb.append("]]></sign>");

        sb.append("<sign_type><![CDATA[");
        sb.append(param.get("sign_type"));
        sb.append("]]></sign_type>");

        sb.append("<body><![CDATA[");
        sb.append(param.get("body"));
        sb.append("]]></body>");

        sb.append("<detail><![CDATA[");
        sb.append(param.get("detail"));
        sb.append("]]></detail>");

        sb.append("<attach><![CDATA[");
        sb.append(param.get("attach"));
        sb.append("]]></attach>");

        sb.append("<out_trade_no><![CDATA[");
        sb.append(param.get("out_trade_no"));
        sb.append("]]></out_trade_no>");

        sb.append("<fee_type><![CDATA[");
        sb.append(param.get("fee_type"));
        sb.append("]]></fee_type>");

        sb.append("<total_fee><![CDATA[");
        sb.append(param.get("total_fee"));
        sb.append("]]></total_fee>");

        sb.append("<spbill_create_ip><![CDATA[");
        sb.append(param.get("spbill_create_ip"));
        sb.append("]]></spbill_create_ip>");

        sb.append("<time_start><![CDATA[");
        sb.append(param.get("time_start"));
        sb.append("]]></time_start>");

        sb.append("<time_expire><![CDATA[");
        sb.append(param.get("time_expire"));
        sb.append("]]></time_expire>");

        sb.append("<goods_tag><![CDATA[");
        sb.append(param.get("goods_tag"));
        sb.append("]]></goods_tag>");

        sb.append("<notify_url><![CDATA[");
        sb.append(param.get("notify_url"));
        sb.append("]]></notify_url>");

        sb.append("<trade_type><![CDATA[");
        sb.append(param.get("trade_type"));
        sb.append("]]></trade_type>");

        sb.append("<limit_pay><![CDATA[");
        sb.append(param.get("limit_pay"));
        sb.append("]]></limit_pay>");

        sb.append("<openid><![CDATA[");
        sb.append(param.get("openid"));
        sb.append("]]></openid>");

        sb.append("</xml>");

        return sb.toString();

    }

}
