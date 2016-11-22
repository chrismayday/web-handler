package com.bluexiii.jwh.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;

/**
 * JdbcTemplate示例
 * Created by bluexiii on 16/8/23.
 */
@Service
@Transactional
public class JdbcTemplateDemoService {
    private static final Logger LOGGER = LoggerFactory.getLogger(JdbcTemplateDemoService.class);
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcTemplateDemoService(/*@Qualifier("jdbcTemplateMobagent")*/ JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void queryDemo(int id) {
        String sql = "select * from t_a_log where seq=" + id;
        List<Map<String, Object>> maps = jdbcTemplate.queryForList(sql);
        for (Map map : maps) {
            String username = (String) map.get("username");
            LOGGER.debug("username={}", username);
        }
    }

    public void insertDemo(String username, String memo) {
        jdbcTemplate.update("INSERT INTO t_a_log (username,memo,operate_time,operate_type,operate_src,operate_dest) VALUES('" + username + "','" + memo + "', now(),'1','','')");
    }

    public void deleteDemo(int id) {
        jdbcTemplate.update("delete from t_a_log where seq=" + id);
    }

}
