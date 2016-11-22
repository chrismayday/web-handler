package com.bluexiii.jwh.controller;

import com.bluexiii.jwh.dto.GdsDetailDTO;
import com.bluexiii.jwh.service.WoegoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by bluexiii on 08/10/2016.
 */
@RestController
@RequestMapping(value = "/v1/woego")
@Api(value = "woego", description = "外部系统实时接口")
public class WoegoController {
    private static final Logger LOGGER = LoggerFactory.getLogger(CategController.class);
    private final WoegoService woegoService;

    @Autowired
    public WoegoController(WoegoService woegoClientService) {
        this.woegoService = woegoClientService;
    }

    @ApiOperation(value = "外部系统商品详情查询", notes = "调用外部系统接口")
    @RequestMapping(value = "gdses/{gdsId}", method = RequestMethod.GET)
    public GdsDetailDTO queryGdsDetail(@ApiParam(value = "商品ID") @PathVariable String gdsId,
                                       @ApiParam(value = "渠道ID") @RequestParam(required = false) String chnlId,
                                       @ApiParam(value = "区域ID") @RequestParam(required = false) String shopLocaleCode) {
        LOGGER.debug("shopLocalCode {}",shopLocaleCode);
        return woegoService.queryGdsDetail(gdsId,chnlId,shopLocaleCode);
    }

}
