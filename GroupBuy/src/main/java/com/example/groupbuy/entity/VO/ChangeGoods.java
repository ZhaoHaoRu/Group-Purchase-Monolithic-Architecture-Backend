package com.example.groupbuy.entity.VO;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ChangeGoods {

    private Integer goodsId;

    private String goodsInfo;

    private BigDecimal price;

    private Integer inventory;

    private String goodsName;

    private String picture;

    private Integer group;
}
