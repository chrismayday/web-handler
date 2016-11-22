package com.bluexiii.jwh.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by bluexiii on 16/8/25.
 */
@ApiModel("类别")
@Entity
public class Categ extends BaseEntity {
    @ApiModelProperty(value = "类别名称", required = true)
    @Column(unique = true, nullable = false, length = 32)
    @NotNull(message = "名称不能为空")
    @Size(min = 2, max = 32, message = "名称长度范围为2-32位字符")
    private String categName;

    @ApiModelProperty(value = "类别描述")
    @Column(length = 32)
    @Size(max = 32, message = "长度不能大于32位字符")
    private String categDesc;

    @ApiModelProperty(value = "外部系统品牌ID")
    @Size(max = 32, message = "长度不能大于32位字符")
    @Column(length = 32)
    private String brandId;

    @ApiModelProperty(value = "列表页展示顺序")
    @Column
    private Integer listOrder;

    @ApiModelProperty(value = "主页展示顺序")
    @Column
    private Integer mainOrder;

    @ApiModelProperty(value = "图片名称")
    @Column(length = 32)
    @Size(max = 32, message = "长度不能大于32位字符")
    private String picName;

    @ApiModelProperty(value = "类别下商品")
    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "categs")
    private Set<Gds> gdses = new HashSet<>();

    public Categ() {
    }

    public Categ(String categName, String categDesc) {
        this.categName = categName;
        this.categDesc = categDesc;
    }

    public String getCategName() {
        return categName;
    }

    public void setCategName(String categName) {
        this.categName = categName;
    }

    public String getCategDesc() {
        return categDesc;
    }

    public void setCategDesc(String categDesc) {
        this.categDesc = categDesc;
    }

    public String getBrandId() {
        return brandId;
    }

    public void setBrandId(String brandId) {
        this.brandId = brandId;
    }

    public Integer getListOrder() {
        return listOrder;
    }

    public void setListOrder(Integer listOrder) {
        this.listOrder = listOrder;
    }

    public Integer getMainOrder() {
        return mainOrder;
    }

    public void setMainOrder(Integer mainOrder) {
        this.mainOrder = mainOrder;
    }

    public String getPicName() {
        return picName;
    }

    public void setPicName(String picName) {
        this.picName = picName;
    }

    public Set<Gds> getGdses() {
        return gdses;
    }

    public void setGdses(Set<Gds> gdses) {
        this.gdses = gdses;
    }

    @Override
    public String toString() {
        return "Categ{" +
                "id='" + getId() + '\'' +
                ", categName='" + categName + '\'' +
                ", categDesc='" + categDesc + '\'' +
                ", listOrder=" + listOrder +
                ", mainOrder=" + mainOrder +
                ", picName='" + picName + '\'' +
//                ", gdses=" + gdses +
                '}';
    }

}
