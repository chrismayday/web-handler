package com.bluexiii.jwh.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by bluexiii on 16/8/25.
 */
@ApiModel("应用部署")
@Entity
public class Deploy extends BaseEntity {

    @ApiModelProperty(value = "应用名称", required = true)
    @Column(nullable = false, length = 32)
    @NotNull(message = "名称不能为空")
    @Size(min = 2, max = 32, message = "名称长度范围为2-32位字符")
    private String deployName;

    @ApiModelProperty(value = "应用介绍")
    @Column(length = 256)
    @Size(max = 256, message = "长度不能大于256位字符")
    private String deployDesc;

    @ApiModelProperty(value = "应用类型")
    @Column(length = 32)
    @NotNull(message = "应用类型不能为空")
    @Size(max = 32, message = "长度不能大于32位字符")
    private String type;

    @ApiModelProperty(value = "IP")
    @Column(length = 32)
    @NotNull(message = "IP不能为空")
    @Size(max = 32, message = "长度不能大于32位字符")
    private String ip;

    @ApiModelProperty(value = "端口")
    @Column(length = 32)
    @NotNull(message = "端口不能为空")
    @Size(max = 32, message = "长度不能大于32位字符")
    private String port;

    @ApiModelProperty(value = "缓存刷新URL")
    @Column(length = 128)
    @Size(max = 128, message = "长度不能大于128位字符")
    private String refreshUrl;

    public Deploy() {
    }

    public Deploy(String deployName, String deployDesc, String type, String ip, String port, String refreshUrl) {
        this.deployName = deployName;
        this.deployDesc = deployDesc;
        this.type = type;
        this.ip = ip;
        this.port = port;
        this.refreshUrl = refreshUrl;
    }

    public String getDeployName() {
        return deployName;
    }

    public void setDeployName(String deployName) {
        this.deployName = deployName;
    }

    public String getDeployDesc() {
        return deployDesc;
    }

    public void setDeployDesc(String deployDesc) {
        this.deployDesc = deployDesc;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getRefreshUrl() {
        return refreshUrl;
    }

    public void setRefreshUrl(String refreshUrl) {
        this.refreshUrl = refreshUrl;
    }

    @Override
    public String toString() {
        return "Deploy{" +
                "deployName='" + deployName + '\'' +
                ", deployDesc='" + deployDesc + '\'' +
                ", type='" + type + '\'' +
                ", ip='" + ip + '\'' +
                ", port='" + port + '\'' +
                ", refreshUrl='" + refreshUrl + '\'' +
                '}';
    }
}
