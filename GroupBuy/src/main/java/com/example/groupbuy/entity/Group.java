package com.example.groupbuy.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Set;
@Getter
@Setter
@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "groupId")
@Table(name = "group", schema = "gpurchase")
public class Group {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "groupId")
    private Integer groupId;

    //团长ID，与用户表的userId外键关联
    @ManyToOne(cascade={CascadeType.MERGE,CascadeType.REFRESH},optional=false)//可选属性optional=false,表示author不能为空。删除文章，不影响用户
    @JoinColumn(name="headerId")//设置在article表中的关联字段(外键)
    private User user;//所属作者

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

    @ManyToMany(mappedBy = "groups")
    private Set<User> users;

    //和商品的多对一
    @OneToMany(mappedBy = "group",fetch = FetchType.EAGER)
    private Set<Goods> goods;
}
