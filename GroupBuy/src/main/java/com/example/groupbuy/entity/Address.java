package com.example.groupbuy.entity;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
@Entity
@Getter
@Setter
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "addressId")
@Table(name = "address")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id")
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
    @JoinColumn(name = "user_id")
    private User user;
}
