package com.example.groupbuy.entity;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import com.example.groupbuy.config.Comment;

import javax.persistence.*;
@Entity
@Getter
@Setter
@Comment("书籍表")
@Table(name = "address")
@ApiModel("地址信息")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id")
    @Comment("地址主键")
    @ApiModelProperty(value = "地址主键")
    private Integer addressId;

    //联系人姓名
    @Basic
    @Column(name = "receiver")
    @Comment("联系人姓名")
    @ApiModelProperty(value = "联系人姓名")
    private String receiver;

    //联系人电话
    @Basic
    @Column(name = "phone")
    @Comment("联系人电话")
    @ApiModelProperty(value = "联系人电话")
    private String phone;

    //联系人地区
    @Basic
    @Column(name = "region")
    @Comment("联系人地区")
    @ApiModelProperty(value = "联系人地区")
    private String region;

    //联系人详细地址
    @Basic
    @Column(name = "location")
    @Comment("联系人详细地址")
    @ApiModelProperty(value = "联系人详细地址")
    private String location;

    //地址的拥有用户，外键关联
    @JsonIgnore
    @ManyToOne(fetch= FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;
}
