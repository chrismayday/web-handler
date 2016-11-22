package com.bluexiii.jwh.repository;

import com.bluexiii.jwh.domain.Depart;
import com.bluexiii.jwh.domain.Role;
import com.bluexiii.jwh.domain.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * Created by bluexiii on 16/8/25.
 */
@RunWith(SpringRunner.class)
@DataJpaTest //强制自动配置并使用内嵌库
//@SpringBootTest
public class UserRepositoryTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserRepositoryTest.class);
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    DepartRepository departRepository;

    @Before
    public void setUp() throws Exception {

    }

    private User persistUser(String userName) {
        Role role = new Role("TEST_ROLE", "测试角色");
        Set<Role> roles = new HashSet<>();
        roles.add(role);

        Depart depart = new Depart("测试部门");

        User user = new User(userName, "测试用户");
        user.setRoles(roles);
        user.setDepart(depart);
        return entityManager.persist(user);
    }

    @Test
    public void passwordEncoderTest() {
        String rawPassword = "unicom531";
        String encPassword = passwordEncoder.encode(rawPassword);
        LOGGER.debug("rawPassword: {}    encPassword: {}", rawPassword, encPassword);
        assertTrue(passwordEncoder.matches(rawPassword, encPassword));
    }

    @Test
    public void saveUserTest() {
        User user = new User("testuser", "济南管理员");
        user.setPassword(passwordEncoder.encode("123456"));
        User result = userRepository.save(user);

        LOGGER.debug("result {}", result);
        assertThat(result.getUsername()).isEqualTo("testuser");
    }

    @Test
    public void deleteUserTest() {
        Long id = persistUser("testuser").getId();
        userRepository.delete(id);
    }


    @Test
    public void saveUserAndRoleTest() {
        Role role = new Role("TEST_ROLE", "测试角色");
        Set<Role> roles = new HashSet<>();
        roles.add(role);
        User user = new User("testuser", "测试用户");
        user.setRoles(roles);
        User result = userRepository.save(user);

        LOGGER.debug("result {}", result);
        assertThat(result.getUsername()).isEqualTo("testuser");
    }

    @Test
    public void updateUserDepartTest() {
        Long id = persistUser("testuser").getId();

        User existing = userRepository.getOne(id);
        existing.setFullname("全名B");
        existing.getDepart().setDepartName("测试部门B");
        User result = userRepository.save(existing);

        LOGGER.debug("result {}", result);
        assertThat(result.getFullname()).isEqualTo("全名B");
        assertThat(result.getDepart().getDepartName()).isEqualTo("测试部门B");
    }

    @Test
    public void findUserTest() {
        Long id = persistUser("testuser").getId();
        User result = userRepository.findOne(id);

        LOGGER.debug("result {}", result);
        assertThat(result.getUsername()).isEqualTo("testuser");
    }
}