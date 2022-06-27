package com.example.groupbuy.entity;

import javax.persistence.*;
import java.util.Objects;

/**
 * 用户订阅的团购
 */
@Entity
@Table(name = "subscriptions", schema = "gpurchase")
public class SubscriptionsEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Integer id;

    //订阅该团购的用户ID
    @Basic
    @Column(name = "userId")
    private Integer userId;

    //被订阅的团购ID
    @Basic
    @Column(name = "groupId")
    private Integer groupId;


//    @ManyToOne
//    @JoinColumn(name = "userId", referencedColumnName = "userId", nullable = false)
//    private UsersEntity usersByUserId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SubscriptionsEntity that = (SubscriptionsEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(userId, that.userId) && Objects.equals(groupId, that.groupId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, groupId);
    }

//    public UsersEntity getUsersByUserId() {
//        return usersByUserId;
//    }
//
//    public void setUsersByUserId(UsersEntity usersByUserId) {
//        this.usersByUserId = usersByUserId;
//    }
}
