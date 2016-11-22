package com.bluexiii.jwh.service;

import com.bluexiii.jwh.dto.TestModel;
import com.bluexiii.jwh.domain.Categ;
import com.bluexiii.jwh.domain.Gds;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertNotNull;

/**
 * Created by bluexiii on 16/8/23.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class CategServiceTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(CategServiceTest.class);

    @Autowired
    private CategService categService;

    private static Validator validator;

    @BeforeClass
    public static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void validatorTest() throws Exception {
        TestModel testModel = new TestModel("a", 1);
        Set<ConstraintViolation<TestModel>> validate = validator.validate(testModel);
        String message = validate.iterator().next().getMessage();
        System.out.println(message);
        LOGGER.debug("result {}", testModel);
    }

    @Test
    public void save() throws Exception {
        Categ categ = new Categ("abc", "测试分类1");
        categ.setListOrder(50);
        categ.setMainOrder(10);
        categ.setPicName("test.jpg");
        Categ save = categService.save(categ);
        LOGGER.debug("result {}", save);
        assertNotNull(save);
    }

    @Test
    public void update() throws Exception {
        Categ categ = new Categ("abc", "测试分类1");
        categ.setListOrder(50);
        categ.setMainOrder(10);
//        categ.setId(14L);
        categ.setPicName("test.jpg");
        categService.update(16L, categ);

    }

    @Test
    public void findOneByCategName1() throws Exception {
        Categ categ = categService.findOne(1L);
        LOGGER.debug("result {}", categ);
        assertNotNull(categ);
    }

    @Test
    public void findOneByCategName() throws Exception {
        Categ categ = categService.findOneByCategName(null);
        LOGGER.debug("result {}", categ);
        assertNotNull(categ);
    }

    @Test
    public void findAll() throws Exception {
        List<Categ> all = categService.findAll();
        LOGGER.debug("result {}", all);
        assertNotNull(all);
    }

    @Test
    public void findAllPageable() throws Exception {
        Pageable pageable = new PageRequest(0, 3, new Sort(Sort.Direction.ASC, "id"));
        Page<Categ> allPageable = categService.findAllPageable(pageable);
        LOGGER.debug("result {}", allPageable);
        assertNotNull(allPageable);
    }

    @Test
    public void delete() throws Exception {
        categService.delete(123456L);
    }

    @Test
    public void findGdsByCategIdPageable() throws Exception {
        Page<Gds> gdsByCategIdPageable = categService.findGdsByCategIdPageable(2L, null);
        LOGGER.debug("result {}", gdsByCategIdPageable);
        assertNotNull(gdsByCategIdPageable);
    }

    @Test
    public void createCategGdsRela() throws Exception {
        categService.createCategGdsRela(null, 1234L);
    }

    @Test
    public void deleteCategGdsRela() throws Exception {
        categService.createCategGdsRela(null, 1234L);
    }


}