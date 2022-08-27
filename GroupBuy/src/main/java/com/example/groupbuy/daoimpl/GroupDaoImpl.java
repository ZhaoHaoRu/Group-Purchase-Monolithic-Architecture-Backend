package com.example.groupbuy.daoimpl;

import com.example.groupbuy.dao.GroupDao;
import com.example.groupbuy.entity.Goods;
import com.example.groupbuy.entity.GoodsPic;
import com.example.groupbuy.entity.GroupBuying;
import com.example.groupbuy.entity.GroupPic;
import com.example.groupbuy.repository.*;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.*;

@Repository
public class GroupDaoImpl implements GroupDao {
    @Resource
    GroupRepository groupRepository;
    @Resource
    GoodsRepository goodsRepository;

    @Resource
    OrderItemsRepository orderItemsRepository;

    @Resource
    GroupPicRepository groupPicRepository;

    @Resource
    GoodsPicRepository goodsPicRepository;

    @Override
    public GroupBuying handleGroupPictures(GroupBuying groupBuying) {
        GroupPic groupPic;
        if(groupBuying.getPicture() == null) {
            groupPic = groupPicRepository.findByGroupId(groupBuying.getGroupId());
            if(groupPic != null) {
                groupBuying.setPicture(groupPic.getPicture());
            }
        }
        groupBuying.setGoods(handleGoodsPictures(groupBuying.getGoods()));
        return groupBuying;
    }

    @Override
    public Set<Goods> handleGoodsPictures(Set<Goods> goodsSet) {
        GoodsPic goodsPic;
        Iterator<Goods> iterator = goodsSet.iterator();
        Set<Goods> newSet = new HashSet<>();
        while(iterator.hasNext()) {
            Goods goods = iterator.next();
            if(goods.getPicture() == null) {
                goodsPic = goodsPicRepository.findByGoodsId(goods.getGoodsId());
                if(goodsPic != null) {
                    goods.setPicture(goodsPic.getPicture());
                }
            }
            newSet.add(goods);
        }
//        for(Goods goods:goodsSet) {
//            if(goods.getPicture() == null) {
//                goodsPic = goodsPicRepository.findByGoodsId(goods.getGoodsId());
//                if(goodsPic != null) {
//                    goods.setPicture(goodsPic.getPicture());
//                }
//            }
//        }
        return newSet;
    }

    @Override
    public Goods handleGoodsPicture(Goods goods) {
        if(goods.getPicture() == null) {
            GoodsPic goodsPic = goodsPicRepository.findByGoodsId(goods.getGoodsId());
            if(goodsPic != null) {
                goods.setPicture(goodsPic.getPicture());
            }
        }
        return goods;
    }

    @Override
    public Optional<GroupBuying> getGroupById(int id) {
        Optional<GroupBuying> groupBuying = groupRepository.findById(id);
        if(groupBuying.isPresent() && groupBuying.get().getPicture() == null) {
            return Optional.of(handleGroupPictures(groupBuying.get()));
        }
        return groupBuying;
    }

    @Override
    public GoodsPic saveGoodsPic(Integer goodsId, String picture) {
        GoodsPic goodsPic;
        if((goodsPic = goodsPicRepository.findByGoodsId(goodsId)) != null) {
            goodsPic.setPicture(picture);
            return goodsPicRepository.save(goodsPic);
        } else {
            GoodsPic goodsPic1 = new GoodsPic();
            goodsPic1.setGoodsId(goodsId);
            goodsPic1.setPicture(picture);
            return goodsPicRepository.save(goodsPic1);
        }
    }

    @Override
    public GroupPic saveGroupPic(Integer groupId, String picture) {
        GroupPic groupPic;
        // TODO: 这里假设groupPic不会重新设置，所以只要数据库中查询到了相应的group的Pic就不会重新设置
        if((groupPic = groupPicRepository.findByGroupId(groupId)) != null) {
//            System.out.println(groupPic.getGroupPicId());
//            System.out.println(groupPic.getGroupId());
//            groupPic.setPicture(picture);
//            return groupPicRepository.save(groupPic);
            return groupPic;
        } else {
            GroupPic groupPic1 = new GroupPic();
            System.out.println("new group picture");
            System.out.println(groupId);
            groupPic1.setGroupId(groupId);
            groupPic1.setPicture(picture);
            return groupPicRepository.save(groupPic1);
        }
    }

