package com.example.groupbuy.entity;


import com.example.groupbuy.config.Comment;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;


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
import java.util.List;
import java.util.Set;
@Getter
@Setter
@Entity
@Table(name = "user")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "userId")
@Comment("用户表")
@ApiModel("用户信息")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    @Comment("用户主键")
    @ApiModelProperty(value = "用户主键")
    private Integer userId;

    @Basic
    @Column(name = "user_name")
    @Comment("用户姓名")
    @ApiModelProperty(value = "用户姓名")
    private String userName;

    //密码
    @Basic
    @Column(name = "password")
    @Comment("用户密码")
    @ApiModelProperty(value = "用户密码")
    private String password;

    //邮箱
    @Basic
    @Column(name = "email")
    @Comment("用户邮箱")
    @ApiModelProperty(value = "用户邮箱")
    private String email;

    //钱包
    @Basic
    @Column(name = "wallet")
    @Comment("钱包余额")
    @ApiModelProperty(value = "钱包余额")
    private BigDecimal wallet;


    //与地址簿的关系
    @JsonIgnore
    @OneToMany(mappedBy = "user",cascade=CascadeType.ALL,fetch = FetchType.EAGER)
    private Set<Address> addresses;

    //与订阅的关系
    @JsonIgnore
    @ManyToMany
    @JoinTable(name = "subscriptions",joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "group_id"))
    //1、关系维护端，负责多对多关系的绑定和解除
    //2、@JoinTable注解的name属性指定关联表的名字，joinColumns指定外键的名字，关联到关系维护端(User)
    //3、inverseJoinColumns指定外键的名字，要关联的关系被维护端(Authority)
    //4、其实可以不使用@JoinTable注解，默认生成的关联表名称为主表表名+下划线+从表表名，
    //即表名为
    //关联到主表的外键名：主表名+下划线+主表中的主键列名,即user_id
    //关联到从表的外键名：主表中用于关联的属性名+下划线+从表的主键列名,即authority_id
    //主表就是关系维护端对应的表，从表就是关系被维护端对应的表
    private Set<GroupBuying> groups;

    //与团购团长的关系一对多
    @JsonIgnore
    @OneToMany(fetch=FetchType.EAGER,mappedBy = "user",cascade = CascadeType.ALL)
    //级联保存、更新、删除、刷新;延迟加载。当删除用户，会级联删除该用户的所有创建了的团购
    //拥有mappedBy注解的实体类为关系被维护端
    //mappedBy="author"中的author是Article中的author属性
    private Set<GroupBuying> createGroups;//创建的团购列表


}
