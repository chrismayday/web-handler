package com.bluexiii.jwh.repository;

import com.bluexiii.jwh.domain.Shop;
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
import static org.junit.Assert.assertNotNull;

/**
 * Created by bluexiii on 16/8/25.
 */
@RunWith(SpringRunner.class)
@DataJpaTest
//@SpringBootTest
public class ShopRepositoryTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(ShopRepositoryTest.class);
    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private ShopRepository shopRepository;

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void saveShopTest() {
        Shop shop = new Shop("测试店铺");
        Shop result = shopRepository.save(shop);
        LOGGER.debug("result {}", result);
        assertThat(result.getShopName()).isEqualTo("测试店铺");
    }

}