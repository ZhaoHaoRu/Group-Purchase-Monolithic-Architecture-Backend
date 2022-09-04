package com.example.groupbuy.entity;

import lombok.Data;

import java.util.Date;

@Data
public class UserAuth<T> {
    private String id;
    private T userInfo;
    private Date expiration;
}
