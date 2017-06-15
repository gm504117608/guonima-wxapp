package com.guonima.wxapp.util;

import java.io.IOException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.*;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;


/**
 * http工具类
 */
public class HttpUtil {

    private final static Logger log = Logger.getLogger(HttpUtil.class);

    /**
     * post 调用
     *
     * @param url    url地址
     * @param params 传输参数集合，会取出值对其进行UTF-8编码
     * @return 返回调用成功的值，失败返回null
     */
    public static String getResponseByPost(String url, Map<String, String> params) {

        HttpClient httpclient = new DefaultHttpClient();
        List<NameValuePair> valuePairs = new ArrayList<NameValuePair>(params.size());
        for (Map.Entry<String, String> entry : params.entrySet()) {
            NameValuePair nameValuePair = new BasicNameValuePair(entry.getKey(), String.valueOf(entry.getValue()));
            valuePairs.add(nameValuePair);
        }
        HttpPost httpPost = new HttpPost(url);

        try {
            UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(valuePairs, HTTP.UTF_8);
            httpPost.setEntity(formEntity);
            HttpResponse resp = httpclient.execute(httpPost);
            int statusCode = resp.getStatusLine().getStatusCode();
            log.debug("调用指定url地址返回的状态码 ： " + statusCode);
            if (statusCode == HttpStatus.SC_OK) {
                HttpEntity resEntity = resp.getEntity();
                return EntityUtils.toString(resEntity, HTTP.UTF_8);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                httpclient.getConnectionManager().shutdown();
                httpPost.releaseConnection();
            } catch (Exception ignore) {

            }
        }
        return null;
    }

    /**
     * post 直接通过地址调用
     * 例如： url = sourceHost+"?targetValue="+targetValue+"&k="+sourceKey+"&p="+sourceSecret+"&e="+ext;
     *
     * @param url url地址
     * @return
     */
    public static String getResponseByPost(String url) {
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost(url);

        try {
            HttpResponse response = httpclient.execute(httppost);
            int statusCode = response.getStatusLine().getStatusCode();
            System.out.println("statusCode" + statusCode);
            if (statusCode == HttpStatus.SC_OK) {
                System.out.println("服务器正常响应.....");
                HttpEntity resEntity = response.getEntity();
                return EntityUtils.toString(resEntity, HTTP.UTF_8);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                httpclient.getConnectionManager().shutdown();
                httppost.releaseConnection();
            } catch (Exception ignore) {

            }
        }
        return null;
    }

    /**
     * post通过json方式调用url
     *
     * @param url     调用URL地址
     * @param jsonStr json格式字符串
     * @return
     */
    public static String getResponseByPost(String url, String jsonStr) {
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost(url);
        try {
            StringEntity se = new StringEntity(jsonStr, HTTP.UTF_8);
            se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
            httppost.setEntity(se);
            HttpResponse response = httpclient.execute(httppost);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == HttpStatus.SC_OK) {
                String result = EntityUtils.toString(response.getEntity());
                return result;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                httpclient.getConnectionManager().shutdown();
                httppost.releaseConnection();
            } catch (Exception ignore) {

            }
        }
        return null;
    }

    /**
     * get 直接通过地址调用
     * 例如： url = sourceHost+"?targetValue="+targetValue+"&k="+sourceKey+"&p="+sourceSecret+"&e="+ext;
     *
     * @param url url地址
     * @return
     */
    public static String getResponseByGet(String url) {
        HttpClient httpclient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(url);

        try {
            HttpResponse response = httpclient.execute(httpGet);
            int statusCode = response.getStatusLine().getStatusCode();
            System.out.println("statusCode" + statusCode);
            if (statusCode == HttpStatus.SC_OK) {
                System.out.println("服务器正常响应.....");
                HttpEntity resEntity = response.getEntity();
                return EntityUtils.toString(resEntity, HTTP.UTF_8);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                httpclient.getConnectionManager().shutdown();
                httpGet.releaseConnection();
            } catch (Exception ignore) {

            }
        }
        return null;
    }

    /**
     * 获取 HttpServletRequest
     */
    public static HttpServletRequest getHttpServletRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }
}
