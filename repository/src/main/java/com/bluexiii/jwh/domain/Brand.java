package com.bluexiii.jwh.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by bluexiii on 16/8/25.
 */
@ApiModel("品牌")
@Entity
public class Brand extends BaseEntity {

    @ApiModelProperty(value = "品牌名称",required = true)
    @Column(unique = true, nullable = false, length = 32)
    @NotNull(message = "名称不能为空")
    @Size(min = 2, max = 32, message = "名称长度范围为2-32位字符")
    private String brandName;

    @ApiModelProperty(value = "品牌描述")
    @Column(length = 32)
    @Size(max = 32, message = "长度不能大于32位字符")
    private String brandDesc;

    public Brand() {
    }

    public Brand(String brandName, String brandDesc) {
        this.brandName = brandName;
        this.brandDesc = brandDesc;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getBrandDesc() {
        return brandDesc;
    }

    public void setBrandDesc(String brandDesc) {
        this.brandDesc = brandDesc;
    }

    @Override
    public String toString() {
        return "Brand{" +
                "brandName='" + brandName + '\'' +
                ", brandDesc='" + brandDesc + '\'' +
                '}';
    }
}