    @Override
    public void updatePopularity(Integer newPopularity, Integer groupId) {
        groupRepository.updatePopularity(newPopularity, groupId);
    }

    @Override
    public GroupBuying save(GroupBuying groupBuying) {
        GroupBuying newGroup = groupRepository.save(groupBuying);
        /**
         * 这里首先要对于团购图片进行存储，注意此处并不会对于团购商品的图片进行操作
         */
        if(newGroup != null && newGroup.getPicture() != null && !newGroup.getPicture().equals("already set")) {
            saveGroupPic(newGroup.getGroupId(), groupBuying.getPicture());
            newGroup.setPicture("already set");
        }
        return newGroup;
    }

    @Override
    public Goods saveGoods(Goods goods) {
        Goods newGoods = goodsRepository.save(goods);
        /**
         * 对于团购商品图片进行存储
         */
        if(newGoods != null) {
            saveGoodsPic(newGoods.getGoodsId(), goods.getPicture());
        }
        return newGoods;
//        if(goods != null) {
//            saveGoodsPic(goods.getGoodsId(), goods.getPicture());
//        }
//        return goodsRepository.save(goods);
    }

    @Override
    public Set<GroupBuying> selectGroupsByTag(String category) {
        Set<GroupBuying> groupBuyings = groupRepository.selectGroupsByTag(category);
        Set<GroupBuying> newSet = new HashSet<>();
        /**
         * 读取团购对应的图片,并添加到团购对象中
         */
        for(GroupBuying groupBuying : groupBuyings) {
            groupBuying = handleGroupPictures(groupBuying);
            newSet.add(groupBuying);
        }
        return newSet;
    }

    @Override
    public Set<GroupBuying> findAll() {
        Set<GroupBuying> groupBuyings = groupRepository.selectAllByStateIsNot();
        Set<GroupBuying> newSet = new HashSet<>();
        /**
         * 读取团购对应的图片,并添加到团购对象中
         */
        for(GroupBuying groupBuying : groupBuyings) {
            groupBuying = handleGroupPictures(groupBuying);
            newSet.add(groupBuying);
        }
        return newSet;
    }

    @Override
    public void endGroup(int groupId) {
        GroupBuying groupBuying = getGroupById(groupId).get();
        groupBuying.setDuration(0);
        groupRepository.save(groupBuying);
    }

    @Override
    public void deleteGroup(int groupId) {
        groupRepository.updateGroupState(groupId);
    }

    @Override
    public void updateGroup(Integer groupId, String groupTitle, String groupInfo, String category,
                                Timestamp startTime, Integer duration){
        groupRepository.updateGroup(category, duration, groupInfo, groupTitle, startTime, groupId);
    }

    @Override
    public Set<Goods> getGoodsByGroupId(Integer groupId){
        Set<Goods> goodsSet = goodsRepository.getGoodsByGroupId(groupId);
        return handleGoodsPictures(goodsSet);
    }

    @Override
    public void updateGoods(Integer goodsId, String goodsInfo, BigDecimal price, Integer inventory){
        goodsRepository.updateGoods(goodsInfo, inventory, price, goodsId);
    }

    @Override
    public void updateCartItems(Integer orderId, Integer oldGoodsId, Integer newGoodsId){
        orderItemsRepository.updateCartItems(newGoodsId, orderId, oldGoodsId);
    }

    @Override
    public Set<GroupBuying> queryAllSecKillGoods() {
        Set<GroupBuying> groupBuyings = groupRepository.queryAllSecKillGoods();
        Set<GroupBuying> newSet = new HashSet<>();
        /**
         * 读取团购对应的图片,并添加到团购对象中
         */
        for(GroupBuying groupBuying : groupBuyings) {
            groupBuying = handleGroupPictures(groupBuying);
            newSet.add(groupBuying);
        }
        return newSet;
    }

    @Override
    public Goods getGoodsById(Integer goodsId) {
        Goods goods = goodsRepository.findByGoodsId(goodsId);
        if(goods != null && goods.getPicture() == null) {
            GoodsPic goodsPic = goodsPicRepository.findByGoodsId(goods.getGoodsId());
            if(goodsPic != null) {
                goods.setPicture(goodsPic.getPicture());
            }
        }
        return goods;
    }

    @Override
    public Integer updateInventory(Integer goodsNumber, Integer goodsId) {
        return goodsRepository.updateInventory(goodsNumber, goodsId);
    }
}
