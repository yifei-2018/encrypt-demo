package com.yifei.encrypt.web.test.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * http请求工具类  
 *
 * @author luqs     
 * @version V1.0   
 * @date 2017/11/7 10:13   
 */
@Slf4j
public class HttpUtils {

    /**
     * 连接池配置
     */
    private static PoolingHttpClientConnectionManager cm;
    /**
     * 连接池配置（不检测ssl）
     */
    private static PoolingHttpClientConnectionManager cmWithoutSSL;
    /**
     * 编码格式：utf-8
     */
    private static final String CHARSET_UTF8 = "utf-8";
    /**
     * 协议：http
     */
    private static final String PROTOCOL_HTTP = "http";
    /**
     * 协议：https
     */
    private static final String PROTOCOL_HTTPS = "https";

    /**
     * 静态块
     */
    static {
        init();
        initWithoutSSL();
    }

    /**
     * 构造私有的无参函数，禁止实例化对象,实现单例
     */
    private HttpUtils() {
    }

    /**
     * post请求
     *
     * @param paramFieldMap 参数Map
     * @param url           请求url
     * @return String
     */
    public static String post(Map<String, Object> paramFieldMap, String url) {
        // httpClient
        CloseableHttpClient httpClient = getHttpClient(url, false);
        // post method
        HttpPost httpPost = new HttpPost(url);
        // set params
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        for (Map.Entry<String, Object> entry : paramFieldMap.entrySet()) {
            params.add(new BasicNameValuePair(entry.getKey(),
                    entry.getValue() == null ? "" : String.valueOf(entry.getValue())));
        }
        httpPost.setEntity(new UrlEncodedFormEntity(params, Consts.UTF_8));
        return execute(httpClient, httpPost, CHARSET_UTF8);
    }

    /**
     * post请求
     *
     * @param url          请求url
     * @param paramJsonStr 参数json
     * @return String
     */
    public static String post(String url, String paramJsonStr) {
        CloseableHttpClient httpClient = getHttpClient(url, false);
        // post method
        HttpPost httpPost = new HttpPost(url);
        StringEntity strEntity = new StringEntity(paramJsonStr, CHARSET_UTF8);
        strEntity.setContentType("application/json");
        httpPost.setEntity(strEntity);
        return execute(httpClient, httpPost, CHARSET_UTF8);
    }


    /**
     * get请求
     *
     * @param url 请求url
     * @return String
     */
    public static String get(String url) {
        CloseableHttpClient httpClient = getHttpClient(url, false);
        // 创建httpget.
        HttpGet httpGet = new HttpGet(url);
        return execute(httpClient, httpGet, CHARSET_UTF8);
    }

    /**
     * 执行请求
     *
     * @param httpClient 客户端
     * @param uriRequest 请求
     * @param charset    编码格式
     * @return String 若出现异常，则返回null
     */
    public static String execute(CloseableHttpClient httpClient, HttpUriRequest uriRequest, String charset) {
        CloseableHttpResponse response = null;
        String resultStr = null;
        try {
            // 执行请求
            response = httpClient.execute(uriRequest);
            // 获取响应实体
            HttpEntity entity = response.getEntity();
            resultStr = EntityUtils.toString(entity, charset);
            // 关闭流
            EntityUtils.consume(entity);
        } catch (IOException e) {
            log.error("请求【{}】出现异常：", uriRequest.getURI().toString(), e);
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    log.error("关闭CloseableHttpResponse出现异常：", e);
                }
            }
        }
        return resultStr;
    }

    /**
     * 获取客户端
     *
     * @param url          url
     * @param sslCheckFlag 是否校验ssl
     * @return CloseableHttpClient
     */
    public static CloseableHttpClient getHttpClient(String url, boolean sslCheckFlag) {
        if (url.startsWith(PROTOCOL_HTTPS) && sslCheckFlag) {
            init();
            return HttpClients.custom().setConnectionManager(cm).build();
        } else {
            initWithoutSSL();
            return HttpClients.custom().setConnectionManager(cmWithoutSSL).build();
        }
    }

    /**
     * 初始化连接池
     */
    private synchronized static void init() {
        if (cm != null) {
            return;
        }
        cm = new PoolingHttpClientConnectionManager();
        try {
            // 整个连接池最大连接数
            cm.setMaxTotal(200);
            // 每路由最大连接数，默认值是2
            cm.setDefaultMaxPerRoute(10);
        } catch (Exception e) {
            log.error("初始化http连接池出现异常：", e);
        }
    }

    /**
     * 初始化连接池
     */
    private synchronized static void initWithoutSSL() {
        if (cmWithoutSSL != null) {
            return;
        }
        try {
            // 在调用SSL之前需要重写验证方法，取消检测SSL
            X509TrustManager trustManager = new X509TrustManager() {
                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }

                @Override
                public void checkClientTrusted(X509Certificate[] xcs, String str) {
                }

                @Override
                public void checkServerTrusted(X509Certificate[] xcs, String str) {
                }
            };
            SSLContext ctx = SSLContext.getInstance(SSLConnectionSocketFactory.TLS);
            ctx.init(null, new TrustManager[]{trustManager}, new SecureRandom());
            SSLConnectionSocketFactory socketFactory = new SSLConnectionSocketFactory(ctx,
                    NoopHostnameVerifier.INSTANCE);
            Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
                    .register(PROTOCOL_HTTP, PlainConnectionSocketFactory.INSTANCE)
                    .register(PROTOCOL_HTTPS, socketFactory).build();
            cmWithoutSSL = new PoolingHttpClientConnectionManager(registry);
            // 整个连接池最大连接数
            cmWithoutSSL.setMaxTotal(200);
            // 每路由最大连接数，默认值是2
            cmWithoutSSL.setDefaultMaxPerRoute(10);
        } catch (Exception e) {
            log.error("初始化http连接池(不检测ssl)出现异常：", e);
        }
    }
}
