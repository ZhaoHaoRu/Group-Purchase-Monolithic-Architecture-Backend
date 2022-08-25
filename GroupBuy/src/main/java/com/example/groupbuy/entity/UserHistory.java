package com.example.groupbuy.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "user_history")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "orderId")
public class UserHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "history_id")
    private Integer historyId;

    @Basic
    @Column(name = "category")
    @ApiModelProperty(value = "团购类型")
    private String category;

    @ManyToOne(fetch= FetchType.EAGER)
    @JoinColumn(name = "user_id")
    @ApiModelProperty(value = "用户信息")
    private User user;

    @Basic
    @Column(name = "liking")
    @ApiModelProperty(value = "喜爱程度")
    private Integer liking;

}
