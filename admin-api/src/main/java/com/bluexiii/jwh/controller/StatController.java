package com.bluexiii.jwh.controller;

import com.bluexiii.jwh.dto.LoginChartDTO;
import com.bluexiii.jwh.dto.SummaryDTO;
import com.bluexiii.jwh.service.StatService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by bluexiii on 2016/9/29.
 */
@Api(value = "stats", description = "统计报表")
@RestController
@RequestMapping(value = "/v1/stats")
public class StatController {
    private static final Logger LOGGER = LoggerFactory.getLogger(StatController.class);
    private final StatService statService;

    @Autowired
    public StatController(StatService statService) {
        this.statService = statService;
    }


    @ApiOperation(value = "首页统计信息", notes = "")
    @RequestMapping(value = "/summary", method = RequestMethod.GET)
    public SummaryDTO summary() {
        return statService.summary();
    }

    @ApiOperation(value = "首页登录量图表", notes = "")
    @RequestMapping(value = "/apiChart", method = RequestMethod.GET)
    public LoginChartDTO apiChart(@ApiParam(value = "服务名") @RequestParam String apiName, @ApiParam(value = "统计天数") @RequestParam Integer days) {
        return statService.apiChart(apiName, days);
    }
}
