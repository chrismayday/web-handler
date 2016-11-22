package com.bluexiii.jwh.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by bluexiii on 2016/9/29.
 */
@ApiModel("首页统计信息")
public class SummaryDTO {
    @ApiModelProperty(value = "品牌数量")
    private long categCount;
    @ApiModelProperty(value = "机型数量")
    private long gdsCount;
    @ApiModelProperty(value = "注册用户数")
    private long userCount;
    @ApiModelProperty(value = "服务调用次数")
    private long requestCount;

    public long getCategCount() {
        return categCount;
    }

    public void setCategCount(long categCount) {
        this.categCount = categCount;
    }

    public long getGdsCount() {
        return gdsCount;
    }

    public void setGdsCount(long gdsCount) {
        this.gdsCount = gdsCount;
    }

    public long getUserCount() {
        return userCount;
    }

    public void setUserCount(long userCount) {
        this.userCount = userCount;
    }

    public long getRequestCount() {
        return requestCount;
    }

    public void setRequestCount(long requestCount) {
        this.requestCount = requestCount;
    }

    @Override
    public String toString() {
        return "SummaryDTO{" +
                "categCount=" + categCount +
                ", gdsCount=" + gdsCount +
                ", userCount=" + userCount +
                ", requestCount=" + requestCount +
                '}';
    }
}
