package com.bluexiii.jwh.repository;

import com.bluexiii.jwh.domain.RequestLog;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by bluexiii on 16/9/20.
 */
@RunWith(SpringRunner.class)
//@DataJpaTest
@SpringBootTest
//@AutoConfigureTestEntityManager
public class RequestLogRepositoryTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(RequestLogRepositoryTest.class);
//    @Autowired
//    private TestEntityManager entityManager;
    @Autowired
    private RequestLogRepository requestLogRepository;

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void saveLogTest() {
        RequestLog requestLog = new RequestLog("/rest/logtest", 10L, "admin", "127.0.0.1");
        RequestLog result = requestLogRepository.save(requestLog);
        LOGGER.debug("result {}", result);
        assertThat(result.getUserName()).isEqualTo("admin");
    }

    @Test
    public void findLogTest() {
        RequestLog result = requestLogRepository.findOne(1L);
        LOGGER.debug("result {}", result);
        LOGGER.debug("isStatus {}", result.isStatus());
    }
}