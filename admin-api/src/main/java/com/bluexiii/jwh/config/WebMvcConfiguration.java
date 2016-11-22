package com.bluexiii.jwh.config;

import com.bluexiii.jwh.handler.ControllerLoggingHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by bluexiii on 16/9/5.
 */
@Configuration
//@EnableWebMvc
public class WebMvcConfiguration extends WebMvcConfigurerAdapter {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("login");
        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);

    }

    //Controller日志
    @Bean
    public ControllerLoggingHandler controllerLoggingHandler() {
        return new ControllerLoggingHandler();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(controllerLoggingHandler())
                .addPathPatterns("/**");
        //.excludePathPatterns("/login");
    }


}
