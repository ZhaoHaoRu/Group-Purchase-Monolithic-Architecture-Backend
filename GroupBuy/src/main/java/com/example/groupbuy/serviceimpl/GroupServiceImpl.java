package com.example.groupbuy.serviceimpl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.groupbuy.dao.GroupDao;
import com.example.groupbuy.dao.UserDao;
import com.example.groupbuy.entity.Goods;
import com.example.groupbuy.entity.GroupBuying;
import com.example.groupbuy.entity.User;
import com.example.groupbuy.service.GroupService;
import com.example.groupbuy.utils.JpaUtils;
import com.example.groupbuy.utils.messageUtils.Message;
import com.example.groupbuy.utils.messageUtils.MessageUtil;
import org.springframework.stereotype.Service;
import com.example.groupbuy.service.*;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Service
public class GroupServiceImpl implements GroupService {
    @Resource
    GroupDao groupDao;
    @Resource
    UserDao userDao;
    @Resource
    OrderService orderService;

    @Override
    public Message<GroupBuying> getGroupById(int id) {
        Optional<GroupBuying> groupBuying =  groupDao.getGroupById(id);
        if(groupBuying.isPresent()) {
            return MessageUtil.createMessage(MessageUtil.LOGIN_SUCCESS_CODE, MessageUtil.SUCCESS, groupBuying.get());
        } else {
            return MessageUtil.createMessage(MessageUtil.MISS_GROUP_CODE, MessageUtil.MISS_GROUP_MSG, null);
        }
    }

    @Override
    public Message<GroupBuying> createGroup(JSONObject groupBuying) {
        GroupBuying group = new GroupBuying();
        group.setGroupTitle(groupBuying.get("groupTitle").toString());
        group.setGroupInfo(groupBuying.get("groupInfo").toString());
        int userId =  Integer.parseInt(groupBuying.get("userId").toString());
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
        if(goodsArray.size() > 0) {
            for(int i = 0; i < goodsArray.size(); ++i) {
                Map<String, String> obj= (Map<String, String>) goodsArray.get(i);
                Goods goods = new Goods();
                goods.setGoodsInfo(obj.get("goodsInfo"));
                goods.setGoodsName(obj.get("goodsName"));
                goods.setPicture(obj.get("picture"));
                BigDecimal bd= new BigDecimal(obj.get("price"));
                goods.setPrice(bd);
                goods.setInventory(Integer.parseInt(obj.get("inventory")));
                goods.setGroup(groupAfterCreate);
                groupDao.saveGoods(goods);
            }
        }
        GroupBuying newGroup = groupDao.save(groupAfterCreate);
        if(newGroup != null) {
            return MessageUtil.createMessage(MessageUtil.SUCCESS_CODE, MessageUtil.SUCCESS, newGroup);
        } else {
            return MessageUtil.createMessage(MessageUtil.CREATE_GROUP_ERROR_CODE, MessageUtil.CREATE_GROUP_ERROR, null);
        }
    }

    @Override
    public Message<User> collectGroup(Integer userId, Integer groupId) {
        Optional<GroupBuying> group = groupDao.getGroupById(groupId);
        if(!group.isPresent()) {
            return MessageUtil.createMessage(MessageUtil.MISS_GROUP_CODE, MessageUtil.MISS_GROUP_MSG, null);
        }
        User user = userDao.findById(userId);
        if(user == null) {
            return MessageUtil.createMessage(MessageUtil.MISS_USER_CODE, MessageUtil.MISS_USER_MSG, null);
        }
        Set<GroupBuying> collects = user.getGroups();

        // 这里的逻辑是如果是已经收藏的团购，这样相当于取消收藏
        if(collects.contains(group.get()))
            collects.remove(group.get());
        else
            collects.add(group.get());
        user.setGroups(collects);
        User userAfterSave = userDao.save(user);
        userAfterSave.setCreateGroups(null);
        userAfterSave.setAddresses(null);
        return MessageUtil.createMessage(MessageUtil.SUCCESS_CODE, MessageUtil.SUCCESS, userAfterSave);
    }

