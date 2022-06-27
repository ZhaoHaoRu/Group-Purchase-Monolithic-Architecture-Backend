package com.example.groupbuy.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Objects;

/**
 * 订单表
 */
@Entity
@Table(name = "orders", schema = "gpurchase")
public class OrdersEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "orderId")
    private Integer orderId;

    //所属团购的ID
    @Basic
    @Column(name = "groupId")
    private Integer groupId;

    //所属用户的ID
    @Basic
    @Column(name = "ownerId")
    private Integer ownerId;

    /**
     * 订单状态 用于区分是购物车还是订单  0-未支付，表示购物车，1-支付，表示订单
     */
    @Basic
    @Column(name = "state")
    private boolean state;

    //下单时间
    @Basic
    @Column(name = "time")
    private Timestamp time;

    //配送地址所对应的ID
    @Basic
    @Column(name = "addressId")
    private Integer addressId;

//    //与订单表项的一对多关系
//    @OneToMany(mappedBy = "ordersByOrderId")
//    private Collection<OrderitemsEntity> orderitemsByOrderId;

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public Integer getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Integer ownerId) {
        this.ownerId = ownerId;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public Integer getAddressId() {
        return addressId;
    }

    public void setAddressId(Integer addressId) {
        this.addressId = addressId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrdersEntity that = (OrdersEntity) o;
        return state == that.state && Objects.equals(orderId, that.orderId) && Objects.equals(groupId, that.groupId) && Objects.equals(ownerId, that.ownerId) && Objects.equals(time, that.time) && Objects.equals(addressId, that.addressId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, groupId, ownerId, state, time, addressId);
    }

//    public Collection<OrderitemsEntity> getOrderitemsByOrderId() {
//        return orderitemsByOrderId;
//    }
//
//    public void setOrderitemsByOrderId(Collection<OrderitemsEntity> orderitemsByOrderId) {
//        this.orderitemsByOrderId = orderitemsByOrderId;
//    }
}
