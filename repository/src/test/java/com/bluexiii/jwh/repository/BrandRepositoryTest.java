package com.bluexiii.jwh.repository;

import com.bluexiii.jwh.domain.Brand;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by bluexiii on 16/8/25.
 */
@RunWith(SpringRunner.class)
//@DataJpaTest //强制自动配置并使用内嵌库
@SpringBootTest
@AutoConfigureTestEntityManager
public class BrandRepositoryTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(BrandRepositoryTest.class);
    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private BrandRepository brandRepository;

    @Before
    public void setUp() throws Exception {

    }

    private Brand persistBrand(String brandName) {
        Brand brand = new Brand(brandName,"测试品牌");
        return entityManager.persist(brand);
    }

    @Test
    public void saveBrandTest() {
        Brand brand = new Brand();
        brand.setBrandName("apple");
        brand.setBrandDesc("苹果");
        brand.setId(9999L);
        Brand result = brandRepository.save(brand);
        LOGGER.debug("result {}", result);
        assertThat(result.getBrandName()).isEqualTo("apple");
    }
}