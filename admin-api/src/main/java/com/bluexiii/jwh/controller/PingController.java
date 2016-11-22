package com.bluexiii.jwh.controller;


import java.util.concurrent.atomic.AtomicLong;

import com.bluexiii.jwh.dto.GreetingDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/v1")
@Api(value = "home", description = "连通性测试")
public class PingController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @ApiOperation(value = "认证信息", notes = "")
    @RequestMapping(value = "/greeting", method = RequestMethod.GET)
    public GreetingDTO greeting(@AuthenticationPrincipal UserDetails userDetails) {
        return new GreetingDTO(counter.incrementAndGet(), String.format(template, userDetails.getUsername()));
    }

    @ApiOperation(value = "连通性", notes = "")
    @RequestMapping(value = "/ping", method = RequestMethod.GET)
    public String ping() {
        return "alive";
    }

}