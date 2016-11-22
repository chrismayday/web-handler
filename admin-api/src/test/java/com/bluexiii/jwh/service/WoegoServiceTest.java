package com.bluexiii.jwh.service;

import com.bluexiii.jwh.dto.GdsDetailDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * Created by bluexiii on 2016/9/27.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class WoegoServiceTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(WoegoServiceTest.class);

    @Autowired
    private WoegoService woegoService;

    @Test
    public void querySearchConditionList() throws Exception {
        Map<String, Object> responseMap = woegoService.querySearchConditionList(null);
        assertThat(((Map) responseMap.get("resp")).get("code")).isEqualTo("0000");
    }

    @Test
    public void queryGdsList() throws Exception {
        String brandId = "1627";
        String pageNum = "1";
        Map<String, Object> responseMap = woegoService.queryGdsList(null, brandId, pageNum);
        assertThat(((Map) responseMap.get("resp")).get("code")).isEqualTo("0000");
    }

    @Test
    public void queryGdsDetail() throws Exception {
        GdsDetailDTO gdsDetailDTO = woegoService.queryGdsDetail("1152042", null, "17");
        LOGGER.debug(gdsDetailDTO.toString());
//        assertThat(((Map) responseMap.get("resp")).get("code")).isEqualTo("0000");
    }

}