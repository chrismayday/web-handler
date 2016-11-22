package com.bluexiii.jwh.service;

import com.bluexiii.jwh.domain.Deploy;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by bluexiii on 19/10/2016.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class DeployServiceTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(DeployServiceTest.class);
    @Autowired
    DeployService deployService;

    @Test
    public void findAllDeploys() throws Exception {
        Pageable pageable = new PageRequest(0, 3, new Sort(Sort.Direction.ASC, "id"));
        Page<Deploy> allDeploys = deployService.findAllDeploys(pageable);
        LOGGER.debug("result {}", allDeploys);
    }

    @Test
    public void refreshCache() throws Exception {
        deployService.refreshCache(1L);
    }

}