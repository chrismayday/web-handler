package com.bluexiii.jwh.dto;

import com.bluexiii.jwh.domain.GdsPic;

import java.util.HashSet;
import java.util.Set;

public class GdsSimpleWithPicDTO {
    private Long id;
    private String chnlId;
    private String gdsId;
    private String skuId;
    private String catId;
    private String shopLocaleCode;

    private String gdsName;
    private String gdsSecName;

    private String gdsPrice;
    private String gdsRealPrice;

    private String picLink;

    private Integer stockCount;
    private Integer orderDef;
    private Integer orderSale;

    private boolean status;

    private Set<GdsPic> gdsPics = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getChnlId() {
        return chnlId;
    }

    public void setChnlId(String chnlId) {
        this.chnlId = chnlId;
    }

    public String getGdsId() {
        return gdsId;
    }

    public void setGdsId(String gdsId) {
        this.gdsId = gdsId;
    }

    public String getSkuId() {
        return skuId;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }

    public String getCatId() {
        return catId;
    }

    public void setCatId(String catId) {
        this.catId = catId;
    }

    public String getShopLocaleCode() {
        return shopLocaleCode;
    }

    public void setShopLocaleCode(String shopLocaleCode) {
        this.shopLocaleCode = shopLocaleCode;
    }

    public String getGdsName() {
        return gdsName;
    }

    public void setGdsName(String gdsName) {
        this.gdsName = gdsName;
    }

    public String getGdsSecName() {
        return gdsSecName;
    }

    public void setGdsSecName(String gdsSecName) {
        this.gdsSecName = gdsSecName;
    }

    public String getGdsPrice() {
        return gdsPrice;
    }

    public void setGdsPrice(String gdsPrice) {
        this.gdsPrice = gdsPrice;
    }

    public String getGdsRealPrice() {
        return gdsRealPrice;
    }

    public void setGdsRealPrice(String gdsRealPrice) {
        this.gdsRealPrice = gdsRealPrice;
    }

    public String getPicLink() {
        return picLink;
    }

    public void setPicLink(String picLink) {
        this.picLink = picLink;
    }

    public Integer getStockCount() {
        return stockCount;
    }

    public void setStockCount(Integer stockCount) {
        this.stockCount = stockCount;
    }

    public Integer getOrderDef() {
        return orderDef;
    }

    public void setOrderDef(Integer orderDef) {
        this.orderDef = orderDef;
    }

    public Integer getOrderSale() {
        return orderSale;
    }

    public void setOrderSale(Integer orderSale) {
        this.orderSale = orderSale;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Set<GdsPic> getGdsPics() {
        return gdsPics;
    }

    public void setGdsPics(Set<GdsPic> gdsPics) {
        this.gdsPics = gdsPics;
    }

    @Override
    public String toString() {
        return "GdsSimpleDTO{" +
                "id=" + id +
                ", chnlId='" + chnlId + '\'' +
                ", gdsId='" + gdsId + '\'' +
                ", skuId='" + skuId + '\'' +
                ", catId='" + catId + '\'' +
                ", shopLocaleCode='" + shopLocaleCode + '\'' +
                ", gdsName='" + gdsName + '\'' +
                ", gdsSecName='" + gdsSecName + '\'' +
                ", gdsPrice='" + gdsPrice + '\'' +
                ", gdsRealPrice='" + gdsRealPrice + '\'' +
                ", picLink='" + picLink + '\'' +
                ", stockCount=" + stockCount +
                ", orderDef=" + orderDef +
                ", orderSale=" + orderSale +
                ", status=" + status +
                '}';
    }
}