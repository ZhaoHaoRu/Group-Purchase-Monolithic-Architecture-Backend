package com.example.groupbuy.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;



@Entity
@Getter
@Setter
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "goodsId")
@Table(name = "goods")
public class Goods {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "goods_id")
    private Integer goodsId;

    //商品所属的团ID，外键关联
    @ManyToOne
    @JoinColumn(name = "group_id")
    private GroupBuying group;

    //商品名称
    @Basic
    @Column(name = "goods_name")
    private String goodsName;

    //商品信息
    @Basic
    @Column(name = "goods_info")
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


}
