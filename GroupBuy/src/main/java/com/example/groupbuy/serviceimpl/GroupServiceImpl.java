package com.example.groupbuy.serviceimpl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.groupbuy.dao.GroupDao;
import com.example.groupbuy.dao.OrderDao;
import com.example.groupbuy.dao.UserDao;
import com.example.groupbuy.entity.Goods;
import com.example.groupbuy.entity.GroupBuying;
import com.example.groupbuy.entity.Orders;
import com.example.groupbuy.entity.User;
import com.example.groupbuy.entity.VO.ChangeGoods;
import com.example.groupbuy.entity.VO.ChangeGroup;
import com.example.groupbuy.service.GroupService;
import com.example.groupbuy.utils.JpaUtils;
import com.example.groupbuy.utils.messageUtils.Message;
import com.example.groupbuy.utils.messageUtils.MessageUtil;
import com.example.groupbuy.utils.redis.RedisUtil;
import com.google.common.collect.Sets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.groupbuy.service.*;

import javax.annotation.Resource;
import javax.swing.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.*;

@Service
public class GroupServiceImpl implements GroupService {
    @Resource
    GroupDao groupDao;
    @Resource
    UserDao userDao;
    @Resource
    OrderService orderService;

    @Resource
    OrderDao orderDao;

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public Message<GroupBuying> getGroupById(int id) {
        Optional<GroupBuying> groupBuying = groupDao.getGroupById(id);
        if (groupBuying.isPresent()) {
            //过滤掉inventory为-1的
            GroupBuying result = groupBuying.get();
            Set<Goods> tmp = result.getGoods();
            tmp = FilterByInventory(tmp);
            result.setGoods(tmp);
            return MessageUtil.createMessage(MessageUtil.LOGIN_SUCCESS_CODE, MessageUtil.SUCCESS, result);
        } else {
            return MessageUtil.createMessage(MessageUtil.MISS_GROUP_CODE, MessageUtil.MISS_GROUP_MSG, null);
        }
    }

    @Override
    public Message<GroupBuying> createGroup(JSONObject groupBuying) {
        GroupBuying group = new GroupBuying();
        group.setGroupTitle(groupBuying.get("groupTitle").toString());
        group.setGroupInfo(groupBuying.get("groupInfo").toString());
        int userId = Integer.parseInt(groupBuying.get("userId").toString());
        User user = userDao.findById(userId);
        group.setUser(user);
        group.setDelivery(groupBuying.get("delivery").toString());
        group.setStartTime(Timestamp.valueOf(groupBuying.get("startTime").toString()));
        group.setDuration(Integer.parseInt(groupBuying.get("duration").toString()));
        group.setPicture(groupBuying.get("picture").toString());
        group.setCategory(groupBuying.get("category").toString());
        group.setState(Integer.parseInt(groupBuying.get("state").toString()));
        GroupBuying groupAfterCreate = groupDao.save(group);

        JSONArray goodsArray = groupBuying.getJSONArray("goods");
        if (goodsArray.size() > 0) {
            for (int i = 0; i < goodsArray.size(); ++i) {
                Map<String, String> obj = (Map<String, String>) goodsArray.get(i);
                Goods goods = new Goods();
                goods.setGoodsInfo(obj.get("goodsInfo"));
                goods.setGoodsName(obj.get("goodsName"));
                goods.setPicture(obj.get("picture"));
                BigDecimal bd = new BigDecimal(obj.get("price"));
                goods.setPrice(bd);
                goods.setInventory(Integer.parseInt(obj.get("inventory")));
                goods.setGroup(groupAfterCreate);
                Goods goods1 = groupDao.saveGoods(goods);
                /**
                 * 将秒杀团购的商品加入缓存
                 */
                addNewProduct(goods);
            }
        }
        GroupBuying newGroup = groupDao.save(groupAfterCreate);

//        /**
//         * 将秒杀团购的商品加入缓存
//         */
//        if(newGroup.getState() == 2) {
//            Set<Goods> goodsSet = newGroup.getGoods();
//            for (Goods goods : goodsSet) {
//                addNewProduct(goods);
//            }
//        }

        if (newGroup != null) {
            return MessageUtil.createMessage(MessageUtil.SUCCESS_CODE, MessageUtil.SUCCESS, newGroup);
        } else {
            return MessageUtil.createMessage(MessageUtil.CREATE_GROUP_ERROR_CODE, MessageUtil.CREATE_GROUP_ERROR, null);
        }
    }

