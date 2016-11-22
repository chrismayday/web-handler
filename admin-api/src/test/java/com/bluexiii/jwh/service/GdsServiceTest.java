package com.bluexiii.jwh.service;

import com.bluexiii.jwh.domain.Categ;
import com.bluexiii.jwh.domain.Gds;
import com.bluexiii.jwh.domain.GdsDetail;
import com.bluexiii.jwh.domain.GdsPic;
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

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * Created by bluexiii on 16/9/13.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
//@Transactional
public class GdsServiceTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(GdsServiceTest.class);

    @Autowired
    private GdsService gdsService;
    @Autowired
    private CategService categService;

    @BeforeClass
    public static void setUp() {
    }

    @Test
    public void save() throws Exception {
        Gds gds = new Gds("test3");
        gds.setGdsRealPrice(119900);
//        Categ categ = categService.findOne(14L);
        Categ categ = new Categ();
        categ.setId(15L);
        gds.addCateg(categ);

        gdsService.save(gds);
    }

    @Test
    public void save2() throws Exception {
        Categ categ = new Categ("test5", "test5");
        categService.save(categ);
        Gds gds = new Gds("test5");
        gds.setGdsRealPrice(149900);
        gds.addCateg(categ);
        gdsService.save(gds);
    }

    @Test
    public void save3() throws Exception {

        Gds gds = new Gds("test12");
        gds.setGdsRealPrice(149900);

        GdsDetail gdsDetail = new GdsDetail();
        gdsDetail.setSpecHtml("test12");
//        gdsDetail.setGds(gds);

        gds.setGdsDetail(gdsDetail);

        gdsService.save(gds);
    }

    @Test
    public void save4() throws Exception {

        Gds gds = new Gds("test12");
        gds.setGdsRealPrice(149900);

        GdsPic gdsPic = new GdsPic("pic12");

        gds.addPic(gdsPic);

        gdsService.save(gds);

    }

    @Test
    public void update() throws Exception {
        Gds gds = gdsService.findOne(147978L);
        Set<GdsPic> gdsPics = gds.getGdsPics();
        for (GdsPic gdsPic : gdsPics) {
            gdsPic.setPicName("qqqqqwwwwweeee");
        }
//        GdsDetail gdsDetail = gds.getGdsDetail();
//        gdsDetail.setSpecHtml("aaaabbbbcccc");
        gdsService.update(147978L, gds);
    }

    @Test
    public void update2() throws Exception {
        Gds gds = gdsService.findOne(147960L);
//        Categ categ = categService.findOne(15L);
        Categ categ = new Categ();
        categ.setId(15L);
        gds.addCateg(categ);
        gdsService.update(147960L, gds);
    }

    @Test
    public void update3() throws Exception {
        Gds gds = gdsService.findOne(147960L);
//        Categ categ = categService.findOne(15L);
//        gds.removeCateg(categ);
        Categ categ = new Categ();
        categ.setId(14L);
        Set categs = new HashSet();
        categs.add(categ);
        gds.setCategs(categs);
        gdsService.update(147960L, gds);
    }

    @Test
    public void update4() throws Exception {
        Gds gds = gdsService.findOne(147960L);
        gds.getCategs().clear();
        gdsService.update(147960L, gds);
    }

    @Test
    public void delete() throws Exception {
        gdsService.delete(147959L);
    }

    @Test
    public void findOne() throws Exception {
        Gds gds = gdsService.findOne(147965L);

//        LOGGER.debug("result {}", gds);
//        assertNotNull(gds);
    }

    @Test
    public void findAll() throws Exception {
        List<Gds> all = gdsService.findAll();
        LOGGER.debug("result {}", all);
        assertNotNull(all);
    }

    @Test
    public void findAllPageable() throws Exception {
        Pageable pageable = new PageRequest(0, 3, new Sort(Sort.Direction.ASC, "id"));
        Page<Gds> allPageable = gdsService.findAllPageable(pageable);
        LOGGER.debug("result {}", allPageable);
        assertNotNull(allPageable);
    }

    @Test
    public void findByGdsNamePageable() throws Exception {
        Pageable pageable = new PageRequest(0, 3, new Sort(Sort.Direction.ASC, "id"));
        Page<Gds> gdses = gdsService.findByGdsNamePageable("vivo", pageable);
        LOGGER.debug("result {}", gdses);
        assertNotNull(gdses);
    }

    @Test
    public void createGdsCategRela() throws Exception {
        gdsService.createGdsCategRela(147970L, 23L);
    }

    @Test
    public void deleteCategGdsRela() throws Exception {
        gdsService.deleteCategGdsRela(147970L, 23L);
    }
}