package com.example.groupbuy.entity;

import javax.persistence.*;
import java.util.Objects;

/**
 * 地址簿
 */

@Entity
@Table(name = "addresses", schema = "gpurchase")
public class AddressesEntity {
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
    @Basic
    @Column(name = "ownerId")
    private Integer ownerId;

//    @ManyToOne
//    @JoinColumn(name = "ownerId", referencedColumnName = "userId", nullable = false)
//    private UsersEntity usersByOwnerId;

    public Integer getAddressId() {
        return addressId;
    }

    public void setAddressId(Integer addressId) {
        this.addressId = addressId;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Integer getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Integer ownerId) {
        this.ownerId = ownerId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AddressesEntity that = (AddressesEntity) o;
        return Objects.equals(addressId, that.addressId) && Objects.equals(receiver, that.receiver) && Objects.equals(phone, that.phone) && Objects.equals(region, that.region) && Objects.equals(location, that.location) && Objects.equals(ownerId, that.ownerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(addressId, receiver, phone, region, location, ownerId);
    }

//    public UsersEntity getUsersByOwnerId() {
//        return usersByOwnerId;
//    }
//
//    public void setUsersByOwnerId(UsersEntity usersByOwnerId) {
//        this.usersByOwnerId = usersByOwnerId;
//    }
}
