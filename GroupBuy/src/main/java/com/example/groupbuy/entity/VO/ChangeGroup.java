package com.example.groupbuy.entity.VO;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Data
public class ChangeGroup {

    private Integer groupId;

    private String groupTitle;

    private String groupInfo;

    private String category;

    private String startTime;

    private Integer duration;

    private List<ChangeGoods> goods;
}
