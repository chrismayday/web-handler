package com.bluexiii.jwh.service;

import com.bluexiii.jwh.dto.LoginChartDTO;
import com.bluexiii.jwh.dto.SummaryDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by bluexiii on 2016/9/29.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class StatServiceTest {



    private static final Logger LOGGER = LoggerFactory.getLogger(StatServiceTest.class);
    @Autowired
    StatService statService;

    @Test
    public void summary() throws Exception {
        SummaryDTO summary = statService.summary();
        LOGGER.debug("summary {}",summary);
        assertThat(summary.getUserCount()).isGreaterThan(0);
    }

    @Test
    public void loginChart() throws Exception {
        LoginChartDTO loginChart = statService.apiChart("/api/v1/actions/login",7);
        LOGGER.debug("loginChart {}",loginChart);
        assertThat(loginChart).isNotNull();
    }
}