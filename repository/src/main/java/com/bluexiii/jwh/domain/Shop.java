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
@ApiModel("店铺")
@Entity
public class Shop extends BaseEntity {

    @ApiModelProperty(value = "店铺名称", required = true)
    @Column(nullable = false, length = 32)
    @NotNull(message = "名称不能为空")
    @Size(min = 2, max = 32, message = "名称长度范围为2-32位字符")
    private String shopName;

    @ApiModelProperty(value = "店铺详情")
    @Column(length = 256)
    @Size(max = 256, message = "长度不能大于256位字符")
    private String shopDesc;

    public Shop() {
    }

    public Shop(String shopName) {
        this.shopName = shopName;
    }

    public Shop(String shopName, String shopDesc) {
        this.shopName = shopName;
        this.shopDesc = shopDesc;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getShopDesc() {
        return shopDesc;
    }

    public void setShopDesc(String shopDesc) {
        this.shopDesc = shopDesc;
    }

    @Override
    public String toString() {
        return "Shop{" +
                "shopName='" + shopName + '\'' +
                ", shopDesc='" + shopDesc + '\'' +
                '}';
    }
}
