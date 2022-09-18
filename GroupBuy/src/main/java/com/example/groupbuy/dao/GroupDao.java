package com.example.groupbuy.dao;

import com.example.groupbuy.entity.*;
import com.example.groupbuy.entity.GroupBuying;
import com.example.groupbuy.service.GroupService;
import io.lettuce.core.dynamic.annotation.Param;

import javax.swing.*;
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

    /**
     * 找出团购对应的团购图片（储存在mongoDB中）
     * @param groupBuying
     * @return
     */
    GroupBuying handleGroupPictures(GroupBuying groupBuying);

    /**
     * 找出团购商品对应的商品图片（储存在mongoDB中）
     * @param goodsSet
     * @return
     */
    Set<Goods> handleGoodsPictures(Set<Goods> goodsSet);

    /**
     * 找出单个商品对应的图片
     * @param goods
     * @return
     */
    Goods handleGoodsPicture(Goods goods);

    /**
     * 保存团购商品对应的图片，如果已经存在该商品，进行覆盖
     * @param goodsId
     * @param picture
     * @return
     */
    GoodsPic saveGoodsPic(Integer goodsId, String picture);

    /**
     * 保存团购对应的图片，如果已经存在该团购，进行覆盖
     * @param groupId
     * @param picture
     * @return
     */
    GroupPic saveGroupPic(Integer groupId, String picture);

    /**
     * 更新团购的受欢迎程度
     * @param newPopularity
     * @param groupId
     */
    void updatePopularity(Integer newPopularity, Integer groupId);

    void updateGroup(Integer groupId, String groupTitle, String groupInfo, String category,
                     Timestamp startTime, Integer duration);

    Set<Goods> getGoodsByGroupId(Integer groupId);

    void updateGoods(Integer goodsId, String goodsInfo, BigDecimal price, Integer inventory);

    void updateCartItems(Integer orderId, Integer oldGoodsId, Integer newGoodsId);

    Set<GroupBuying> queryAllSecKillGoods();

    Goods getGoodsById(Integer goodsId);

    Integer updateInventory(Integer goodsNumber, Integer goodsId);


}
