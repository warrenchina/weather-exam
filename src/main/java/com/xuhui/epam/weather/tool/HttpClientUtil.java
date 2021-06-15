package com.xuhui.epam.weather.tool;



import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.apache.http.*;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.springframework.util.StringUtils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.util.logging.Logger;



@SuppressWarnings("deprecation")
public class HttpClientUtil {


    private static PoolingHttpClientConnectionManager connMgr;
    private static RequestConfig requestConfig;
    private static final int MAX_TIMEOUT = 120000;
    private static CloseableHttpClient httpClient = null;
    public static final String DEFAULT_ENCODING="utf-8";
    public static final int DEFAULT_MAX_CONNECTIONS=100;
    public static final String DEFAULT_CONTENT_TYPE="application/json";

    static {
       init();
    }

    public static void init(){
    	 connMgr = new PoolingHttpClientConnectionManager();  //设置连接池
         connMgr.setMaxTotal(DEFAULT_MAX_CONNECTIONS);        // 设置连接池大小
         connMgr.setDefaultMaxPerRoute(DEFAULT_MAX_CONNECTIONS);

         RequestConfig.Builder configBuilder = RequestConfig.custom();
         configBuilder.setConnectTimeout(MAX_TIMEOUT);   // 设置连接超时
         configBuilder.setSocketTimeout(MAX_TIMEOUT);    // 设置读取超时
         configBuilder.setConnectionRequestTimeout(MAX_TIMEOUT);  // 设置从连接池获取连接实例的超时
         configBuilder.setStaleConnectionCheckEnabled(true);      // 在提交请求之前 测试连接是否可用
         requestConfig = configBuilder.build();
    }

    /**
     * 生产HttpClient实例
     * 公开，静态的工厂方法，需要使用时才去创建该单体
     *
     * @return CloseableHttpClient
     */
    public static CloseableHttpClient getHttpClient() {
        if (httpClient == null) {
            httpClient = HttpClients.createDefault();
        }
        return httpClient;
    }


    /**
     *
     * @param url
     * @param httpConnectionTimeout
     * @param headers
     * @param encoding
     * @return
     */
    public static String doGet(String url, int httpConnectionTimeout, Header[] headers, String encoding) throws  Exception {
        CloseableHttpClient httpClient = HttpClientUtil.getHttpClient();
        HttpGet httpget = null;
        CloseableHttpResponse response = null;
         httpget = new HttpGet(url);
            // 设置请求和传输超时时间
            RequestConfig requestConfig = RequestConfig.custom()
                    .setSocketTimeout(httpConnectionTimeout)
                    .setConnectTimeout(httpConnectionTimeout)
                    .build();

            httpget.setConfig(requestConfig);


            //设置http header信息
            if(headers != null && headers.length != 0){
                httpget.setHeaders(headers);
            }

            response = httpClient.execute(httpget);

            if(encoding==null || "".equals(encoding.trim())){
            	encoding=DEFAULT_ENCODING;
            }
            String result = EntityUtils.toString(response.getEntity(), encoding);
            response.close();
            return result;

    }
    /**
     * 调用接口获取所有商户的信息
     * @return
     */
    public static Map<String,String> getResultByURLAndKey(String url) throws Exception{
        String result = null;
        try {
            result = HttpClientUtil.doGet(url, 30000, null, null);
            if(StringUtils.isEmpty(result)){
                return null;
            }
            JsonObject jsonObject=new JsonObject();
            JsonArray jsonArray=jsonObject.getAsJsonArray(result);
            Gson gson = new Gson();
            Map<String,String> gsonMap =  gson.fromJson(result,Map.class);
            return gsonMap;
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }

    }






}
