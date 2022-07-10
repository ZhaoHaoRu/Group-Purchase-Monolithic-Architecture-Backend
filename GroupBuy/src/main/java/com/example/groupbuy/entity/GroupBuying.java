package com.example.groupbuy.entity;

import com.example.groupbuy.config.Comment;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@Comment("团购信息表")
@ApiModel("团购详情信息")
public class GroupBuying {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "group_id")
    @Comment("团购详情主键")
    @ApiModelProperty(value = "订单详情主键")
    private Integer groupId;

    //团长ID，与用户表的userId外键关联
    @ManyToOne(fetch = FetchType.EAGER)//可选属性optional=false,表示author不能为空。
    @JoinColumn(name="user_id")//设置在article表中的关联字段(外键)
    @Comment("团购团长")
    @ApiModelProperty(value = "团购团长")
    private User user;//所属用户


    //团购名称
    @Basic
    @Column(name = "group_title")
    @Comment("团购名称")
    @ApiModelProperty(value = "团购名称")
    private String groupTitle;

    //团购信息
    @Basic
    @Column(name = "group_info")
    @Comment("团购信息")
    @ApiModelProperty(value = "团购信息")
    private String groupInfo;

    //配送方式
    @Basic
    @Column(name = "delivery")
    @Comment("配送方式")
    @ApiModelProperty(value = "配送方式")
    private String delivery;

    //团购开始时间
    @Basic
    @Column(name = "start_time")
    @Comment("团购开始时间")
    @ApiModelProperty(value = "团购开始时间")
    private Timestamp startTime;

    //团购持续时间
    @Basic
    @Column(name = "duration")
    @Comment("团购持续小时")
    @ApiModelProperty(value = "团购持续小时")
    private Integer duration;

    //团购图片
    @Basic
    @Column(name = "picture")
    @Comment("团购图片")
    @ApiModelProperty(value = "团购图片")
    private String picture;

    /**
     * 团购状态： 0-团购已取消    1-普通团购   2-秒杀团购   3-团购已结束
     */
    @Basic
    @Column(name = "state")
    @Comment("团购状态")
    @ApiModelProperty(value = "团购状态")
    private Integer state;

    // 团购类型：水果鲜花，肉禽蛋，水产海鲜，乳品烘培，酒水饮料
    @Basic
    @Column(name = "category")
    @Comment("团购类型")
    @ApiModelProperty(value = "团购类型")
    private String category;

    @JsonIgnore
    @ManyToMany(mappedBy = "groups")
    private Set<User> users;

    //和商品的多对一
    @OneToMany(mappedBy = "group",fetch = FetchType.EAGER)
    private Set<Goods> goods;

    //和订单的多对一
    @JsonIgnore
    @OneToMany(mappedBy = "group",fetch = FetchType.EAGER)
    private Set<Orders> orders;
}
