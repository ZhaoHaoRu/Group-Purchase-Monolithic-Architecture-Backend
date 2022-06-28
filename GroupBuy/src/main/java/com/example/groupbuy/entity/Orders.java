package com.example.groupbuy.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Set;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
@Getter
@Setter
@Entity
@Table(name = "orders")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "orderId")
public class Orders {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "orderId")
    private Integer orderId;

    //所属团购的ID
    @ManyToOne(fetch= FetchType.EAGER)
    @JoinColumn(name = "groupId")

    @ApiModelProperty(value = "用户信息")
    private Group group;

    //所属用户的ID
    @ManyToOne(fetch= FetchType.EAGER)
    @JoinColumn(name = "ownerId")

    @ApiModelProperty(value = "用户信息")
    private User user;

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
    @ManyToOne(fetch= FetchType.EAGER)
    @JoinColumn(name = "addressId")

    @ApiModelProperty(value = "配送地址")
    private Address address;

    //与订单表项的一对多关系
    @OneToMany(mappedBy = "orders",fetch=FetchType.EAGER,cascade = CascadeType.ALL)
    private Set<OrderItems> orderItems;
}
