package com.bluexiii.jwh.controller;

import com.bluexiii.jwh.component.GdsSimpleDTOConverter;
import com.bluexiii.jwh.domain.Gds;
import com.bluexiii.jwh.dto.GdsSimpleDTO;
import com.bluexiii.jwh.service.GdsService;
import com.bluexiii.jwh.util.RestUtil;
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

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;


/**
 * Created by bluexiii on 16/9/5.
 */
@RestController
@RequestMapping(value = "/v1/gdses")
@Api(value = "gdses", description = "商品管理")
public class GdsController {
    private static final Logger LOGGER = LoggerFactory.getLogger(GdsController.class);
    @Autowired
    private GdsService gdsService;

    @Autowired
    private GdsSimpleDTOConverter gdsSimpleDTOConverter;

    @ApiOperation(value = "创建商品", notes = "")
    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@ApiParam(value = "商品模型") @RequestBody @Valid Gds gds,
                       HttpServletResponse response) {
        Gds save = gdsService.save(gds);
        RestUtil.setHeaderLocation(response, save.getId());
    }

    @ApiOperation(value = "按ID修改商品", notes = "")
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@ApiParam(value = "商品ID") @PathVariable Long id,
                       @ApiParam(value = "商品模型") @RequestBody @Valid Gds gds) {
        gdsService.update(id, gds);
    }

    @ApiOperation(value = "按ID删除商品", notes = "")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@ApiParam(value = "商品ID") @PathVariable Long id) {
        gdsService.delete(id);
    }

    @ApiOperation(value = "按ID查询商品", notes = "")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Gds findOneByCategName(@ApiParam(value = "商品ID") @PathVariable Long id) {
        return gdsService.findOne(id);
    }

    @ApiOperation(value = "分页查询全部商品", notes = "分页参数示例:?page=0&size=3&sort=id,DESC")
    @RequestMapping(value = "", method = RequestMethod.GET)
    public Page<GdsSimpleDTO> findAllPageable(@ApiParam(value = "分页参数") @PageableDefault(size = 20) Pageable pageable,
                                              @ApiParam(value = "模糊查询") @RequestParam(required = false) String searchValue) {
        Page<Gds> gdses;
        if (searchValue == null) {
            gdses = gdsService.findAllPageable(pageable);
        } else {
            gdses = gdsService.searchGdsesPageable(pageable, searchValue);
        }

        Page<GdsSimpleDTO> dtoPage = gdses.map(gdsSimpleDTOConverter);
        return dtoPage;
    }

    @ApiOperation(value = "根据商品名模糊查询商品", notes = "分页参数示例:?page=0&size=3&sort=id,DESC")
    @RequestMapping(value = "/byname/{gdsName}", method = RequestMethod.GET)
    public Page<Gds> findByGdsNamePageable(@PathVariable String gdsName,
                                           @ApiParam(value = "分页参数") @PageableDefault(size = 20) Pageable pageable) {
        Page<Gds> allPageable = gdsService.findByGdsNamePageable(gdsName, pageable);
        return allPageable;
    }


    @ApiOperation(value = "创建商品与类别关联", notes = "")
    @RequestMapping(value = "/{gdsId}/gdses/{categId}", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void createCategGdsRela(@ApiParam(value = "商品ID") @PathVariable Long gdsId,
                                   @ApiParam(value = "类别ID") @PathVariable Long categId) {
        gdsService.createGdsCategRela(gdsId, categId);
    }

    @ApiOperation(value = "删除商品与类别关联", notes = "")
    @RequestMapping(value = "/{gdsId}/gdses/{categId}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCategGdsRela(@ApiParam(value = "商品ID") @PathVariable Long gdsId,
                                   @ApiParam(value = "类别ID") @PathVariable Long categId) {
        gdsService.deleteCategGdsRela(gdsId, categId);
    }

    @ApiOperation(value = "按ID修改商品状态", notes = "")
    @RequestMapping(value = "/{id}/status/{status}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateStatus(@ApiParam(value = "商品ID") @PathVariable Long id,
                             @ApiParam(value = "状态") @PathVariable boolean status) {
        gdsService.updateStatus(id,status);
    }


}