    @Override
    public Message<User> collectGroup(Integer userId, Integer groupId) {
        Optional<GroupBuying> group = groupDao.getGroupById(groupId);
        if (!group.isPresent()) {
            return MessageUtil.createMessage(MessageUtil.MISS_GROUP_CODE, MessageUtil.MISS_GROUP_MSG, null);
        }
        User user = userDao.findById(userId);
        if (user == null) {
            return MessageUtil.createMessage(MessageUtil.MISS_USER_CODE, MessageUtil.MISS_USER_MSG, null);
        }
        Set<GroupBuying> collects = user.getGroups();

        // 这里的逻辑是如果是已经收藏的团购，这样相当于取消收藏
        if (collects.contains(group.get())) {
            collects.remove(group.get());
            /**
             * 对于用户的喜好历史进行修改
             */
            String category = group.get().getCategory();
            userDao.updateLiking(-1, userId, category);
            /**
             * 对于团购的受欢迎程度进行修改
             */
            groupDao.updatePopularity(-1, groupId);
        }
        else {
            collects.add(group.get());
            /**
             * 对于用户的喜好历史进行修改
             */
            userDao.updateLiking(1, userId, group.get().getCategory());
            /**
             * 对于团购的受欢迎程度进行修改
             */
            groupDao.updatePopularity(1, groupId);
        }
        user.setGroups(collects);
        User userAfterSave = userDao.save(user);
        userAfterSave.setCreateGroups(null);
        userAfterSave.setAddresses(null);
        return MessageUtil.createMessage(MessageUtil.SUCCESS_CODE, MessageUtil.SUCCESS, userAfterSave);
    }

    @Override
    public Message<Set<GroupBuying>> getGroupByTag(String category) {
        Set<GroupBuying> groups = groupDao.selectGroupsByTag(category);
        if (groups != null) {
            //过滤掉inventory=-1的
            for (GroupBuying group:groups){
                Set<Goods> tmp = group.getGoods();
                tmp = FilterByInventory(tmp);
                group.setGoods(tmp);
            }
            return MessageUtil.createMessage(MessageUtil.SUCCESS_CODE, MessageUtil.SUCCESS, groups);
        } else {
            return MessageUtil.createMessage(MessageUtil.MISS_TAG_CODE, MessageUtil.MISS_TAG_MSG, null);
        }
    }

    @Override
    public Message<Set<GroupBuying>> getAllGroup() {
        Set<GroupBuying> groups = groupDao.findAll();
        //过滤掉inventory=-1的
        for (GroupBuying group:groups){
            Set<Goods> tmp = group.getGoods();
            tmp = FilterByInventory(tmp);
            group.setGoods(tmp);
        }
        return MessageUtil.createMessage(MessageUtil.SUCCESS_CODE, MessageUtil.SUCCESS, groups);
    }

    @Override
    public Message<Set<GroupBuying>> getCollectedGroup(Integer userId) {
        User user = userDao.findById(userId);
        if (user == null)
            return MessageUtil.createMessage(MessageUtil.MISS_USER_CODE, MessageUtil.MISS_USER_MSG, null);

        // 这里的group还是没有图片的
        Set<GroupBuying> groups = user.getGroups();

        // 此处只会选择还有效的团购
        /** NOTICE！
         * 对于set中元素进行remove,要用迭代器遍历，要不就会报错
         */
        Iterator<GroupBuying> iterator = groups.iterator();
        while(iterator.hasNext()) {
            GroupBuying group = iterator.next();
            if (group.getState() == 0)
                iterator.remove();
        }
//        for (GroupBuying group : groups) {
//            if (group.getState() == 0)
//                groups.remove(group);
//        }

        //过滤掉inventory=-1的
        /**
         * 为团购从mongoDB中获取相应的图片
         */
        Set<GroupBuying> newGroups = new HashSet<>();
        for (GroupBuying group:groups){
            Set<Goods> tmp = group.getGoods();
            tmp = FilterByInventory(tmp);
            group.setGoods(tmp);
            newGroups.add(groupDao.handleGroupPictures(group));
        }

        return MessageUtil.createMessage(MessageUtil.SUCCESS_CODE, MessageUtil.SUCCESS, groups);
    }


    @Override
    public Message<String> endGroup(int groupId) {
        groupDao.endGroup(groupId);
        return MessageUtil.createMessage(MessageUtil.LOGIN_SUCCESS_CODE, MessageUtil.SUCCESS);
    }


    @Override
    public Message<String> deleteGroup(int groupId) {
        Message<String> result = orderService.deleteOrderByGroupId(groupId);
        if (result.getStatus() != 1)
            return MessageUtil.createMessage(MessageUtil.FAIL_CODE, MessageUtil.FAIL);
        groupDao.deleteGroup(groupId);
        return MessageUtil.createMessage(MessageUtil.LOGIN_SUCCESS_CODE, MessageUtil.SUCCESS);
    }


