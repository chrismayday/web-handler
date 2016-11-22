package com.bluexiii.jwh.domain;

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
@ApiModel("商品")
@Entity
public class Gds extends BaseEntity {

    @ApiModelProperty(value = "商品名称", required = true)
    @Column(nullable = false, length = 32)
    @NotNull(message = "名称不能为空")
    @Size(min = 2, max = 32, message = "名称长度范围为2-32位字符")
    private String gdsName;

    @ApiModelProperty(value = "商品别名")
    @Column(length = 128)
    @Size(max = 128, message = "长度不能大于128位字符")
    private String gdsSecName;

    @ApiModelProperty(value = "外部系统商品ID")
    @Size(max = 32, message = "长度不能大于32位字符")
    @Column(length = 32)
    private String gdsId;

    @ApiModelProperty(value = "外部系统型号ID")
    @Size(max = 32, message = "长度不能大于32位字符")
    @Column(length = 32)
    private String skuId;

    @ApiModelProperty(value = "外部系统类别ID")
    @Size(max = 32, message = "长度不能大于32位字符")
    @Column(length = 32)
    private String catId;

    @ApiModelProperty(value = "外部系统区域ID")
    @Size(max = 32, message = "长度不能大于32位字符")
    @Column(length = 32)
    private String shopLocaleCode;

    @ApiModelProperty(value = "零售价格")
    @Column
    private Integer gdsPrice;

    @ApiModelProperty(value = "提货价格")
    @Column
    private Integer gdsRealPrice;

    @ApiModelProperty(value = "库存")
    @Column
    private Integer stockCount;

    @ApiModelProperty(value = "综合排序")
    @Column
    private Integer orderDef;

    @ApiModelProperty(value = "销量排序")
    @Column
    private Integer orderSale;

    @ApiModelProperty(value = "归属类别")
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "gds_categ_rela",
            joinColumns = {@JoinColumn(name = "gds_id")},
            inverseJoinColumns = {@JoinColumn(name = "categ_id")})
    private Set<Categ> categs = new HashSet<>();

    @ApiModelProperty(value = "商品图片")
    @OneToMany(mappedBy = "gds", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<GdsPic> gdsPics = new HashSet<>();

    @ApiModelProperty(value = "商品详情")
    @OneToOne(optional = false, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "gds_detail_id", unique = true)
    private GdsDetail gdsDetail;

    @ApiModelProperty(value = "品牌")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id")
    private Brand brand;

    @ApiModelProperty(value = "店铺")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shop_id")
    private Shop shop;


    public Gds() {
    }

    public Gds(String gdsName) {
        this.gdsName = gdsName;
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

    public Integer getGdsPrice() {
        return gdsPrice;
    }

    public void setGdsPrice(Integer gdsPrice) {
        this.gdsPrice = gdsPrice;
    }

    public Integer getGdsRealPrice() {
        return gdsRealPrice;
    }

    public void setGdsRealPrice(Integer gdsRealPrice) {
        this.gdsRealPrice = gdsRealPrice;
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

    public Set<Categ> getCategs() {
        return categs;
    }

    public void setCategs(Set<Categ> categs) {
        this.categs = categs;
    }

    public Set<GdsPic> getGdsPics() {
        return gdsPics;
    }

    public void setGdsPics(Set<GdsPic> gdsPics) {
        //TODO 级联更新的set方法
        for (GdsPic gdsPic : gdsPics) {
            if (gdsPic.getGds() == null) {
                gdsPic.setGds(this);
            }
        }

        this.gdsPics = gdsPics;
    }

    public GdsDetail getGdsDetail() {
        return gdsDetail;
    }

    public void setGdsDetail(GdsDetail gdsDetail) {
//        if (gdsDetail.getGds() == null) {
//            gdsDetail.setGds(this);
//        }
        this.gdsDetail = gdsDetail;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    /**
     * 增加单个分类
     *
     * @param categ
     */
    public void addCateg(Categ categ) {
        Set<Categ> categs = this.categs;
        categs.add(categ);
        this.categs = categs;
    }

    /**
     * 移除单个分类
     *
     * @param categ
     */
    public void removeCateg(Categ categ) {
        Set<Categ> categs = this.categs;
        categs.remove(categ);
        this.categs = categs;
    }


    /**
     * 增加单个图片
     *
     * @param gdsPic
     */
    public void addPic(GdsPic gdsPic) {
        Set<GdsPic> gdsPics = this.getGdsPics();
        gdsPics.add(gdsPic);
        this.setGdsPics(gdsPics);
    }

    /**
     * 移除单个图片
     *
     * @param gdsPic
     */
    public void removePic(GdsPic gdsPic) {
        Set<GdsPic> gdsPics = this.getGdsPics();
        categs.remove(gdsPic);
        this.setGdsPics(gdsPics);
    }


    @Override
    public String toString() {
        return "Gds{" +
                "gdsName='" + gdsName + '\'' +
                ", gdsSecName='" + gdsSecName + '\'' +
                ", skuId=" + skuId +
                ", gdsPrice=" + gdsPrice +
                ", gdsRealPrice=" + gdsRealPrice +
                ", stockCount=" + stockCount +
                ", orderDef=" + orderDef +
                ", orderSale=" + orderSale +
//                ", categs=" + categs +
//                ", gdsPics=" + gdsPics +
//                ", gdsDetail=" + gdsDetail +
//                ", brand=" + brand +
//                ", shop=" + shop +
                '}';
    }
}
