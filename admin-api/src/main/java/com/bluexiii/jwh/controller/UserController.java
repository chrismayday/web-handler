package com.bluexiii.jwh.controller;

import com.bluexiii.jwh.dto.UserInfoDTO;
import com.bluexiii.jwh.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by bluexiii on 2016/9/29.
 */
@Api(value = "users", description = "用户管理")
@RestController
@RequestMapping(value = "/v1/users")
public class UserController {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @ApiOperation(value = "获取当前登录用户基本信息", notes = "")
    @RequestMapping(value = "/current", method = RequestMethod.GET)
    public UserInfoDTO findUserInfo(@AuthenticationPrincipal UserDetails userDetails) {
        String username = (userDetails == null) ? "guest" : userDetails.getUsername();
        return userService.userInfo(username);
    }
}
