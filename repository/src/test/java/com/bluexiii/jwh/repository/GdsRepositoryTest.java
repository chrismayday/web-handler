package com.bluexiii.jwh.repository;

import com.bluexiii.jwh.domain.Categ;
import com.bluexiii.jwh.domain.Gds;
import com.bluexiii.jwh.domain.GdsDetail;
import com.bluexiii.jwh.domain.GdsPic;
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
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by bluexiii on 16/8/25.
 */
@RunWith(SpringRunner.class)
//@DataJpaTest
@SpringBootTest
public class GdsRepositoryTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(GdsRepositoryTest.class);
    //    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private GdsRepository gdsRepository;
    @Autowired
    GdsDetailRepository gdsDetailRepository;
    @Autowired
    GdsPicRepository gdsPicRepository;
    @Autowired
    CategRepository categRepository;

    @Before
    public void setUp() throws Exception {

    }

    private Gds persistGds(String gdsName) {
        GdsDetail gdsDetail = new GdsDetail();
        gdsDetail.setSpecHtml("spechtml" + new Random().nextInt(10));
        gdsDetail.setDetailHtml("detailhtml" + new Random().nextInt(10));

        GdsPic gdsPic = new GdsPic("主图" + new Random().nextInt(10));
        gdsPic.setPicLink("http://www.abc.com/pic.jpg");

        Gds gds = new Gds(gdsName);
        gds.setGdsPrice(new Random().nextInt(299900));
        gds.setGdsDetail(gdsDetail);
        gds.addPic(gdsPic);

        return entityManager.persist(gds);
    }

    @Test
    public void saveGdsTest() {
        Gds gds = new Gds("测试机型");
        gds.setGdsRealPrice(49900);
        Gds result = gdsRepository.save(gds);

        LOGGER.debug("result {}", result);
        assertThat(result.getGdsName()).isEqualTo("测试机型");
    }

    @Test
    public void saveGdsAndDetailTest() {
        GdsDetail gdsDetail = new GdsDetail();
        gdsDetail.setSpecHtml("spechtml");
        gdsDetail.setDetailHtml("detailhtml");

        Gds gds = new Gds("测试机型");
        gds.setGdsRealPrice(79900);
        gds.setGdsDetail(gdsDetail);
        Gds result = gdsRepository.save(gds);

        LOGGER.debug("result {}", result);
        assertThat(result.getGdsDetail().getDetailHtml()).isEqualTo("detailhtml");
    }

    @Test
    public void saveGdsAndPicTest() {
        GdsPic gdsPic = new GdsPic("主图");
        gdsPic.setPicLink("http://www.abc.com/pic.jpg");

        Gds gds = new Gds("测试机型");
        gds.setGdsRealPrice(79900);
        gds.addPic(gdsPic);
        Gds result = gdsRepository.save(gds);

        LOGGER.debug("result {}", result);
        assertThat(result.getGdsPics()).hasSize(1);
    }

    @Test
    public void saveGdsAndCategTest() {
        Categ categ = categRepository.findOneByCategName("hot");
        Set<Categ> categs = new HashSet<>();
        categs.add(categ);

        Gds gds = new Gds("测试机型E");
        gds.setGdsRealPrice(29900);
        gds.setCategs(categs);
        Gds result = gdsRepository.save(gds);

        LOGGER.debug("result={}", result);
        assertThat(result.getCategs()).hasSize(1);
    }

    @Test
    public void findGdsTest() {
        Long id = persistGds("测试机型").getId();

        Gds gds = gdsRepository.findOne(id);
        LOGGER.debug("gds {}", gds);

        Set<GdsPic> gdsPics = gds.getGdsPics();
        for (GdsPic gdsPic : gdsPics) {
            LOGGER.debug("gdsPic {}", gdsPic);
        }
        assertThat(gds).isNotNull();
    }

    @Test
    public void deleteGdsTest() {
        Long id = persistGds("测试机型").getId();
        gdsRepository.delete(id);
    }


    @Test
    public void searchGdsesPageable() {
        Pageable pageable = new PageRequest(0, 5, new Sort(Sort.Direction.ASC, "id"));
        Page<Gds> gdses = gdsRepository.searchPageable("vivo", pageable);
        LOGGER.debug("result {}", gdses);
    }

    @Test
    public void searchGdsByCategPageable() {
        Categ categ = categRepository.findOne(3L);
        Set<Categ> categs = new LinkedHashSet<>(Arrays.asList(categ));
        Pageable pageable = new PageRequest(0, 5, new Sort(Sort.Direction.ASC, "id"));
        Page<Gds> gdses = gdsRepository.searchByCategsPageable(null, "ppppppp", pageable);
        LOGGER.debug("result {}", gdses);
    }


    @Test
    public void findGdsByCategNullPageable() {
        Pageable pageable = new PageRequest(0, 5, new Sort(Sort.Direction.ASC, "id"));
        Page<Gds> gdses = gdsRepository.findeGdsByCategNullPageable(pageable);
        LOGGER.debug("result {}", gdses);
    }

    @Test
    public void searchGdsByCategNullPageable() {
        Pageable pageable = new PageRequest(0, 5, new Sort(Sort.Direction.ASC, "id"));
        Page<Gds> gdses = gdsRepository.searchByCategsNullPageable("C", pageable);
        LOGGER.debug("result {}", gdses);
    }

    @Test
    public void countByCategs() {
        Categ categ = categRepository.findOne(3L);
        Set categs = new HashSet(Arrays.asList(categ));
        int ct = gdsRepository.countByCategs(categs);
        System.out.println(ct);
    }

    @Test
    @Transactional
    public void updateStatus() {
        int i = gdsRepository.updateStatus(147940L, true);
        System.out.println(i);
    }

    @Test
    public void findByCategsAndStatusIsTrue() {
        Categ categ = categRepository.findOne(3L);
        Pageable pageable = new PageRequest(0, 5, new Sort(Sort.Direction.ASC, "id"));

        Page<Gds> gdses = gdsRepository.findByCategsAndStatusIsTrue(categ, pageable);
        LOGGER.debug("gdses {}" ,gdses);
    }

    @Test
    public void findAllByStatusIsTrue() {
        Pageable pageable = new PageRequest(0, 5, new Sort(Sort.Direction.ASC, "id"));

        Page<Gds> gdses = gdsRepository.findAllByStatusIsTrue( pageable);
        LOGGER.debug("gdses {}" ,gdses);
    }

    @Test
    public void findByGdsNameContainingAndStatusIsTrue() {
        Pageable pageable = new PageRequest(0, 5, new Sort(Sort.Direction.ASC, "id"));

        Page<Gds> gdses = gdsRepository.findByGdsNameContainingAndStatusIsTrue("iphone", pageable);
        LOGGER.debug("gdses {}" ,gdses);
    }



}