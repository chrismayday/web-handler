package com.bluexiii.jwh.repository;

import com.bluexiii.jwh.domain.GdsPic;
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
public class GdsPicRepositoryTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(GdsPicRepositoryTest.class);
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private GdsPicRepository gdsPicRepository;

    @Before
    public void setUp() throws Exception {

    }

    private GdsPic persistGdsPic(String picName) {
        GdsPic gdsPic = new GdsPic("主图");
        return entityManager.persist(gdsPic);
    }

    @Test
    public void saveGdsPicTest() {
        GdsPic gdsPic = new GdsPic("主图");
        gdsPic.setPicLink("http://www.abc.com/pic.jpg");
        GdsPic result = gdsPicRepository.save(gdsPic);

        LOGGER.debug("result {}", result);
        assertThat(result.getPicName()).isEqualTo("主图");
    }

}