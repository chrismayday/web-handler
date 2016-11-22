package com.bluexiii.jwh.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * Created by bluexiii on 2016/9/29.
 */
@ApiModel("首页登录图表")
public class LoginChartDTO {
    @ApiModelProperty(value = "日期")
    private List<String> statDate;

    @ApiModelProperty(value = "登录次数")
    private List<Long> loginCount;

    @ApiModelProperty(value = "登录人数")
    private List<Long> ipCount;

    public List<String> getStatDate() {
        return statDate;
    }

    public void setStatDate(List<String> statDate) {
        this.statDate = statDate;
    }

    public List<Long> getLoginCount() {
        return loginCount;
    }

    public void setLoginCount(List<Long> loginCount) {
        this.loginCount = loginCount;
    }

    public List<Long> getIpCount() {
        return ipCount;
    }

    public void setIpCount(List<Long> ipCount) {
        this.ipCount = ipCount;
    }


    @Override
    public String toString() {
        return "LoginChartDTO{" +
                "statDate=" + statDate +
                ", loginCount=" + loginCount +
                ", ipCount=" + ipCount +
                '}';
    }
}
