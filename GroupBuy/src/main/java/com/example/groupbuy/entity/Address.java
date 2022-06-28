package com.example.groupbuy.entity;


import javax.persistence.*;

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
@Entity
@Getter
@Setter
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "addressId")
@Table(name = "addresses", schema = "gpurchase")
public class Address {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "addressId")
    private Integer addressId;

    //联系人姓名
    @Basic
    @Column(name = "receiver")
    private String receiver;

    //联系人电话
    @Basic
    @Column(name = "phone")
    private String phone;

    //联系人地区
    @Basic
    @Column(name = "region")
    private String region;

    //联系人详细地址
    @Basic
    @Column(name = "location")
    private String location;

    //地址的拥有用户，外键关联
    @ManyToOne(fetch= FetchType.EAGER)
    @JoinColumn(name = "ownerId")
    private User user;
}
