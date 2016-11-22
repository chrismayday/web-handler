package com.bluexiii.jwh.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * Created by bluexiii on 16/8/25.
 */
@ApiModel("请求日志")
@Entity
public class RequestLog extends BaseEntity {

    @ApiModelProperty(value = "请求地址")
    @Column(length = 256)
    private String requestUri;

    @ApiModelProperty(value = "请求处理时长")
    @Column
    private Long processTime;

    @ApiModelProperty(value = "用户名")
    @Column(length = 256)
    private String userName;

    @ApiModelProperty(value = "IP地址")
    @Column(length = 256)
    private String remoteAddr;

    public RequestLog() {
    }

    public RequestLog(String requestUri, Long processTime, String userName, String remoteAddr) {
        this.requestUri = requestUri;
        this.processTime = processTime;
        this.userName = userName;
        this.remoteAddr = remoteAddr;
    }

    public String getRequestUri() {
        return requestUri;
    }

    public void setRequestUri(String requestUri) {
        this.requestUri = requestUri;
    }

    public Long getProcessTime() {
        return processTime;
    }

    public void setProcessTime(Long processTime) {
        this.processTime = processTime;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRemoteAddr() {
        return remoteAddr;
    }

    public void setRemoteAddr(String remoteAddr) {
        this.remoteAddr = remoteAddr;
    }

    @Override
    public String toString() {
        return "RequestLog{" +
                "requestUri='" + requestUri + '\'' +
                ", processTime=" + processTime +
                ", userName='" + userName + '\'' +
                ", remoteAddr='" + remoteAddr + '\'' +
                '}';
    }
}
