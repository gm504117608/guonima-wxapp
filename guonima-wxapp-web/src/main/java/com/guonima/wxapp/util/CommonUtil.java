package com.guonima.wxapp.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 一些公共的工具方法集合类
 *
 * @author guonima
 * @create 2017-04-06 10:34
 */
public class CommonUtil {
    private CommonUtil() {
    }

    /*
     * 电话格式验证
     */
    private static final String PHONE_CALL_PATTERN = "^(\\(\\d{3,4}\\)|\\d{3,4}-)?\\d{7,8}(-\\d{1,4})?$";

    /*
     * 中国电信号码格式验证 手机段： 133,153,180,181,189,177,1700
     */
    private static final String CHINA_TELECOM_PATTERN = "(^1(33|53|77|8[019])\\d{8}$)|(^1700\\d{7}$)";

    /*
     * 中国联通号码格式验证 手机段：130,131,132,155,156,185,186,145,176,1709
     */
    private static final String CHINA_UNICOM_PATTERN = "(^1(3[0-2]|4[5]|5[56]|7[6]|8[56])\\d{8}$)|(^1709\\d{7}$)";

    /*
     * 中国移动号码格式验证
     * 手机段：134,135,136,137,138,139,150,151,152,157,158,159,182,183,184
     * ,187,188,147,178,1705
     */
    private static final String CHINA_MOBILE_PATTERN = "(^1(3[4-9]|4[7]|5[0-27-9]|7[8]|8[2-478])\\d{8}$)|(^1705\\d{7}$)";

    /**
     * 验证电话号码的格式
     *
     * @param str 校验电话字符串
     * @return 返回正确 true, 否则为 false
     */
    public static boolean isPhoneCallNum(String str) {

        return str == null || str.trim().equals("") ? false : match(
                PHONE_CALL_PATTERN, str);
    }

    /**
     * 验证【电信】手机号码的格式
     *
     * @param str 校验手机字符串
     * @return 返回true, 否则为false
     */
    public static boolean isChinaTelecomPhoneNum(String str) {

        return str == null || str.trim().equals("") ? false : match(
                CHINA_TELECOM_PATTERN, str);
    }

    /**
     * 验证【联通】手机号码的格式
     *
     * @param str 校验手机字符串
     * @return 返回true, 否则为false
     */
    public static boolean isChinaUnicomPhoneNum(String str) {

        return str == null || str.trim().equals("") ? false : match(
                CHINA_UNICOM_PATTERN, str);
    }

    /**
     * 验证【移动】手机号码的格式
     *
     * @param str 校验手机字符串
     * @return 返回true, 否则为false
     */
    public static boolean isChinaMobilePhoneNum(String str) {

        return str == null || str.trim().equals("") ? false : match(
                CHINA_MOBILE_PATTERN, str);
    }


    /**
     * 验证手机和电话号码的格式
     *
     * @param str 校验手机字符串
     * @return 返回true, 否则为false
     */
    public static boolean isPhoneNum(String str) {
        // 如果字符串为空，直接返回false
        if (str == null || str.trim().equals("")) {
            return false;
        } else {
            int comma = str.indexOf(",");// 是否含有逗号
            int caesuraSign = str.indexOf("、");// 是否含有顿号
            int space = str.trim().indexOf(" ");// 是否含有空格
            if (comma == -1 && caesuraSign == -1 && space == -1) {
                // 如果号码不含分隔符,直接验证
                str = str.trim();
                return (isPhoneCallNum(str) || isChinaTelecomPhoneNum(str)
                        || isChinaUnicomPhoneNum(str) || isChinaMobilePhoneNum(str)) ? true
                        : false;
            } else {
                // 号码含分隔符,先把分隔符统一处理为英文状态下的逗号
                if (caesuraSign != -1) {
                    str = str.replaceAll("、", ",");
                }
                if (space != -1) {
                    str = str.replaceAll(" ", ",");
                }

                String[] phoneNumArr = str.split(",");
                //遍历验证
                for (String temp : phoneNumArr) {
                    temp = temp.trim();
                    if (isPhoneCallNum(temp) || isChinaTelecomPhoneNum(temp)
                            || isChinaUnicomPhoneNum(temp)
                            || isChinaMobilePhoneNum(temp)) {
                        continue;
                    } else {
                        return false;
                    }
                }
                return true;
            }

        }

    }

    /**
     * 执行正则表达式
     *
     * @param pat 表达式
     * @param str 待验证字符串
     * @return 返回true, 否则为false
     */
    private static boolean match(String pat, String str) {
        Pattern pattern = Pattern.compile(pat);
        Matcher match = pattern.matcher(str);
        return match.find();
    }

    /**
     * 校验给定字符的长度在指定的范围内;只校验字符串String、int型、、
     *
     * @param obj    待校验对象
     * @param minLen 最小长度
     * @param maxLen 最大长度
     * @return
     */
    public static boolean checkLength(Object obj, int minLen, int maxLen) {
        if (null == obj) {
            return false;
        }
        if (minLen > maxLen) {
            return false;
        }
        if (obj instanceof String) {
            int len = ((String) obj).length();
            if (len >= minLen && len <= maxLen) {
                return true;
            }
            return false;
        }
        if (obj instanceof Integer) {
            int len = (String.valueOf((Integer) obj)).length();
            if (len >= minLen && len <= maxLen) {
                return true;
            }
            return false;
        }
        return false;
    }

    /**
     * 验证邮箱
     *
     * @param email
     * @return
     */
    public static boolean checkEmail(String email) {
        boolean flag = false;
        try {
            String check = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
            Pattern regex = Pattern.compile(check);
            Matcher matcher = regex.matcher(email);
            flag = matcher.matches();
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }

    public static void main(String[] args) {

        System.out.println(isPhoneNum("17750581369"));
        System.out.println(isPhoneNum("13306061248"));
        System.out.println(isPhoneNum("17750581369,13306061248"));
        System.out.println(isPhoneNum("17750581369 13306061248"));
        System.out.println(isPhoneNum("17750581369、13306061248"));
        System.out.println(isPhoneNum("0596-3370653"));
        System.out.println(isPhoneNum("1100000000"));

    }

}
