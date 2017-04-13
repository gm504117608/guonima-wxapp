package com.guonima.wxapp.util;

import java.util.*;

/**
 * @author guonima
 * @create 2017-04-13 15:41
 */
public class SecurityUtil {

    /**
     * 身份验证token值算法：
     * 算法是：将特定的某几个参数一map的数据结构传入，
     * 进行字典序排序以后进行md5加密,32位小写加密；
     *
     * @param srcData 约定用来计算token的参数
     * @return
     */
    public static String createToken(Map<String, Object> srcData) {
        //排序，根据key 的字典序排序
        if (null == srcData) {
            return null;
        }
        List<Map.Entry<String, Object>> list = new ArrayList<Map.Entry<String, Object>>(srcData.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<String, Object>>() {
            //升序排序
            public int compare(Map.Entry<String, Object> o1, Map.Entry<String, Object> o2) {
                return o1.getKey().compareTo(o2.getKey());
            }
        });

        StringBuffer srcSb = new StringBuffer();
        for (Map.Entry<String, Object> srcAtom : list) {
            srcSb.append(String.valueOf(srcAtom.getValue()));
        }
        System.out.println("身份验证加密前字符串：" + srcSb.toString());
        //计算token
        String token = MD5Util.md5(srcSb.toString());
        return token;
    }

    /**
     * 生成token，根据传入的字符串
     *
     * @param str 计算token的字符串
     * @return
     */
    public static String createToken(String str) {
        //计算token
        String token = MD5Util.md5(str);
        return token;
    }
}
