package com.bluexiii.jwh.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by bluexiii on 16/9/18.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class JdbcTemplateDemoServiceTest {

    @Autowired
    JdbcTemplateDemoService jdbcTemplateDemoService;

    @Test
    public void queryDemo() throws Exception {
        jdbcTemplateDemoService.queryDemo(3);
    }

    @Test
    public void insertDemo() throws Exception {
        jdbcTemplateDemoService.insertDemo("admin","memotest");
    }

    @Test
    public void deleteDemo() throws Exception {
        jdbcTemplateDemoService.deleteDemo(2);
    }

}