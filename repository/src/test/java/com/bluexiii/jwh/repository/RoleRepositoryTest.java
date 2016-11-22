package com.bluexiii.jwh.repository;

import com.bluexiii.jwh.domain.Role;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by bluexiii on 16/8/25.
 */
@RunWith(SpringRunner.class)
@DataJpaTest
//@SpringBootTest
public class RoleRepositoryTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(RoleRepositoryTest.class);
    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private RoleRepository roleRepository;

    @Before
    public void setUp() throws Exception {

    }

    private Role persistRole(String roleName) {
        Role role = new Role(roleName,"测试角色");
        return entityManager.persist(role);
    }

    @Test
    public void saveRoleTest() {
        Role role = new Role("TEST_ROLE","测试角色");
        Role result = roleRepository.save(role);
        LOGGER.debug("result {}", result);
        assertThat(result.getRoleName()).isEqualTo("TEST_ROLE");
    }

    @Test
    public void updateRoleTest() {
        String roleName = persistRole("TEST_ROLE").getRoleName();
        Role role = roleRepository.findByRoleName(roleName);
        role.setRoleDesc("测试角色B");
        Role result = roleRepository.save(role);

        LOGGER.debug("result {}", result);
        assertThat(result.getRoleDesc()).isEqualTo("测试角色B");
    }
}