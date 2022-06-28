package com.example.groupbuy.entity;

import com.example.groupbuy.config.Comment;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
@Getter
@Setter
@Entity
@Table(name = "order_item")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "orderItemId")
@Comment("订单详情信息表")
@ApiModel("订单详情信息")
public class OrderItems {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "orderItemId")
    private Integer orderItemId;

    //商品ID
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "goodsId")
    @Comment("商品主键")
    @ApiModelProperty(value = "商品信息")
    private Goods good;

    //商品数量
    @Basic
    @Column(name = "goodsNumber")
    private Integer goodsNumber;

    //所属订单ID

    //与订单的关系
    @ManyToOne
    @JoinColumn(name = "orderId")
    @Comment("订单主键")
    @ApiModelProperty(value = "订单信息")
    private Orders orders;
}
