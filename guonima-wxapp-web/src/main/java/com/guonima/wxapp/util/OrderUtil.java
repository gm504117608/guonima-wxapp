package com.guonima.wxapp.util;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 订单相关工具类
 */
public class OrderUtil {
    private OrderUtil() {
    }

    /**
     * 生成订单号
     * 规则 ： 000【标志位预留以后使用，比如分表分库等】 + 日期（年月日时分秒毫秒） +
     * System.currentTimeMillis()中的5位 +
     * System.nanoTime()时间纳秒中5位
     *
     * @return 订单号
     */
    public static String createOrderNo() {
        StringBuilder orderNo = new StringBuilder("000");
        // jdk1.8的时间类是线程安全的
        orderNo.append(ZonedDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS")));
        orderNo.append((System.currentTimeMillis() / 1000 + "").substring(5));
        orderNo.append((System.nanoTime() + "").substring(6).substring(0, 5));
        return orderNo.toString();
    }

    public static void main(String[] args) {
        System.out.println(createOrderNo());
    }

}
