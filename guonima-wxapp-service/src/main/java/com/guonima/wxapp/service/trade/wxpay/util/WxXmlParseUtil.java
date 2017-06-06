package com.guonima.wxapp.service.trade.wxpay.util;

import com.guonima.wxapp.util.XmlParserUtil;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.SortedMap;
import java.util.TreeMap;

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

        if (StringUtils.isNotEmpty(param.get("appid"))) {
            sb.append("<appid><![CDATA[");
            sb.append(param.get("appid"));
            sb.append("]]></appid>");
        }
        if (StringUtils.isNotEmpty(param.get("mch_id"))) {
            sb.append("<mch_id><![CDATA[");
            sb.append(param.get("mch_id"));
            sb.append("]]></mch_id>");
        }
        if (StringUtils.isNotEmpty(param.get("device_info"))) {
            sb.append("<device_info><![CDATA[");
            sb.append(param.get("device_info"));
            sb.append("]]></device_info>");
        }
        if (StringUtils.isNotEmpty(param.get("nonce_str"))) {
            sb.append("<nonce_str><![CDATA[");
            sb.append(param.get("nonce_str"));
            sb.append("]]></nonce_str>");
        }
        if (StringUtils.isNotEmpty(param.get("sign"))) {
            sb.append("<sign><![CDATA[");
            sb.append(param.get("sign"));
            sb.append("]]></sign>");
        }
        if (StringUtils.isNotEmpty(param.get("sign_type"))) {
            sb.append("<sign_type><![CDATA[");
            sb.append(param.get("sign_type"));
            sb.append("]]></sign_type>");
        }
        if (StringUtils.isNotEmpty(param.get("body"))) {
            sb.append("<body><![CDATA[");
            sb.append(param.get("body"));
            sb.append("]]></body>");
        }
        if (StringUtils.isNotEmpty(param.get("detail"))) {
            sb.append("<detail><![CDATA[");
            sb.append(param.get("detail"));
            sb.append("]]></detail>");
        }
        if (StringUtils.isNotEmpty(param.get("attach"))) {
            sb.append("<attach><![CDATA[");
            sb.append(param.get("attach"));
            sb.append("]]></attach>");
        }
        if (StringUtils.isNotEmpty(param.get("out_trade_no"))) {
            sb.append("<out_trade_no><![CDATA[");
            sb.append(param.get("out_trade_no"));
            sb.append("]]></out_trade_no>");
        }
        if (StringUtils.isNotEmpty(param.get("fee_type"))) {
            sb.append("<fee_type><![CDATA[");
            sb.append(param.get("fee_type"));
            sb.append("]]></fee_type>");
        }
        if (StringUtils.isNotEmpty(param.get("total_fee"))) {
            sb.append("<total_fee><![CDATA[");
            sb.append(param.get("total_fee"));
            sb.append("]]></total_fee>");
        }
        if (StringUtils.isNotEmpty(param.get("spbill_create_ip"))) {
            sb.append("<spbill_create_ip><![CDATA[");
            sb.append(param.get("spbill_create_ip"));
            sb.append("]]></spbill_create_ip>");
        }
        if (StringUtils.isNotEmpty(param.get("time_start"))) {
            sb.append("<time_start><![CDATA[");
            sb.append(param.get("time_start"));
            sb.append("]]></time_start>");
        }
        if (StringUtils.isNotEmpty(param.get("time_expire"))) {
            sb.append("<time_expire><![CDATA[");
            sb.append(param.get("time_expire"));
            sb.append("]]></time_expire>");
        }
        if (StringUtils.isNotEmpty(param.get("goods_tag"))) {
            sb.append("<goods_tag><![CDATA[");
            sb.append(param.get("goods_tag"));
            sb.append("]]></goods_tag>");
        }
        if (StringUtils.isNotEmpty(param.get("notify_url"))) {
            sb.append("<notify_url><![CDATA[");
            sb.append(param.get("notify_url"));
            sb.append("]]></notify_url>");
        }
        if (StringUtils.isNotEmpty(param.get("trade_type"))) {
            sb.append("<trade_type><![CDATA[");
            sb.append(param.get("trade_type"));
            sb.append("]]></trade_type>");
        }
        if (StringUtils.isNotEmpty(param.get("limit_pay"))) {
            sb.append("<limit_pay><![CDATA[");
            sb.append(param.get("limit_pay"));
            sb.append("]]></limit_pay>");
        }
        if (StringUtils.isNotEmpty(param.get("openid"))) {
            sb.append("<openid><![CDATA[");
            sb.append(param.get("openid"));
            sb.append("]]></openid>");
        }
        sb.append("</xml>");

        return sb.toString();
    }


    /**
     * DOM解析xml字符串 ，微信支付解析返回结果
     *
     * @param xml xml字符串
     */
    public static SortedMap<String, String> getCallbackParam(String xml) {
        if (StringUtils.isEmpty(xml)) {
            return null;
        }
        SortedMap<String, String> result = new TreeMap<String, String>();
        result.put("return_code", XmlParserUtil.getNodeValue(xml, "//xml/return_code")); // 返回状态码
        result.put("return_msg", XmlParserUtil.getNodeValue(xml, "//xml/return_msg")); // 返回信息
        result.put("result_code", XmlParserUtil.getNodeValue(xml, "//xml/result_code")); // 业务结果
        result.put("err_code", XmlParserUtil.getNodeValue(xml, "//xml/err_code")); // 错误代码
        result.put("err_code_des", XmlParserUtil.getNodeValue(xml, "//xml/err_code_des")); // 错误代码描述
        result.put("appid", XmlParserUtil.getNodeValue(xml, "//xml/appid")); // 公众账号ID
        result.put("mch_id", XmlParserUtil.getNodeValue(xml, "//xml/mch_id")); // 商户号
        result.put("nonce_str", XmlParserUtil.getNodeValue(xml, "//xml/nonce_str")); // 随机字符串
        result.put("trade_type", XmlParserUtil.getNodeValue(xml, "//xml/trade_type")); // 交易类型	JSAPI、NATIVE、APP
        result.put("fee_type", XmlParserUtil.getNodeValue(xml, "//xml/fee_type")); // 货币种类
        result.put("cash_fee", XmlParserUtil.getNodeValue(xml, "//xml/cash_fee")); // 现金支付金额
        result.put("cash_fee_type", XmlParserUtil.getNodeValue(xml, "//xml/cash_fee_type")); // 现金支付货币类型
        result.put("total_fee", XmlParserUtil.getNodeValue(xml, "//xml/total_fee")); // 订单金额，单位为分
        result.put("attach", XmlParserUtil.getNodeValue(xml, "//xml/attach")); // 商家数据包
        result.put("out_trade_no", XmlParserUtil.getNodeValue(xml, "//xml/out_trade_no")); // 自己系统订单号
        result.put("transaction_id", XmlParserUtil.getNodeValue(xml, "//xml/transaction_id")); // 微信支付流水号
        result.put("sign", XmlParserUtil.getNodeValue(xml, "//xml/sign")); // 签名
        result.put("sign_type", XmlParserUtil.getNodeValue(xml, "//xml/sign_type")); // 签名类型
        result.put("openid", XmlParserUtil.getNodeValue(xml, "//xml/openid")); // 用户标识
        result.put("is_subscribe", XmlParserUtil.getNodeValue(xml, "//xml/is_subscribe")); // 是否关注公众账号
        result.put("bank_type", XmlParserUtil.getNodeValue(xml, "//xml/bank_type")); // 付款银行
        result.put("device_info", XmlParserUtil.getNodeValue(xml, "//xml/device_info")); // 微信支付分配的终端设备号
        result.put("time_end", XmlParserUtil.getNodeValue(xml, "//xml/time_end")); // 支付完成时间

        return result;
    }


    /**
     * 微信返回信息封装
     *
     * @param return_code 返回代码
     * @param return_msg  返回信息
     * @return
     */
    public static String backWeixin(String return_code, String return_msg) {
        StringBuffer bf = new StringBuffer();
        bf.append("<xml>");

        bf.append("<return_code><![CDATA[");
        bf.append(return_code);
        bf.append("]]></return_code>");

        bf.append("<return_msg><![CDATA[");
        bf.append(return_msg);
        bf.append("]]></return_msg>");

        bf.append("</xml>");
        return bf.toString();
    }
}
