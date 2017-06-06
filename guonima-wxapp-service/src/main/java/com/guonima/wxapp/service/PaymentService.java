package com.guonima.wxapp.service;

import com.guonima.wxapp.Response;

import java.math.BigDecimal;
import java.util.Map;

/**
 * @author : guonima
 * @create : 2017-05-20-13:53
 */
public interface PaymentService {

    /**
     * 获取微信统一下单信息
     *
     * @param orderNo 订单号
     * @param cost    支付金额
     * @param body    备注信息
     * @return
     */
    public Response unifiedOrder(String orderNo, BigDecimal cost, String body);

    /**
     * 订单支付成功回调函数
     *
     * @param param 回调参数
     * @return
     */
    public String wxpayCallback(Map<String, String> param);
}
