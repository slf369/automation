package com.demo.util.common;

import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * HTTPClient工具类
 */
public class HttpClientUtil {

    private static final String CHAR_SET = "UTF-8";
    private static Logger logger = LoggerFactory.getLogger(HttpClientUtil.class);
    // 最大连接数200
    private static int MAX_CONNECTION_NUM = 200;
    // 单路由最大连接数20
    private static int MAX_PER_ROUTE = 20;
    // 向服务端请求超时时间设置(单位:毫秒)
    private static int SERVER_REQUEST_TIME_OUT = 10000;
    // 服务端响应超时时间设置(单位:毫秒)
    private static int SERVER_RESPONSE_TIME_OUT = 10000;
    // HttpRequest的SocketTimeout(单位:毫秒)
    private static int requestSocketTimeout = 10000;
    // HttpRequest的ConnectTimeout(单位:毫秒)
    private static int requestConnectTimeout = 10000;
    // 同步对象
    private static Object SYNC_LOCK = new Object();
    // 连接池管理对象
    private volatile static PoolingHttpClientConnectionManager cm = null;

    // 单例-双重校验锁法
    private volatile static HttpClientUtil instance;

    private HttpClientUtil() {
    }

    public static HttpClientUtil getInstance(){
        if(instance==null){
            synchronized (HttpClientUtil.class){
                if(instance==null){
                    instance=new HttpClientUtil();
                }
            }
        }
        return instance;
    }

    /**
     * 初始化连接池管理对象
     * @return PoolingHttpClientConnectionManager
     */
    private PoolingHttpClientConnectionManager getPoolManager() {
        if (null == cm) {
            synchronized (SYNC_LOCK) {
                if (null == cm) {
                    SSLContextBuilder sslContextBuilder = new SSLContextBuilder();
                    try {
                        sslContextBuilder.loadTrustMaterial(null, new TrustSelfSignedStrategy());
                        SSLConnectionSocketFactory socketFactory = new SSLConnectionSocketFactory(sslContextBuilder.build());
                        Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
                                .register("https", socketFactory)
                                .register("http", new PlainConnectionSocketFactory())
                                .build();
                        cm = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
                        cm.setMaxTotal(MAX_CONNECTION_NUM);
                        cm.setDefaultMaxPerRoute(MAX_PER_ROUTE);
                    } catch (Exception e) {
                        logger.error("Init PoolingHttpClientConnectionManager Error" + e);
                    }
                }
            }
        }
        logger.info("Init PoolingHttpClientConnectionManager Success");
        return cm;
    }

    /**
     * 创建线程安全的HttpClient
     * @param config 客户端超时设置
     * @return
     */
    public  CloseableHttpClient getHttpsClient(RequestConfig config) {
        CloseableHttpClient httpClient = HttpClients.custom()
                .setDefaultRequestConfig(config)
                .setConnectionManager(this.getPoolManager())
                .build();
        return httpClient;
    }

    /**
     * @param url      请求的URL
     * @param header   请求头
     * @param jsonBody Post请求体
     */
    public String postJson(String url, Map<String, String> header, String jsonBody) {
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse response = null;
        HttpPost httpPost = null;
        // 请求Entity
        StringEntity reqEntity = null;
        // 返回Entity
        HttpEntity resEntity = null;
        // 返回状态码
        int status;
        // 返回字符串
        String result = "";
        try {
            if (url != null && !url.isEmpty()) {
                httpPost = new HttpPost(url);
            }
            // connectTimeout设置服务器请求超时时间
            // socketTimeout设置服务器响应超时时间
            RequestConfig requestConfig = RequestConfig.custom()
                    .setSocketTimeout(requestSocketTimeout).setConnectTimeout(requestConnectTimeout).build();
            httpPost.setConfig(requestConfig);

            // 设置报文头
            if (header != null && !header.isEmpty()) {
                for (Map.Entry<String, String> map : header.entrySet()) {
                    httpPost.addHeader(map.getKey(), map.getValue());
                }
            }
            // 设置请求实体
            if (jsonBody != null && !jsonBody.isEmpty()) {
                reqEntity = new StringEntity(jsonBody, CHAR_SET);
                reqEntity.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
//        reqEntity.setContentEncoding("UTF-8");
//        reqEntity.setContentType("application/json");
                httpPost.setEntity(reqEntity);
            }
            if (httpClient == null) {
                httpClient = getHttpsClient(requestConfig);
            }
            // 执行请求
            response = httpClient.execute(httpPost);
            // 转换结果
            status = response.getStatusLine().getStatusCode();
            if (status >= 200 && status <= 300) {
                resEntity = response.getEntity();
                if (resEntity != null) {
                    result = EntityUtils.toString(resEntity, CHAR_SET);
//                String resStr = EntityUtils.toString(resEntity, CHAR_SET);
//                responseStr = Unicode2String.convertUnicode(resStr);
                    EntityUtils.consume(response.getEntity());
                    return result;
                }else {
                    resEntity = response.getEntity();
                    httpPost.abort();
                    logger.error(" 接收响应：url" + url + " status=" + status + " resopnse=" + EntityUtils.toString(resEntity, "utf-8"));
                }
            }
        } catch (Exception e) {
            if (e instanceof SocketTimeoutException) {
                // 服务器请求超时
                logger.error("server request time out");
            } else if (e instanceof ConnectTimeoutException) {
                // 服务器响应超时(已经请求了)
                logger.error("server response time out");
            }
        } finally {
            httpPost.releaseConnection();
            if (response != null) {
                try {
                    //
                    EntityUtils.consume(response.getEntity());
                    response.close();
                } catch (IOException e) {
                    logger.error(e.getMessage());
                }
            }
        }
        return result;
    }



