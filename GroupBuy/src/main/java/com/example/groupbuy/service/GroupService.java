package com.example.groupbuy.service;

import com.example.groupbuy.entity.Goods;
import com.example.groupbuy.entity.GroupBuying;
import com.example.groupbuy.entity.VO.ChangeGroup;
import com.example.groupbuy.utils.messageUtils.Message;
import com.alibaba.fastjson.JSONObject;
import com.example.groupbuy.entity.GroupBuying;
import com.example.groupbuy.entity.User;
import com.example.groupbuy.utils.messageUtils.Message;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Set;


public interface GroupService {
    Message<GroupBuying> getGroupById(int id);

    Message<String> endGroup(int groupId);

    Message<String> deleteGroup(int groupId);

    Message<String> changeGroup(ChangeGroup groupBuying);

    Message<GroupBuying> createGroup(JSONObject groupBuying);

    Message<User> collectGroup(Integer userId, Integer groupId);

    Message<Set<GroupBuying>> getGroupByTag(String category);

    Message<Set<GroupBuying>> getAllGroup();

    Message<Set<GroupBuying>> getCollectedGroup(Integer userId);

    Set<Goods> FilterByInventory(Set<Goods> goodsList);

    Message<Boolean> judgeCollected(Integer userId, Integer groupId);
}
