package com.bluexiii.jwh.dto;

public class GdsDetailDTO {

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

    private String detailLink;
    private String detailHtml;

    private String specLink;
    private String specHtml;

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

    public String getDetailLink() {
        return detailLink;
    }

    public void setDetailLink(String detailLink) {
        this.detailLink = detailLink;
    }

    public String getDetailHtml() {
        return detailHtml;
    }

    public void setDetailHtml(String detailHtml) {
        this.detailHtml = detailHtml;
    }

    public String getSpecLink() {
        return specLink;
    }

    public void setSpecLink(String specLink) {
        this.specLink = specLink;
    }

    public String getSpecHtml() {
        return specHtml;
    }

    public void setSpecHtml(String specHtml) {
        this.specHtml = specHtml;
    }

    @Override
    public String toString() {
        return "GdsDetailDTO{" +
                "chnlId='" + chnlId + '\'' +
                ", gdsId='" + gdsId + '\'' +
                ", skuId='" + skuId + '\'' +
                ", catId='" + catId + '\'' +
                ", shopLocaleCode='" + shopLocaleCode + '\'' +
                ", gdsName='" + gdsName + '\'' +
                ", gdsSecName='" + gdsSecName + '\'' +
                ", gdsPrice='" + gdsPrice + '\'' +
                ", gdsRealPrice='" + gdsRealPrice + '\'' +
                ", picLink='" + picLink + '\'' +
                ", detailLink='" + detailLink + '\'' +
                ", detailHtml='" + detailHtml + '\'' +
                ", specLink='" + specLink + '\'' +
                ", specHtml='" + specHtml + '\'' +
                '}';
    }
}