package com.bluexiii.jwh.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * WebMvcTest示列
 * Created by bluexiii on 16/9/20.
 */
@RunWith(SpringRunner.class)
@WebMvcTest(PingController.class)
public class PingControllerTest {
    @Autowired
    private MockMvc mvc;


    @Test
    public void ping() throws Exception {
        this.mvc.perform(get("/api/v1/ping").accept(MediaType.TEXT_PLAIN))
                .andExpect(status().isOk()).andExpect(content().string("alive"));
    }

}