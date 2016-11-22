package com.bluexiii.jwh.service;

import com.bluexiii.jwh.exception.BusinessException;
import com.bluexiii.jwh.dto.UploadDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Pattern;

/**
 * Created by bluexiii on 17/10/2016.
 */
@Service
@ConfigurationProperties(prefix = "config")
public class UploadService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UploadService.class);

    private String picBasePath;
    private String picBaseUrl;

    public UploadService() {
    }

    public UploadService(String picBasePath, String picBaseUrl) {
        this.picBasePath = picBasePath;
        this.picBaseUrl = picBaseUrl;
    }

    public String getPicBasePath() {
        return picBasePath;
    }

    public void setPicBasePath(String picBasePath) {
        this.picBasePath = picBasePath;
    }

    public String getPicBaseUrl() {
        return picBaseUrl;
    }

    public void setPicBaseUrl(String picBaseUrl) {
        this.picBaseUrl = picBaseUrl;
    }


    //文件上传
    public UploadDTO uploadImage(MultipartFile multipartFile) {
        //以ckeditor的格式返回
        UploadDTO uploadDTO = new UploadDTO();
        //保存
        try {
            InputStream is = multipartFile.getInputStream();
            String filename = changeFilename(multipartFile.getOriginalFilename());
            String savePath = this.picBasePath;
            String saveDate = getSaveDate();
            Path targetPath = new File(savePath + File.separator + saveDate, filename).toPath();
            Files.copy(is, targetPath);

            uploadDTO.setUploaded(true);
            uploadDTO.setFileName(filename);
            uploadDTO.setUrl(this.picBaseUrl + "/" + saveDate + "/" + filename);
        } catch (IOException e) {
            e.printStackTrace();
            uploadDTO.setUploaded(false);
            Map<String, String> errorMap = new LinkedHashMap();
            errorMap.put("message", "文件保存失败");
            uploadDTO.setError(errorMap);
        }
        return uploadDTO;
    }

    //文件重命名
    public String changeFilename(String originalFilename) {
        String suffix = (originalFilename.lastIndexOf(".") > 0) ? originalFilename.substring(originalFilename.lastIndexOf(".")) : "";

        if (!Pattern.matches(".(gif|jpe?g|png)", suffix)) {
            throw new BusinessException(901,"图片类型不正确");
        }
        String prefix = UUID.randomUUID().toString().toUpperCase().replace("-", "");
        return prefix + suffix;
    }

    //获取日期
    public String getSaveDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDate today = LocalDate.now();
        String path = today.format(formatter);

        //创建子目录
        String savePath = this.picBasePath;
        if (!savePath.endsWith(File.separator)) {
            savePath += File.separator;
        }
        savePath += path;
        File file = new File(savePath);
        if (!file.exists()) {
            file.mkdirs();
        }

        return path;
    }
}
