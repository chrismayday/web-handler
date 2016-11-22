package com.bluexiii.jwh.extra;

import com.bluexiii.jwh.service.CategService;
import com.bluexiii.jwh.service.GdsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.bluexiii.jwh.domain.Categ;
import com.bluexiii.jwh.domain.Gds;
import com.bluexiii.jwh.service.CategServiceTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by bluexiii on 18/10/2016.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class JsonTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(CategServiceTest.class);
    private ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private CategService categService;

    @Autowired
    private GdsService gdsService;

    @Test
    public void mapTest() throws Exception {
        Categ categ = categService.findOne(1L);
        boolean status = categ.isStatus();
        LOGGER.debug("status {}", status);

        String categStr = objectMapper.writeValueAsString(categ);
        LOGGER.debug("categStr {}", categStr);
    }

    @Test
    public void mapTest2() throws Exception {
        Gds gds = gdsService.findOne(147938L);
        String gdsStr = objectMapper.writeValueAsString(gds);

        LOGGER.debug("categStr {}", gds);
    }
}
