package com.example.groupbuy.dao;

import com.example.groupbuy.entity.GroupBuying;
import com.example.groupbuy.entity.Goods;
import com.example.groupbuy.entity.GroupBuying;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.Optional;

public interface GroupDao {
    Optional<GroupBuying> getGroupById(int id);

    void endGroup(int groupId);

    void deleteGroup(int groupId);
    GroupBuying save(GroupBuying groupBuying);


    Goods saveGoods(Goods goods);

    Set<GroupBuying> selectGroupsByTag(String category);

    Set<GroupBuying> findAll();
}
