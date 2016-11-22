package com.bluexiii.jwh.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;
import java.util.Map;

/**
 * Created by bluexiii on 2016/9/29.
 */
@ApiModel("文件上传返回信息")
public class UploadDTO {
    @ApiModelProperty(value = "成功标志")
    private boolean uploaded;
    @ApiModelProperty(value = "文件名")
    private String fileName;
    @ApiModelProperty(value = "文件URL")
    private String url;
    @ApiModelProperty(value = "报错信息")
    private Map<String,String> error;

    public boolean isUploaded() {
        return uploaded;
    }

    public void setUploaded(boolean uploaded) {
        this.uploaded = uploaded;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Map<String, String> getError() {
        return error;
    }

    public void setError(Map<String, String> error) {
        this.error = error;
    }

    @Override
    public String toString() {
        return "UploadDTO{" +
                "uploaded='" + uploaded + '\'' +
                ", fileName='" + fileName + '\'' +
                ", url='" + url + '\'' +
                ", error=" + error +
                '}';
    }
}
