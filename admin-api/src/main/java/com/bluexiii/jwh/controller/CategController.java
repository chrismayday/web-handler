package com.bluexiii.jwh.controller;

import com.bluexiii.jwh.component.GdsSimpleDTOConverter;
import com.bluexiii.jwh.domain.Categ;
import com.bluexiii.jwh.domain.Gds;
import com.bluexiii.jwh.service.CategService;
import com.bluexiii.jwh.util.RestUtil;
import com.bluexiii.jwh.dto.GdsSimpleDTO;
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
@RequestMapping(value = "/v1/categs")
@Api(value = "categ", description = "类别管理")
public class CategController {
    private static final Logger LOGGER = LoggerFactory.getLogger(CategController.class);
    @Autowired
    private CategService categService;
    @Autowired
    private GdsSimpleDTOConverter gdsSimpleDTOConverter;

    @ApiOperation(value = "创建类别", notes = "")
    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@ApiParam(value = "类别模型") @RequestBody @Valid Categ categ,
                       HttpServletResponse response) {
        Categ save = categService.save(categ);
        RestUtil.setHeaderLocation(response, save.getId());
    }

    @ApiOperation(value = "按ID修改类别", notes = "")
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@ApiParam(value = "类别ID") @PathVariable Long id,
                       @ApiParam(value = "类别模型") @RequestBody @Valid Categ categ) {
        categService.update(id, categ);
    }

    @ApiOperation(value = "按ID删除类别", notes = "")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@ApiParam(value = "类别ID") @PathVariable Long id) {
        categService.delete(id);
    }

    @ApiOperation(value = "按ID查询类别", notes = "")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Categ findOne(@ApiParam(value = "类别ID") @PathVariable Long id) {
        return categService.findOne(id);
    }

    @ApiOperation(value = "分页查询全部类别", notes = "分页参数示例:?page=0&size=3&sort=id,DESC")
    @RequestMapping(value = "", method = RequestMethod.GET)
    public Page<Categ> findAllPageable(@ApiParam(value = "分页参数") @PageableDefault(size = 20) Pageable pageable,
                                       @ApiParam(value = "模糊查询") @RequestParam(required = false) String searchValue) {
        Page<Categ> categs;
        if (searchValue == null || searchValue.equals("")) {
            categs = categService.findAllPageable(pageable);
        } else {
            categs = categService.searchCategsPageable(pageable, searchValue);
        }
        return categs;
    }

    @ApiOperation(value = "分页查询类别下商品", notes = "")
    @RequestMapping(value = "/{id}/gdses", method = RequestMethod.GET)
    public Page<GdsSimpleDTO> findGdsByCategIdPageable(@ApiParam(value = "类别ID") @PathVariable Long id,
                                                       @ApiParam(value = "分页参数") @PageableDefault(size = 20) Pageable pageable,
                                                       @ApiParam(value = "模糊查询") @RequestParam(required = false) String searchValue) {
        Page<Gds> gdses = null;
        if (searchValue == null || searchValue.equals("")) {
            gdses = categService.findGdsByCategIdPageable(id, pageable);
        } else {
            gdses = categService.searchGdsByCategIdPageable(pageable, id, searchValue);

        }
        Page<GdsSimpleDTO> dtoPage = gdses.map(gdsSimpleDTOConverter);
        return dtoPage;
    }

    @ApiOperation(value = "创建类别与商品关联", notes = "")
    @RequestMapping(value = "/{categId}/gdses/{gdsId}", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void createCategGdsRela(@ApiParam(value = "类别ID") @PathVariable Long categId,
                                   @ApiParam(value = "商品ID") @PathVariable Long gdsId) {
        categService.createCategGdsRela(categId, gdsId);
    }

    @ApiOperation(value = "删除类别与商品关联", notes = "")
    @RequestMapping(value = "/{categId}/gdses/{gdsId}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCategGdsRela(@ApiParam(value = "类别ID") @PathVariable Long categId,
                                   @ApiParam(value = "商品ID") @PathVariable Long gdsId) {
        LOGGER.debug("categId: {}", categId);
        LOGGER.debug("gdsId: {}", gdsId);

        categService.deleteCategGdsRela(categId, gdsId);
    }
}
