package com.example.groupbuy.entity;

import com.example.groupbuy.config.Comment;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;



@Entity
@Getter
@Setter
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "goodsId")
@Table(name = "goods")
@Comment("商品表")
@ApiModel("商品信息")
public class Goods {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "goods_id")
    @Comment("商品主键")
    @ApiModelProperty(value = "商品主键")
    private Integer goodsId;

    //商品所属的团ID，外键关联
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")
    @Comment("商品所属团购ID")
    @ApiModelProperty(value = "商品所属团购ID")
    private GroupBuying group;

    //商品名称
    @Basic
    @Column(name = "goods_name")
    @Comment("商品名称")
    @ApiModelProperty(value = "商品名称")
    private String goodsName;

    //商品信息
    @Basic
    @Column(name = "goods_info")
    @Comment("商品信息")
    @ApiModelProperty(value = "商品信息")
    private String goodsInfo;

    //商品价格
    @Basic
    @Column(name = "price")
    @Comment("商品价格")
    @ApiModelProperty(value = "商品价格")
    private BigDecimal price;

    //商品库存
    @Basic
    @Column(name = "inventory")
    @Comment("商品库存")
    @ApiModelProperty(value = "商品库存")
    private int inventory;

    //商品图片
    @Basic
    @Column(name = "picture")
    @Comment("商品图片")
    @ApiModelProperty(value = "商品图片")
    private String picture;


}
