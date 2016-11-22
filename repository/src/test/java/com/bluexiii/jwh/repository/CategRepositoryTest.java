package com.bluexiii.jwh.repository;

import com.bluexiii.jwh.domain.Categ;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;

/**
 * Created by bluexiii on 16/8/25.
 */
@RunWith(SpringRunner.class)
//@DataJpaTest
@SpringBootTest
public class CategRepositoryTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(CategRepositoryTest.class);

    //    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CategRepository categRepository;

    @Before
    public void setUp() throws Exception {

    }

    private Categ persistCateg(String categName) {
        Categ categ = new Categ(categName, "测试分类");
        return entityManager.persist(categ);
    }

    @Test
    public void saveGdsTest() {
        Categ categ = new Categ("testa", "测试分类a");
        Categ result = categRepository.save(categ);
        LOGGER.debug("result {}", result);
        assertThat(result.getCategName()).isEqualTo("testa");
    }

    @Test
    public void updateGdsTest() {
        Long id = persistCateg("categ1").getId();

        Categ existing = categRepository.findOne(id);
        existing.setCategDesc("desc2");
        Categ result = categRepository.save(existing);

        LOGGER.debug("result {}", result);
        assertThat(result.getCategDesc()).isEqualTo("desc2");
    }

    @Test
    public void findGdsTest() {
        String categName = persistCateg("categ1").getCategName();

        Categ result = categRepository.findOneByCategName(categName);
        LOGGER.debug("result {}", result);
        assertThat(result).isNotNull();
    }

    @Test
    public void searchCategsPageable() {
        Pageable pageable = new PageRequest(0, 5, new Sort(Sort.Direction.ASC, "id"));
        Page<Categ> categs = categRepository.searchCategsPageable(pageable, "pp");
        LOGGER.debug("result {}", categs);

    }

    @Test
    public void findCategMainPage() {
        List<Categ> result = categRepository.findCategsInMainPage();
        LOGGER.debug("====================== {}", result);
    }

}