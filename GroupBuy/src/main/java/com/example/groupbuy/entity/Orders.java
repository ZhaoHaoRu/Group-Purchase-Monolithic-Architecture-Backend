package com.example.groupbuy.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "orders")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "orderId")
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Integer orderId;

    //所属团购的ID
    @ManyToOne(fetch= FetchType.EAGER)
    @JoinColumn(name = "group_id")
    @ApiModelProperty(value = "用户信息")
    private GroupBuying group;

    //所属用户的ID
    @ManyToOne(fetch= FetchType.EAGER)
    @JoinColumn(name = "user_id")

    @ApiModelProperty(value = "用户信息")
    private User user;

    /**
     * 订单状态 用于区分是购物车还是订单  0-未支付，表示购物车，1-支付，表示订单 2-订单取消，已退款
     */
    @Basic
    @Column(name = "state")
    private Integer state;

    //下单时间
    @Basic
    @Column(name = "time")
    private Timestamp time;

    //配送地址所对应的ID
    @ManyToOne(fetch= FetchType.EAGER)
    @JoinColumn(name = "address_id")
    @ApiModelProperty(value = "配送地址")
    private Address address;

    //与订单表项的一对多关系
    @OneToMany(mappedBy = "orders",fetch=FetchType.EAGER,cascade = CascadeType.ALL)
    private Set<OrderItems> orderItems;
}