    @Override
    public Message<Set<GroupBuying>> getGroupByTag(String category) {
        Set<GroupBuying> groups = groupDao.selectGroupsByTag(category);
        if(groups != null) {
            return MessageUtil.createMessage(MessageUtil.SUCCESS_CODE, MessageUtil.SUCCESS, groups);
        } else {
            return MessageUtil.createMessage(MessageUtil.MISS_TAG_CODE, MessageUtil.MISS_TAG_MSG, null);
        }
    }

    @Override
    public Message<Set<GroupBuying>> getAllGroup() {
        Set<GroupBuying> groups = groupDao.findAll();
        return MessageUtil.createMessage(MessageUtil.SUCCESS_CODE, MessageUtil.SUCCESS, groups);
    }

    @Override
    public Message<Set<GroupBuying>> getCollectedGroup(Integer userId) {
        User user = userDao.findById(userId);
        if (user == null)
            return MessageUtil.createMessage(MessageUtil.MISS_USER_CODE, MessageUtil.MISS_USER_MSG, null);

        Set<GroupBuying> groups = user.getGroups();

        // 此处只会选择还有效的团购
        for (GroupBuying group:groups) {
            if(group.getState() == 0)
                groups.remove(group);
        }
        return MessageUtil.createMessage(MessageUtil.SUCCESS_CODE, MessageUtil.SUCCESS, groups);
    }


    @Override
    public Message<String> endGroup(int groupId){
        groupDao.endGroup(groupId);
        return MessageUtil.createMessage(MessageUtil.LOGIN_SUCCESS_CODE,MessageUtil.SUCCESS);
    }


    @Override
    public Message<String> deleteGroup(int groupId){
//        Message<String> result = orderService.deleteOrderByGroupId(groupId);
//        if(result.getStatus() != 1)
//            return MessageUtil.createMessage(MessageUtil.FAIL_CODE, MessageUtil.FAIL);
        groupDao.deleteGroup(groupId);
        return MessageUtil.createMessage(MessageUtil.LOGIN_SUCCESS_CODE,MessageUtil.SUCCESS);
    }


    @Override
    public  Message<String> changeGroup(JSONObject groupBuying){

        int groupId =  groupBuying.getInteger("groupId");
        GroupBuying groupById = groupDao.getGroupById(groupId).orElseThrow(() -> new IllegalArgumentException("错误"));

        GroupBuying newGroup = groupById;

        if(groupBuying.containsKey("groupTitle"))
            newGroup.setGroupTitle(groupBuying.getString("groupTitle"));
        if(groupBuying.containsKey("groupInfo"))
            newGroup.setGroupInfo(groupBuying.getString("groupInfo"));
        if(groupBuying.containsKey("groupInfo"))
            newGroup.setDelivery(groupBuying.getString("groupInfo"));
        if(groupBuying.containsKey("groupInfo"))
            newGroup.setDuration(groupBuying.getInteger("duration"));
        if(groupBuying.containsKey("picture"))
            newGroup.setPicture(groupBuying.getString("picture"));
        if(groupBuying.containsKey("category"))
            newGroup.setCategory(groupBuying.getString("category"));


        if(groupBuying.containsKey("goods")){
            JSONArray goodsArray = groupBuying.getJSONArray("goods");
        //System.out.println(groupBuying);
        //System.out.println(goodsArray.size());

        if(goodsArray.size() > 0) {

            newGroup.setGoods(null);

            for(int i = 0; i < goodsArray.size(); ++i) {

                //Object obj=goodsArray.parseObject(i);

                JSONObject obj =goodsArray.getJSONObject(i);

                Goods goods = new Goods();

                goods.setGoodsInfo(obj.getString("goodsInfo"));
                goods.setGoodsName(obj.getString("goodsName"));
                goods.setPicture(obj.getString("picture"));



                BigDecimal bd= new BigDecimal((obj.getString("price")));


                goods.setPrice(bd);
                System.out.println(groupBuying);

                goods.setInventory(Integer.parseInt(obj.get("inventory").toString()));



                goods.setGroup(newGroup);


                groupDao.saveGoods(goods);
            }
        }
        }

        JpaUtils.createUpdateEntity(groupById, newGroup);
        groupDao.save(newGroup);
        return MessageUtil.createMessage(MessageUtil.SUCCESS_CODE,MessageUtil.SUCCESS);
    }

}
