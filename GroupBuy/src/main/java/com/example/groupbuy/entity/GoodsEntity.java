package com.example.groupbuy.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * 商品表
 */
@Entity
@Table(name = "goods", schema = "gpurchase")
public class GoodsEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "goodsId")
    private Integer goodsId;

    //商品所属的团ID，外键关联
    @Basic
    @Column(name = "groupId")
    private Integer groupId;

    //商品名称
    @Basic
    @Column(name = "goodsName")
    private String goodsName;

    //商品信息
    @Basic
    @Column(name = "goodsInfo")
    private String goodsInfo;

    //商品价格
    @Basic
    @Column(name = "price")
    private BigDecimal price;

    //商品库存
    @Basic
    @Column(name = "inventory")
    private int inventory;

    //商品图片
    @Basic
    @Column(name = "picture")
    private String picture;

//    //与团的外键关联
//    @ManyToOne
//    @JoinColumn(name = "groupId", referencedColumnName = "groupId", nullable = false)
//    private GroupEntity groupByGroupId;

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsInfo() {
        return goodsInfo;
    }

    public void setGoodsInfo(String goodsInfo) {
        this.goodsInfo = goodsInfo;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getInventory() {
        return inventory;
    }

    public void setInventory(int inventory) {
        this.inventory = inventory;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GoodsEntity that = (GoodsEntity) o;
        return inventory == that.inventory && Objects.equals(goodsId, that.goodsId) && Objects.equals(groupId, that.groupId) && Objects.equals(goodsName, that.goodsName) && Objects.equals(goodsInfo, that.goodsInfo) && Objects.equals(price, that.price) && Objects.equals(picture, that.picture);
    }

    @Override
    public int hashCode() {
        return Objects.hash(goodsId, groupId, goodsName, goodsInfo, price, inventory, picture);
    }

//    public GroupEntity getGroupByGroupId() {
//        return groupByGroupId;
//    }
//
//    public void setGroupByGroupId(GroupEntity groupByGroupId) {
//        this.groupByGroupId = groupByGroupId;
//    }
}
