package com.bluexiii.jwh.controller;

import com.bluexiii.jwh.domain.Deploy;
import com.bluexiii.jwh.service.DeployService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * Created by bluexiii on 2016/9/29.
 */
@Api(value = "deploys", description = "部署管理")
@RestController
@RequestMapping(value = "/v1/deploys")
public class DeployController {
    private static final Logger LOGGER = LoggerFactory.getLogger(DeployController.class);
    private final DeployService deployService;

    @Autowired
    public DeployController(DeployService deployService) {
        this.deployService = deployService;
    }

    @ApiOperation(value = "获取全部应用", notes = "")
    @RequestMapping(value = "", method = RequestMethod.GET)
    public Page<Deploy> findAllDeploys(@ApiParam(value = "分页参数") @PageableDefault(size = 20) Pageable pageable) {
        return deployService.findAllDeploys(pageable);
    }


    @ApiOperation(value = "刷新缓存", notes = "")
    @RequestMapping(value = "/actions/refresh-cache/{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void refreshCache(@ApiParam(value = "应用ID") @PathVariable Long id) {
        deployService.refreshCache(id);
    }

}
