package com.bluexiii.jwh.util;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by bluexiii on 2016/9/26.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class WoegoUtilTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(WoegoUtilTest.class);
    @Autowired
    private WoegoUtil woegoUtil;

    Map<String, String> requestMap;
    String method;

    @Before
    public void setUp() throws Exception {
        method = "com.ai.woego.gds.info.querySearchConditionList";
        requestMap = new LinkedHashMap<>();
        requestMap.put("chnlId", "17b3rov");
        requestMap.put("catId", "100");

    }

    @Test
    public void formatParam() throws Exception {
        Map<String, String> httpReq = woegoUtil.formatParam(method, requestMap);
        LOGGER.debug("httpReq {}", httpReq);
        assertThat(httpReq.get("sign")).isNotEmpty();
    }

    @Test
    public void callService() throws Exception {
        Map<String, String> httpReq = woegoUtil.formatParam(method, requestMap);
        LOGGER.debug("httpReq {}", httpReq);
        String httpResp = woegoUtil.callService(httpReq);
        LOGGER.debug("httpResp {}", httpResp);
        assertThat(httpResp).isNotEmpty();
    }

    @Test
    public void apiClient() throws Exception {
        Map<String, Object> map = woegoUtil.apiClient(method, requestMap);
        LOGGER.debug("map {}", map);
    }
}