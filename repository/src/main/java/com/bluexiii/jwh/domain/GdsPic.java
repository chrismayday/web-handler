package com.bluexiii.jwh.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by bluexiii on 16/8/25.
 */
@ApiModel("商品图片")
@Entity
public class GdsPic extends BaseEntity {

    @ApiModelProperty(value = "图片名称", required = true)
    @Column(nullable = false, length = 64)
    @NotNull(message = "名称不能为空")
    @Size(min = 2, max = 64, message = "名称长度范围为2-64位字符")
    private String picName;

    @ApiModelProperty(value = "图片文件名")
    @Column(length = 128)
    @Size(max = 128, message = "长度不能大于128位字符")
    private String picFile;

    @ApiModelProperty(value = "图片链接")
    @Column(length = 128)
    @Size(max = 128, message = "长度不能大于128位字符")
    private String picLink;

    @ApiModelProperty(value = "商品")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gds_id")
    @JsonIgnore
    private Gds gds;


    public GdsPic() {
    }

    public GdsPic(String picName) {
        this.picName = picName;
    }

    public String getPicName() {
        return picName;
    }

    public void setPicName(String picName) {
        this.picName = picName;
    }

    public String getPicFile() {
        return picFile;
    }

    public void setPicFile(String picFile) {
        this.picFile = picFile;
    }

    public String getPicLink() {
        return picLink;
    }

    public void setPicLink(String picLink) {
        this.picLink = picLink;
    }

    public Gds getGds() {
        return gds;
    }

    public void setGds(Gds gds) {
        this.gds = gds;
    }

    @Override
    public String toString() {
        return "GdsPic{" +
                "picName='" + picName + '\'' +
                ", picFile='" + picFile + '\'' +
                ", picLink='" + picLink + '\'' +
//                ", gds=" + gds +
                '}';
    }
}
