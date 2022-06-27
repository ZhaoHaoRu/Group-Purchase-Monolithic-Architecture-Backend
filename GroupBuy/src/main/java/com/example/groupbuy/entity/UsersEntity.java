package com.example.groupbuy.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Objects;

/**
 * 用户表
 */
@Entity
@Table(name = "users", schema = "gpurchase")
public class UsersEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "userId")
    private Integer userId;

    //用户名
    @Basic
    @Column(name = "userName")
    private String userName;

    //密码
    @Basic
    @Column(name = "password")
    private String password;

    //邮箱
    @Basic
    @Column(name = "email")
    private String email;

    //钱包
    @Basic
    @Column(name = "wallet")
    private BigDecimal wallet;

//    //与地址簿的关系
//    @OneToMany(mappedBy = "usersByOwnerId")
//    private Collection<AddressesEntity> addressesByUserId;
//
//    //与订阅的关系
//    @OneToMany(mappedBy = "usersByUserId")
//    private Collection<SubscriptionsEntity> subscriptionsByUserId;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public BigDecimal getWallet() {
        return wallet;
    }

    public void setWallet(BigDecimal wallet) {
        this.wallet = wallet;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UsersEntity that = (UsersEntity) o;
        return Objects.equals(userId, that.userId) && Objects.equals(userName, that.userName) && Objects.equals(password, that.password) && Objects.equals(email, that.email) && Objects.equals(wallet, that.wallet);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, userName, password, email, wallet);
    }

//    public Collection<AddressesEntity> getAddressesByUserId() {
//        return addressesByUserId;
//    }
//
//    public void setAddressesByUserId(Collection<AddressesEntity> addressesByUserId) {
//        this.addressesByUserId = addressesByUserId;
//    }
//
//    public Collection<SubscriptionsEntity> getSubscriptionsByUserId() {
//        return subscriptionsByUserId;
//    }
//
//    public void setSubscriptionsByUserId(Collection<SubscriptionsEntity> subscriptionsByUserId) {
//        this.subscriptionsByUserId = subscriptionsByUserId;
//    }
}
