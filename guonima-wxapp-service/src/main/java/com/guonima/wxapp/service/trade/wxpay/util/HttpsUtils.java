package com.guonima.wxapp.service.trade.wxpay.util;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.URL;


/**
 * post提交xml格式的参数
 */
public class HttpsUtils {

    /**
     * post请求并得到返回结果
     *
     * @param requestUrl
     * @param requestMethod
     * @param output
     * @return
     */
    public static String httpsRequest(String requestUrl, String requestMethod, String output) {
        InputStream is = null;
        InputStreamReader isr = null;
        BufferedReader br = null;

        try {
            URL url = new URL(requestUrl);
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setUseCaches(false);
            connection.setRequestMethod(requestMethod);
            if (null != output) {
                OutputStream outputStream = connection.getOutputStream();
                outputStream.write(output.getBytes("UTF-8"));
                outputStream.close();
            }
            // 从输入流读取返回内容
            is = connection.getInputStream();
            isr = new InputStreamReader(is, "utf-8");
            br = new BufferedReader(isr);
            String str = null;
            StringBuffer buffer = new StringBuffer();
            while ((str = br.readLine()) != null) {
                buffer.append(str);
            }
            connection.disconnect();
            return buffer.toString();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
                if (isr != null) {
                    isr.close();
                }
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            br = null;
            isr = null;
            is = null;
        }
        return "";
    }

}
