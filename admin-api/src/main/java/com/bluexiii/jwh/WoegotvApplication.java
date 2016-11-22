package com.bluexiii.jwh;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;


//@EnableAuthorizationServer
//@EnableResourceServer
//@EnableGlobalMethodSecurity(prePostEnabled = true)

@SpringBootApplication
public class WoegotvApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(WoegotvApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(WoegotvApplication.class, args);
    }
}