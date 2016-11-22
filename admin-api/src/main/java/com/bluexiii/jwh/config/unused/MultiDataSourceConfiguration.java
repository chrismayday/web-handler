package com.bluexiii.jwh.config.unused;

import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

/**
 * Created by bluexiii on 16/8/8.
 */
//@Configuration
//@Profile("multids")
public class MultiDataSourceConfiguration {

    //数据源配置
    @Bean(name = "dataSourcePrimary")
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource-primary")
    public DataSource dataSourcePrimary() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "dataSourceMobagent")
    @ConfigurationProperties(prefix = "spring.datasource-mobagent")
    public DataSource dataSourceMobagent() {
        return DataSourceBuilder.create().build();
    }

    //JdbcTemplate配置
    @Bean(name = "jdbcTemplatePrimary")
    @Primary
    public JdbcTemplate jdbcTemplatePrimary() {
        return new JdbcTemplate(dataSourcePrimary());
    }

    @Bean(name = "jdbcTemplateMobagent")
    public JdbcTemplate jdbcTemplateMobagent() {
        return new JdbcTemplate(dataSourceMobagent());
    }
}