    public String postForm(String url, Map<String, String> header, Map<String, String> params) {
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse response = null;
        HttpPost httpPost = null;
        // 请求Entity
        StringEntity reqEntity = null;
        // 返回Entity
        HttpEntity resEntity = null;
        // 返回状态码
        int status;
        // 返回字符串
        String result = "";
        try {
            if (url != null && !url.isEmpty()) {
                httpPost = new HttpPost(url);
            }
            // connectTimeout设置服务器请求超时时间
            // socketTimeout设置服务器响应超时时间
            RequestConfig requestConfig = RequestConfig.custom()
                    .setSocketTimeout(requestSocketTimeout).setConnectTimeout(requestConnectTimeout).build();
            httpPost.setConfig(requestConfig);

            // 设置报文头
            if (header != null && !header.isEmpty()) {
                for (Map.Entry<String, String> map : header.entrySet()) {
                    httpPost.addHeader(map.getKey(), map.getValue());
                }
            }

            // 设置请求参数
            if (!params.isEmpty()) {
                List<NameValuePair> formparams = new ArrayList<NameValuePair>();
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    //给参数赋值
                    formparams.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
                }
                UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(formparams, Consts.UTF_8);
                httpPost.setEntity(urlEncodedFormEntity);
            }

            if (httpClient == null) {
                httpClient = getHttpsClient(requestConfig);
            }
            // 执行请求
            response = httpClient.execute(httpPost);
            // 转换结果
            status = response.getStatusLine().getStatusCode();
            if (status >= 200 && status <= 300) {
                resEntity = response.getEntity();
                if (resEntity != null) {
                    result = EntityUtils.toString(resEntity, CHAR_SET);
//                String resStr = EntityUtils.toString(resEntity, CHAR_SET);
//                responseStr = Unicode2String.convertUnicode(resStr);
                    EntityUtils.consume(response.getEntity());
                    return result;
                }else {
                    resEntity = response.getEntity();
                    httpPost.abort();
                    logger.error(" 接收响应：url" + url + " status=" + status + " resopnse=" + EntityUtils.toString(resEntity, "utf-8"));
                }
            }
        } catch (Exception e) {
            if (e instanceof SocketTimeoutException) {
                // 服务器请求超时
                logger.error("server request time out");
            } else if (e instanceof ConnectTimeoutException) {
                // 服务器响应超时(已经请求了)
                logger.error("server response time out");
            }
        } finally {
            httpPost.releaseConnection();
            if (response != null) {
                try {
                    //
                    EntityUtils.consume(response.getEntity());
                    response.close();
                } catch (IOException e) {
                    logger.error(e.getMessage());
                }
            }
        }
        return result;
    }


    /**
     * @param url       Get请求的URL
     * @param header    请求头
     * @param pathParam get请求的路径参数
     */
    public  String getRequest(String url, Map<String, String> header, Map<String, String> pathParam){
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse response = null;
        HttpGet httpGet = null;
        // 请求Entity
        StringEntity reqEntity = null;
        // 返回Entity
        HttpEntity resEntity = null;
        // 返回状态码
        int status;
        // 返回字符串
        String result = "";
        try {
            if (url != null && !url.isEmpty()) {
                // URL拼接
                String mUrl = url;
                mUrl = getGetParams(url, pathParam);
                httpGet = new HttpGet(mUrl);
            }
            // 设置报文头
            if (header != null && !header.isEmpty()) {
                for (Map.Entry<String, String> entry : header.entrySet()) {
                    httpGet.addHeader(entry.getKey(), entry.getValue());
                }
            }
            // connectTimeout设置服务器请求超时时间
            // socketTimeout设置服务器响应超时时间
            RequestConfig requestConfig = RequestConfig.custom()
                    .setSocketTimeout(requestSocketTimeout).setConnectTimeout(requestConnectTimeout).build();
            httpGet.setConfig(requestConfig);
            // 设置报文头
            if (header != null && !header.isEmpty()) {
                for (Map.Entry<String, String> map : header.entrySet()) {
                    httpGet.addHeader(map.getKey(), map.getValue());
                }
            }
            if (httpClient == null) {
                httpClient = getHttpsClient(requestConfig);
            }
            // 执行请求
            response = httpClient.execute(httpGet);
            // 转换结果
            status = response.getStatusLine().getStatusCode();
            if (status >= 200 && status <= 300) {
                resEntity = response.getEntity();
                if (resEntity != null) {
                    result = EntityUtils.toString(resEntity, CHAR_SET);
//                String resStr = EntityUtils.toString(resEntity, CHAR_SET);
//                responseStr = Unicode2String.convertUnicode(resStr);
                    EntityUtils.consume(response.getEntity());
                    return result;
                }
                else {
                    resEntity = response.getEntity();
                    httpGet.abort();
                    logger.error(" 接收响应：url" + url + " status=" + status + " resopnse=" + EntityUtils.toString(resEntity, "utf-8"));
                }
            }
        } catch (Exception e) {
            if (e instanceof SocketTimeoutException) {
                // 服务器请求超时
                logger.error("server request time out");
            } else if (e instanceof ConnectTimeoutException) {
                // 服务器响应超时(已经请求了)
                logger.error("server response time out");
            }
        } finally {
            httpGet.releaseConnection();
            if (response != null) {
                try {
                    //
                    EntityUtils.consume(response.getEntity());
                    response.close();
                } catch (IOException e) {
                    logger.error(e.getMessage());
                }
            }
        }
        return result;
    }

    /**
     * 获取Get请求全路径
     *
     * @param url
     * @param paramsMap
     * @return
     */
    public  String getGetParams(String url, Map<String, String> paramsMap) {
        if (paramsMap != null && !paramsMap.isEmpty()) {
            url = url + "?";
            for (String key : paramsMap.keySet()) {
                url = url + key + "=" + paramsMap.get(key) + "&";
            }
            url = url.substring(0, url.length() - 1);
        }
        return url;
    }


    public static void main(String[] args) throws IOException {
        HttpClientUtil httpClientUtil=HttpClientUtil.getInstance();

        String url1 = "http://localhost:8085/user/getusers";
        Map<String, String> params = new HashMap<String, String>();
        params.put("role", "Manager");
        Map<String, String> header = new HashMap<String, String>();
        header.put("Content-Type", "application/json");

        String url2 = "http://localhost:8085/user/updateuser";
        String jsonBody="{\n" +
                "\t\"id\":\"2\",\n" +
                "\t\"name\":\"user2\",\n" +
                "\t\"password\":\"adfjasdfahdfa\",\n" +
                "\t\"salt\":\"680137\",\n" +
                "\t\"role\":\"Manager\"\n" +
                "}";

        String url3 = "http://172.16.17.152:8090/riskcontrol/login/check";
        header.put("Content-Type", "application/json");
        String jsonBody3="{\n" +
                "  \"FUNCODE\": \"CHECK\", \n" +
                "  \"RPID\":\"R0000000016CzBHI\",\n" +
                "  \"IP\":\"110.10.51.95\",\n" +
                "  \"MOBILEID\":\"15000551455\",\n" +
                "  \"REQDATE\":\"20171122\",\n" +
                "  \"REQTIME\":\"182800\",\n" +
                "  \"CHANNEL\":\"APP\",\n" +
                "  \"LOGINTYPE\":\"PASS\",\n" +
                "  \"USERID\":\"U201708290125000644\",\n" +
                "  \"MACHINETYPE\":\"IOS\",\n" +
                "  \"MACHINENO\":\"D439B5D1540FD1AC37C51FECCEEFE38F2EC64B82\",  \n" +
                "  \"VERSION\":\"\"\n" +
                "}";

        System.out.println(httpClientUtil.postJson(url3, header, jsonBody3));

        for (int i = 0; i < 100; i++) {
            System.out.println(httpClientUtil.getRequest(url1, header, params));
            System.out.println(httpClientUtil.postJson(url2, header, jsonBody));
            System.out.println(httpClientUtil.postJson(url3, header, jsonBody3));
            System.out.println("Current Number is :"+i);
        }
    }

}
