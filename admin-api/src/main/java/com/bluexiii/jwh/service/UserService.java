package com.bluexiii.jwh.service;

import com.bluexiii.jwh.domain.User;
import com.bluexiii.jwh.dto.UserInfoDTO;
import com.bluexiii.jwh.exception.ResourceNotFoundException;
import com.bluexiii.jwh.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

/**
 * Created by bluexiii on 2016/9/29.
 */
@Service
@Transactional
public class UserService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * 获取用户基本信息
     *
     * @param userName
     * @return
     */
    public UserInfoDTO userInfo(String userName) {
        Assert.notNull(userName);
        User user = userRepository.findOneByUsername(userName);
        if (user == null) {
            throw new ResourceNotFoundException(String.format("用户不存在 userName=%s", userName));
        }
        UserInfoDTO userInfoDTO = new UserInfoDTO();
        userInfoDTO.setUsername(user.getUsername());
        userInfoDTO.setFullname(user.getFullname());
        userInfoDTO.setPhone(user.getPhone());
        userInfoDTO.setEmail(user.getPhone());
        userInfoDTO.setDepartId(user.getDepart().getId());
        userInfoDTO.setDepartName(user.getDepart().getDepartName());
        return userInfoDTO;
    }
}
