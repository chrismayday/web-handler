package com.bluexiii.jwh.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import javax.validation.constraints.Size;

/**
 * Created by bluexiii on 16/8/25.
 */
@ApiModel("商品详情")
@Entity
public class GdsDetail extends BaseEntity {

    @ApiModelProperty(value = "详情介绍页面")
    @Column(length = 16777215, columnDefinition = "mediumtext")
    @Size(max = 16777215, message = "长度不能大于16777215位字符")
    private String detailHtml;

    @ApiModelProperty(value = "详情介绍链接")
    @Column(length = 128)
    @Size(max = 128, message = "长度不能大于128位字符")
    private String detailLink;

    @ApiModelProperty(value = "规格介绍页面")
    @Column(length = 16777215, columnDefinition = "mediumtext")
    @Size(max = 16777215, message = "长度不能大于16777215位字符")
    private String specHtml;

    @ApiModelProperty(value = "规格介绍链接")
    @Column(length = 128)
    @Size(max = 128, message = "长度不能大于128位字符")
    private String specLink;

//    @ApiModelProperty(value = "商品")
//    @OneToOne(mappedBy = "gdsDetail", fetch = FetchType.LAZY)
//    @JsonIgnore
//    private Gds gds;

    public GdsDetail() {
    }

    public String getDetailHtml() {
        return detailHtml;
    }

    public void setDetailHtml(String detailHtml) {
        this.detailHtml = detailHtml;
    }

    public String getDetailLink() {
        return detailLink;
    }

    public void setDetailLink(String detailLink) {
        this.detailLink = detailLink;
    }

    public String getSpecHtml() {
        return specHtml;
    }

    public void setSpecHtml(String specHtml) {
        this.specHtml = specHtml;
    }

    public String getSpecLink() {
        return specLink;
    }

    public void setSpecLink(String specLink) {
        this.specLink = specLink;
    }

//    public Gds getGds() {
//        return gds;
//    }
//
//    public void setGds(Gds gds) {
//        this.gds = gds;
//    }

    @Override
    public String toString() {
        return "GdsDetail{" +
                "detailHtml='" + detailHtml + '\'' +
                ", detailLink='" + detailLink + '\'' +
                ", specHtml='" + specHtml + '\'' +
                ", specLink='" + specLink + '\'' +
//                ", gds=" + gds +
                '}';
    }
}
