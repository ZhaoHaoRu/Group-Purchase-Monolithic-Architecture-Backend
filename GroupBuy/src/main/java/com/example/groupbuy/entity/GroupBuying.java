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
@Table(name = "groupbuying")
public class GroupBuying {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "group_id")
    private Integer groupId;

    //团长ID，与用户表的userId外键关联
    @ManyToOne(fetch = FetchType.EAGER)//可选属性optional=false,表示author不能为空。
    @JoinColumn(name="user_id")//设置在article表中的关联字段(外键)
    private User user;//所属用户


    //团购名称
    @Basic
    @Column(name = "group_title")
    private String groupTitle;

    //团购信息
    @Basic
    @Column(name = "group_info")
    private String groupInfo;

    //配送方式
    @Basic
    @Column(name = "delivery")
    private String delivery;

    //团购开始时间
    @Basic
    @Column(name = "start_time")
    private Timestamp startTime;

    //团购持续时间
    @Basic
    @Column(name = "duration")
    private Integer duration;

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

    //和订单的多对一
    @OneToMany(mappedBy = "group",fetch = FetchType.EAGER)
    private Set<Orders> orders;
}
