package com.bluexiii.jwh.service;

import com.bluexiii.jwh.dto.UserInfoDTO;
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
public class UserServiceTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceTest.class);

    @Autowired
    UserService userService;

    @Test
    public void userInfo() throws Exception {
        String userName = "admin";
        UserInfoDTO userInfoDTO = userService.userInfo(userName);
        LOGGER.debug(userInfoDTO.toString());
        assertThat(userInfoDTO.getUsername()).isEqualTo(userName);
    }

}