    @Override
    public Message<String> changeGroup(ChangeGroup groupBuying) {
        Integer groupId = groupBuying.getGroupId();
        String groupTitle = groupBuying.getGroupTitle();
        String groupInfo = groupBuying.getGroupInfo();
        String category = groupBuying.getCategory();
        Integer duration = groupBuying.getDuration();
        //这个goodsList接受的是前端传回来的goodsList所以不会有inventory为-1的，多次修改应该是可以的
        List<ChangeGoods> goodsList = groupBuying.getGoods();
        //时间戳转换
        String StrTime = groupBuying.getStartTime();
        Timestamp startTime = Timestamp.valueOf(StrTime);

        //更新团购信息
        groupDao.updateGroup(groupId, groupTitle, groupInfo, category, startTime, duration);

        /**
         * TODO：对于缓存中的团购信息进行修改，如果bug太多就不加这个了
         */
        GroupBuying fullGroup = groupDao.getGroupById(groupId).get();
        if(fullGroup == null)
            return MessageUtil.createMessage(MessageUtil.FAIL_CODE, MessageUtil.FAIL);
        boolean isSecKill = (fullGroup.getState() == 2);

        //更新商品
        for (int i = 0; i < goodsList.size(); ++i) {
            ChangeGoods newGoods = goodsList.get(i);
            Goods oldGoods = groupDao.getGoodsById(newGoods.getGoodsId());
            //如果商品价格不改变，那么可以在原商品上面直接更新
            if (newGoods.getPrice().compareTo(oldGoods.getPrice())==0) {
                groupDao.updateGoods(newGoods.getGoodsId(), newGoods.getGoodsInfo(), newGoods.getPrice(), newGoods.getInventory());
                /**
                 *  对于缓存中的秒杀商品进行更新
                 */
                if(isSecKill) {
                    updateProduct(newGoods);
                }
            } else {
                //原商品的库存变为-1
                groupDao.updateGoods(oldGoods.getGoodsId(), oldGoods.getGoodsInfo(), oldGoods.getPrice(), -1);

                //插入新商品
                Goods goods = new Goods();
                //保证修改的信息存入了
                goods.setGoodsInfo(newGoods.getGoodsInfo());
                goods.setInventory(newGoods.getInventory());
                goods.setPrice(newGoods.getPrice());
                //保证原来的一些信息不变
                goods.setGroup(oldGoods.getGroup());
                goods.setGoodsName(oldGoods.getGoodsName());
                goods.setPicture(oldGoods.getPicture());
                Goods goodsAfterSave = groupDao.saveGoods(goods);

                /**
                 * 对于新的秒杀商品进行更新
                 */
                addNewProduct(goodsAfterSave);
                //更新所有的购物车
                List<Orders> CartList = orderDao.getGroupAllCarts(groupId);
                for (int j = 0; j < CartList.size(); ++j) {
                    Orders cart = CartList.get(j);
                    Integer oldGoodsId = oldGoods.getGoodsId();
                    Integer newGoodsId = goods.getGoodsId();
                    groupDao.updateCartItems(cart.getOrderId(), oldGoodsId, newGoodsId);
                }
            }
        }
        return MessageUtil.createMessage(MessageUtil.SUCCESS_CODE, MessageUtil.SUCCESS);
    }

    @Override
    public Set<Goods> FilterByInventory(Set<Goods> sourceList){
        List<Goods> goodsList = new ArrayList<>(sourceList);
        Set<Goods> newSet = Sets.newHashSet();
        for (int i=0; i<goodsList.size(); ++i){
            Goods goods = goodsList.get(i);
            if (goods.getInventory()>=0){
                newSet.add(goods);
            }
        }
        return newSet;
    }

    @Override
    public Message<Boolean> judgeCollected(Integer userId, Integer groupId) {
        User user = userDao.findById(userId);
        if(user == null) {
            return MessageUtil.createMessage(MessageUtil.FAIL_CODE, MessageUtil.FAIL, false);
        }
        Set<GroupBuying> groups = user.getGroups();
        for(GroupBuying group : groups) {
            if(group.getGroupId() == groupId)
                return MessageUtil.createMessage(MessageUtil.SUCCESS_CODE, MessageUtil.SUCCESS, true);
        }
        return MessageUtil.createMessage(MessageUtil.FAIL_CODE, MessageUtil.FAIL, false);
    }

    @Override
    public void updateProduct(ChangeGoods goods) {
        String goodsStr = goods.getGoodsId().toString();
        redisUtil.del(goodsStr);
        redisUtil.getRedisTemplate().opsForValue().set(goodsStr, String.valueOf(goods.getInventory()));
//        if(goods.getInventory() <= 0) {
//            localOverMap.put(goods.getGoodsId().toString(), true);
//        } else {
//            localOverMap.put(goods.getGoodsId().toString(), false);
//        }
    }

    @Override
    public void addNewProduct(Goods goods) {
        String goodsStr = goods.getGoodsId().toString();
        redisUtil.getRedisTemplate().opsForValue().set(goodsStr, String.valueOf(goods.getInventory()));
        redisUtil.getRedisTemplate().opsForValue().set("price_" + goodsStr, goods.getPrice().toString());
    }

}
