package com.bluexiii.jwh.util;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 外部系统TV帮助类
 * Created by bluexiii on 16/8/2.
 */
@Component
@ConfigurationProperties(prefix = "config")
public class WoegoUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(WoegoUtil.class);
    private ObjectMapper objectMapper = new ObjectMapper();

    private String appBaseUrl;
    private String picBaseUrl;
    private String apiUrl;
    private String appkey;
    private String secret;
    private String session;

    public WoegoUtil() {
    }

    public WoegoUtil(String appBaseUrl, String picBaseUrl, String apiUrl, String appkey, String secret, String session) {
        this.appBaseUrl = appBaseUrl;
        this.picBaseUrl = picBaseUrl;
        this.apiUrl = apiUrl;
        this.appkey = appkey;
        this.secret = secret;
        this.session = session;
    }

    public String getAppBaseUrl() {
        return appBaseUrl;
    }

    public void setAppBaseUrl(String appBaseUrl) {
        this.appBaseUrl = appBaseUrl;
    }

    public String getPicBaseUrl() {
        return picBaseUrl;
    }

    public void setPicBaseUrl(String picBaseUrl) {
        this.picBaseUrl = picBaseUrl;
    }

    public String getApiUrl() {
        return apiUrl;
    }

    public void setApiUrl(String apiUrl) {
        this.apiUrl = apiUrl;
    }

    public String getAppkey() {
        return appkey;
    }

    public void setAppkey(String appkey) {
        this.appkey = appkey;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }


    /**
     * 格式化入参
     *
     * @param paramMap
     * @return
     */
    public Map<String, String> formatParam(String method, Map<String, String> paramMap) {
        Map<String, String> requestMap = new HashMap<String, String>();

        //基本参数
        String format = "JSON";
        String v = "1.0";
        String signMethod = "md5";
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        requestMap.put("app_key", this.appkey);
        requestMap.put("sign_method", signMethod);
        requestMap.put("timestamp", sdf.format(new Date()));
        requestMap.put("woego.session", this.session);
        requestMap.put("format", format);
        requestMap.put("v", v);
        requestMap.put("method", method);

        //服务入参
        Iterator entries = paramMap.entrySet().iterator();
        while (entries.hasNext()) {
            Map.Entry entry = (Map.Entry) entries.next();
            String key = (String) entry.getKey();
            String value = (String) entry.getValue();
            requestMap.put(key, value);
        }

        //计算sign
        String signStr = "";
        String cnType = signMethod;
        if (cnType.equals("md5")) {
            signStr = DigestUtil.digest(requestMap, this.secret, DigestUtil.DigestALGEnum.MD5, "UTF-8");
        } else if ("hmac".equals(cnType)) {
            signStr = DigestUtil.digest(requestMap, this.secret, DigestUtil.DigestALGEnum.SHA256, "UTF-8");
        }
        requestMap.put("sign", signStr);
        return requestMap;
    }

    /**
     * 发起网络请求
     *
     * @param requestMap
     * @return
     */
    public String callService(Map<String, String> requestMap) {
        String responseStr = null;
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse httpResponse = null;

        try {
            httpClient = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost(this.apiUrl);
            Header header = new BasicHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
            httpPost.addHeader(header);

            List<NameValuePair> nvps = new ArrayList();
            Iterator it = requestMap.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry entry = (Map.Entry) it.next();
                String key = (String) entry.getKey();
                String value = (String) entry.getValue();
                NameValuePair nvp = new BasicNameValuePair(key, value);
                nvps.add(nvp);
            }
            HttpEntity entity = new UrlEncodedFormEntity(nvps);

            if (entity != null) {
                httpPost.setEntity(entity);
            }
            httpResponse = httpClient.execute(httpPost);
            LOGGER.info("http status: {}", httpResponse.getStatusLine());
            if (httpResponse.getEntity() != null) {
                responseStr = EntityUtils.toString(httpResponse.getEntity());
            }
        } catch (Exception e) {
            LOGGER.warn("执行http post方法出错 {}", e);
        } finally {
            HttpClientUtils.closeQuietly(httpResponse);
            HttpClientUtils.closeQuietly(httpClient);
        }

        return responseStr;
    }

    /**
     * 封装外部系统API客户端，输出Map
     *
     * @param method
     * @param paramMap
     * @return
     */
    public Map<String, Object> apiClient(String method, Map<String, String> paramMap) {
        Map<String, Object> responseMap = new LinkedHashMap<>();

        Map<String, String> requestMap = formatParam(method, paramMap);
        LOGGER.debug("requestMap {}",requestMap);
        String responseStr = callService(requestMap);
        LOGGER.debug("responseStr {}",responseStr);

        try {
            responseMap = objectMapper.readValue(responseStr, LinkedHashMap.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return responseMap;
    }

    /**
     * 封装外部系统API客户端,输出JsonNode
     *
     * @param method
     * @param paramMap
     * @return
     */
    public JsonNode apiClientJsonNode(String method, Map<String, String> paramMap) {
        JsonNode jsonNode = null;
        Map<String, String> requestMap = formatParam(method, paramMap);
        LOGGER.debug("requestMap {}",requestMap);
        String responseStr = callService(requestMap);
        LOGGER.debug("responseStr {}",responseStr);

        try {
            jsonNode = objectMapper.readTree(responseStr);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonNode;
    }
}