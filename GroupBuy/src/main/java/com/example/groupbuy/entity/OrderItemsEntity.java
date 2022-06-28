package com.example.groupbuy.entity;

import javax.persistence.*;
import java.util.Objects;

/**
 * 订单项表
 */
@Entity
@Table(name = "orderitems", schema = "gpurchase")
public class OrderItemsEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "orderItemId")
    private Integer orderItemId;

    //商品ID
    @Basic
    @Column(name = "goodsId")
    private Integer goodsId;

    //商品数量
    @Basic
    @Column(name = "goodsNumber")
    private Integer goodsNumber;

    //所属订单ID
    @Basic
    @Column(name = "orderId")
    private Integer orderId;

//    //与订单的关系
//    @ManyToOne
//    @JoinColumn(name = "orderId", referencedColumnName = "orderId", nullable = false)
//    private OrdersEntity ordersByOrderId;
//
//    public Integer getOrderItemId() {
//        return orderItemId;
//    }

    public void setOrderItemId(Integer orderItemId) {
        this.orderItemId = orderItemId;
    }

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public Integer getGoodsNumber() {
        return goodsNumber;
    }

    public void setGoodsNumber(Integer goodsNumber) {
        this.goodsNumber = goodsNumber;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderItemsEntity that = (OrderItemsEntity) o;
        return Objects.equals(orderItemId, that.orderItemId) && Objects.equals(goodsId, that.goodsId) && Objects.equals(goodsNumber, that.goodsNumber) && Objects.equals(orderId, that.orderId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderItemId, goodsId, goodsNumber, orderId);
    }

//    public OrdersEntity getOrdersByOrderId() {
//        return ordersByOrderId;
//    }
//
//    public void setOrdersByOrderId(OrdersEntity ordersByOrderId) {
//        this.ordersByOrderId = ordersByOrderId;
//    }
}
