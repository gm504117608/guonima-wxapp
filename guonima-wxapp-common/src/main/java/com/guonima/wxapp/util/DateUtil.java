package com.guonima.wxapp.util;

import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期工具类
 *
 * @author guonima
 * @create 2017-04-11 14:29
 */
public class DateUtil {

    /**
     * 年月日时分秒(无下划线) yyyyMMddHHmmss
     */
    public static final String FULL_NOT_LINE_DATE = "yyyyMMddHHmmss";
    /**
     * 年月日(无下划线) yyyyMMdd
     */
    public static final String SHORT_NOT_LINE_DATE = "yyyyMMdd";
    /**
     * 完整时间 yyyy-MM-dd HH:mm:ss
     */
    public static final String FULL_DATE = "yyyy-MM-dd HH:mm:ss";
    /**
     * 简短时间 yyyy-MM-dd
     */
    public static final String SHORT_DATE = "yyyy-MM-dd";

    /**
     * 解析String类型的date为Date型
     *
     * @param strDate string日期
     * @return
     */
    public static Date parse(String strDate) {
        return parse(strDate, FULL_DATE);
    }

    /**
     * 解析String类型的date为Date型
     *
     * @param strDate string日期
     * @param pattern 格式化类型
     * @return
     */
    public static Date parse(String strDate, String pattern) {
        if (StringUtils.isEmpty(strDate)) {
            return null;
        }
        if (StringUtils.isEmpty(pattern)) {
            pattern = FULL_DATE;
        }
        try {
            return new SimpleDateFormat(pattern).parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 将date型日期转为String日期
     *
     * @param date
     * @param pattern
     * @return
     */
    public static String format(Date date, String pattern) {
        if (date == null) {
            return null;
        }
        if (StringUtils.isEmpty(pattern)) {
            pattern = FULL_DATE;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(date);
    }

    /**
     * 将date型日期转为String日期
     *
     * @param date
     * @return
     */
    public static String format(Date date) {
        return format(date, FULL_DATE);
    }

    /**
     * 计算两个时间的间隔 秒数/分钟数/小时数/天数/月数/年数
     *
     * @param d1  开始时间
     * @param d2  结束时间
     * @param str 计算类型标志
     * @return
     */
    public static long datesOfTime(Date d1, Date d2, String str) {
        if (StringUtils.isEmpty(str)) {
            str = "second";
        }
        if (null == d1 || d2 == null) {
            throw new NullPointerException("传入的日期不能为空");
        }
        long t1 = d1.getTime();
        long t2 = d2.getTime();
        long result = 0L;
        switch (str) {
            case "second":
                result = Math.abs((t1 - t2) / 1000);
                break;
            case "minute":
                result = Math.abs((t1 - t2) / 1000 / 60);
                break;
            case "hour":
                result = Math.abs((t1 - t2) / 1000 / 60 / 60);
                break;
            case "day":
                result = Math.abs((t1 - t2) / 1000 / 60 / 60 / 24);
                break;
            case "month":
                result = Math.abs((t1 - t2) / 1000 / 60 / 60 / 24 / 12);
                break;
            case "year":
                result = Math.abs((t1 - t2) / 1000 / 60 / 60 / 24 / 12 / 365);
                break;
            default:
                result = 0L;
                break;
        }
        return result;
    }
}
