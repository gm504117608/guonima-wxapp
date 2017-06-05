package com.guonima.wxapp.service.trade.wxpay.util;

import com.guonima.wxapp.service.trade.wxpay.config.WxpayConfig;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;

/**
 * 微信支付签名
 */
public class WXSignUtils {
    /**
     * 微信支付签名算法sign
     *
     * @param characterEncoding
     * @param parameters
     * @return
     */
    public static String createSign(String characterEncoding, SortedMap<String, String> parameters) {
        StringBuffer sb = new StringBuffer();
        /**
         * 所有参与传参的参数按照accsii排序（升序）
         * 排序是为了和回调函数里面的签名做比对，查看是否是一致
         */
        Set es = parameters.entrySet();
        Iterator it = es.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String k = (String) entry.getKey();
            Object v = entry.getValue();
            if (null != v && !"".equals(v)
                    && !"sign".equals(k) && !"key".equals(k)) {
                sb.append(k + "=" + v + "&");
            }
        }
        sb.append("key=" + WxpayConfig.KEY);
        String sign = MD5Utils.MD5Encode(sb.toString(), characterEncoding).toUpperCase();
        return sign;
    }


}
