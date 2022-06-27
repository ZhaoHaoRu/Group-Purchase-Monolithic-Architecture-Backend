package com.example.groupbuy.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

/**
 *   团购表
 */
@Entity
@Table(name = "group", schema = "gpurchase")
public class GroupEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "groupId")
    private Integer groupId;

    //团长ID，与用户表的userId外键关联
    @Basic
    @Column(name = "headerId")
    private Integer headerId;

    //团购名称
    @Basic
    @Column(name = "groupTitle")
    private String groupTitle;

    //团购信息
    @Basic
    @Column(name = "groupInfo")
    private String groupInfo;

    //配送方式
    @Basic
    @Column(name = "delivery")
    private String delivery;

    //团购开始时间
    @Basic
    @Column(name = "startTime")
    private Timestamp startTime;

    //团购持续时间
    @Basic
    @Column(name = "duration")
    private Timestamp duration;

    //团购图片
    @Basic
    @Column(name = "picture")
    private String picture;

    /**
     * 团购状态： 0-团购已取消    1-普通团购   2-秒杀团购
     */
    @Basic
    @Column(name = "state")
    private Integer state;

    // 团购类型：水果鲜花，肉禽蛋，水产海鲜，乳品烘培，酒水饮料
    @Basic
    @Column(name = "category")
    private String category;

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public Integer getHeaderId() {
        return headerId;
    }

    public void setHeaderId(Integer headerId) {
        this.headerId = headerId;
    }

    public String getGroupTitle() {
        return groupTitle;
    }

    public void setGroupTitle(String groupTitle) {
        this.groupTitle = groupTitle;
    }

    public String getGroupInfo() {
        return groupInfo;
    }

    public void setGroupInfo(String groupInfo) {
        this.groupInfo = groupInfo;
    }

    public String getDelivery() {
        return delivery;
    }

    public void setDelivery(String delivery) {
        this.delivery = delivery;
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public Timestamp getDuration() {
        return duration;
    }

    public void setDuration(Timestamp duration) {
        this.duration = duration;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GroupEntity that = (GroupEntity) o;
        return Objects.equals(groupId, that.groupId) && Objects.equals(headerId, that.headerId) && Objects.equals(groupTitle, that.groupTitle) && Objects.equals(groupInfo, that.groupInfo) && Objects.equals(delivery, that.delivery) && Objects.equals(startTime, that.startTime) && Objects.equals(duration, that.duration) && Objects.equals(picture, that.picture) && Objects.equals(state, that.state) && Objects.equals(category, that.category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(groupId, headerId, groupTitle, groupInfo, delivery, startTime, duration, picture, state, category);
    }
}
