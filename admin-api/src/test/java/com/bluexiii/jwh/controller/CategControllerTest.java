package com.bluexiii.jwh.controller;

import com.bluexiii.jwh.service.CategService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.bluexiii.jwh.domain.Categ;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.Matchers.*;

/**
 * WebMvcTest示列
 * Created by bluexiii on 16/9/20.
 */
@RunWith(SpringRunner.class)
@WebMvcTest(CategController.class)
@EnableSpringDataWebSupport  //解析pageable
public class CategControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private CategService categService;

    @Before
    public void setUp() throws Exception {
        stubRepository();
    }

    private void stubRepository() {
        List<Categ> categs = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            categs.add(mockCateg("Categ" + i));
        }
        given(categService.findOne(anyLong())).willReturn(mockCateg("apple"));
        given(categService.findAllPageable(any())).willReturn(new PageImpl<Categ>(categs));
        given(categService.save(any())).willReturn(mockCateg("huawei"));
    }

    private Categ mockCateg(String prefix) {
        Categ categ = new Categ(prefix + "_name", prefix + "_desc");
        categ.setId(new Long(new Random().nextInt(6)));
        return categ;
    }

    private byte[] toJson(Object r) throws Exception {
        ObjectMapper map = new ObjectMapper();
        return map.writeValueAsString(r).getBytes();
    }

    @Test
    public void saveTest() throws Exception {
        Categ categ = mockCateg("huawei");
        byte[] categJson = toJson(categ);
        mvc.perform(post("/api/v1/categs/")
                .content(categJson)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    public void updateTest() throws Exception {
        Categ categ = mockCateg("huawei");
        byte[] categJson = toJson(categ);
        mvc.perform(put("/api/v1/categs/1")
                .content(categJson)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    public void deleteTest() throws Exception {
        mvc.perform(delete("/api/v1/categs/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void findOneTest() throws Exception {
        mvc.perform(get("/api/v1/categs/1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.categName", is("apple_name")));
    }

    @Test
    public void findAllPageableTest() throws Exception {
        mvc.perform(get("/api/v1/categs/?page=0&size=3&sort=id,DESC")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(10)));
    }
}