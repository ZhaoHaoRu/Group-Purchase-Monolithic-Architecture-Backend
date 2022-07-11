package com.example.groupbuy.dao;

import com.example.groupbuy.entity.GroupBuying;
import com.example.groupbuy.entity.Goods;
import com.example.groupbuy.entity.GroupBuying;

import java.math.BigDecimal;
import java.sql.Timestamp;
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

    void updateGroup(Integer groupId, String groupTitle, String groupInfo, String category,
                     Timestamp startTime, Integer duration, String delivery);

    List<Goods> getGoodsByGroupId(Integer groupId);

    void updateGoods(Integer goodsId, String goodsInfo, BigDecimal price, Integer inventory);

    void updateCartItems(Integer orderId, Integer oldGoodsId, Integer newGoodsId);
}
