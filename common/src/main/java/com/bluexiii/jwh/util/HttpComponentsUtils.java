package com.bluexiii.jwh.util;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class HttpComponentsUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(HttpComponentsUtils.class);

    // -- http get -- //
    public static String get(String uri) {
        return get(uri, null);
    }

    public static String get(String uri, List<Header> headers) {
        String result = null;
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse httpResponse = null;
        LOGGER.info("uri=" + uri);

        try {
            httpClient = HttpClients.createDefault();
            HttpUriRequest httpGet = new HttpGet(uri);
            if (headers != null && headers.size() > 0) {
                for (Header header : headers) {
                    httpGet.addHeader(header);
                }
            }
            httpResponse = httpClient.execute(httpGet);
            LOGGER.info("http status: {}", httpResponse.getStatusLine());
            if (httpResponse.getEntity() != null) {
                result = EntityUtils.toString(httpResponse.getEntity());
            }
        } catch (Exception e) {
            LOGGER.warn("执行http get方法出错.", e);
        } finally {
            HttpClientUtils.closeQuietly(httpResponse);
            HttpClientUtils.closeQuietly(httpClient);
        }
        return result;
    }

    // -- http post -- //
    public static String post(String uri) {
        return post(uri, null, null);
    }

    public static String post(String uri, List headers) {
        return post(uri, headers, null);
    }

    public static String post(String uri, List<Header> headers, HttpEntity entity) {
        String result = null;
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse httpResponse = null;
        try {
            httpClient = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost(uri);
            if (headers != null && headers.size() > 0) {
                for (Header header : headers) {
                    httpPost.addHeader(header);
                }
            }
            if (entity != null) {
                httpPost.setEntity(entity);
            }
            httpResponse = httpClient.execute(httpPost);
            LOGGER.info("http status: {}", httpResponse.getStatusLine());
            if (httpResponse.getEntity() != null) {
                result = EntityUtils.toString(httpResponse.getEntity());
            }
        } catch (Exception e) {
            LOGGER.warn("执行http post方法出错.", e);
        } finally {
            HttpClientUtils.closeQuietly(httpResponse);
            HttpClientUtils.closeQuietly(httpClient);
        }
        return result;
    }
}
