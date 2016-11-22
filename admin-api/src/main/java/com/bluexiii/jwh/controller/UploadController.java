package com.bluexiii.jwh.controller;

import com.bluexiii.jwh.dto.UploadDTO;
import com.bluexiii.jwh.service.UploadService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by bluexiii on 17/10/2016.
 */
@Api(value = "upload", description = "文件上传")
@Controller
@RequestMapping(value = "/v1/upload")
public class UploadController {
    private static final Logger LOGGER = LoggerFactory.getLogger(UploadController.class);

    private final UploadService uploadService;

    @Autowired
    public UploadController(UploadService uploadService) {
        this.uploadService = uploadService;
    }

    @ApiOperation(value = "图片上传", notes = "")
    @RequestMapping(value = "/images", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<UploadDTO> uploadFile(@RequestParam("upload") MultipartFile multipartFile) {
        UploadDTO uploadDTO = uploadService.uploadImage(multipartFile);
        if (uploadDTO.isUploaded()) {
            return new ResponseEntity<>(uploadDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(uploadDTO, HttpStatus.CONFLICT);
        }
    }
}
