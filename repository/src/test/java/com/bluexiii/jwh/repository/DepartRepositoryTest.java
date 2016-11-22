package com.bluexiii.jwh.repository;

import com.bluexiii.jwh.domain.Depart;
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
public class DepartRepositoryTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(DepartRepositoryTest.class);
    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private DepartRepository departRepository;

    @Before
    public void setUp() throws Exception {

    }

    private Depart persistDepart(String departName) {
        Depart depart = new Depart(departName);
        return entityManager.persist(depart);
    }

    @Test
    public void saveDepartTest() {
        Depart depart0017 = new Depart("全省");
        Depart result0017 = departRepository.save(depart0017);

        Depart depart0531 = new Depart("济南");
        depart0531.setParentDepart(depart0017);
        Depart result0531 = departRepository.save(depart0531);

        LOGGER.debug("result0017 {}", result0017);
        LOGGER.debug("result0531 {}", depart0531);
        assertThat(result0531.getDepartName()).isEqualTo("济南");
    }

    @Test
    public void findDepartTest() {
        Long id = persistDepart("测试部门").getId();
        Depart depart = departRepository.findOne(id);

        LOGGER.debug("result {}", depart);
        assertThat(depart).isNotNull();
    }

    @Test
    public void deleteDepartTest() {
        Long id = persistDepart("测试部门").getId();
        departRepository.delete(id);
    }

}