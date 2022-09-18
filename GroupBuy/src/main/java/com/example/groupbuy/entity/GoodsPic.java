package com.example.groupbuy.entity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import com.alibaba.fastjson.JSON;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

@Document
@Getter
@Setter
public class GoodsPic implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer goodsPicId;

    @Indexed(unique = true)
    private Integer goodsId;

    /**
     * base64
     */
    private String picture;
}
