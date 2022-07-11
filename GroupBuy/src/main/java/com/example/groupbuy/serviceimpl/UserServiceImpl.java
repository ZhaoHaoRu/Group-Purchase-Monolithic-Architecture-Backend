package com.example.groupbuy.serviceimpl;

import com.alibaba.fastjson.JSONObject;
import com.example.groupbuy.dao.*;
import com.example.groupbuy.entity.*;
import com.example.groupbuy.service.UserService;
import com.example.groupbuy.utils.messageUtils.Message;
import com.example.groupbuy.utils.messageUtils.MessageUtil;
import com.google.common.collect.Sets;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserDao userDao;

    @Override
    public Message<User> userAuth(String userName, String password) {
        User user = userDao.checkUser(userName, password);
        if(user != null){
            return MessageUtil.createMessage(MessageUtil.LOGIN_SUCCESS_CODE, MessageUtil.LOGIN_SUCCESS_MSG, user);
        }
        else return MessageUtil.createMessage(MessageUtil.LOGIN_ERROR_CODE, MessageUtil.LOGIN_ERROR_MSG, null);
    }

    // 此处的逻辑是不允许用户名重复, 默认用户最开始钱包中的金额是1000
    @Override
    public Message<User> register(String userName, String password, String email) {
        User checkedUser = userDao.findByUserName(userName);
        if(checkedUser != null) {
            return MessageUtil.createMessage(MessageUtil.REGISTER_ERROR_CODE, MessageUtil.REGISTER_ERROR_MSG,null);
        }
        User user = new User();
        user.setUserName(userName);
        user.setPassword(password);
        user.setEmail(email);
        user.setWallet(BigDecimal.valueOf(1000));
        User afterSave = userDao.save(user);
        return MessageUtil.createMessage(MessageUtil.REGISTER_SUCCESS_CODE, MessageUtil.REGISTER_SUCCESS_MSG,afterSave);
    }

    @Override
    public Message<User> getUserById(int id) {
        User user = userDao.findById(id);

        if(user != null){
            return MessageUtil.createMessage(MessageUtil.SUCCESS_CODE, MessageUtil.SUCCESS,user);
        }
        return MessageUtil.createMessage(MessageUtil.MISS_USER_CODE, MessageUtil.MISS_USER_MSG,null);
    }

    @Override
    public Message<Set<GroupBuying> > getUserCollection(int userId) {
        User user = userDao.findById(userId);
        if(user == null) {
            return MessageUtil.createMessage(MessageUtil.MISS_USER_CODE, MessageUtil.MISS_USER_MSG, null);
        }
        //过滤掉inventory=-1的
        Set<GroupBuying> groups = user.getGroups();
        for (GroupBuying group:groups){
            Set<Goods> tmp = group.getGoods();
            tmp = FilterByInventory(tmp);
            group.setGoods(tmp);
        }
        return MessageUtil.createMessage(MessageUtil.SUCCESS_CODE, MessageUtil.SUCCESS, groups);
    }

    @Override
    public Message<Set<Address> > getUserAddress(int userId) {
        User user = userDao.findById(userId);

        if(user == null) {
            return MessageUtil.createMessage(MessageUtil.MISS_USER_CODE, MessageUtil.MISS_USER_MSG, null);
        }

        else {
            return MessageUtil.createMessage(MessageUtil.SUCCESS_CODE, MessageUtil.SUCCESS, user.getAddresses());
            }

    }

    @Override
    public Message<Integer> setNewAddress(int userId, String receiver, String phone, String region, String location) {
        User user = userDao.findById(userId);
        //如果没有这个用户
        if(user == null) {
            return MessageUtil.createMessage(MessageUtil.MISS_USER_CODE, MessageUtil.MISS_USER_MSG, -1);
        }

//        Set<Address> addresses = user.getAddresses();
        Address address = new Address();
        address.setLocation(location);
        address.setReceiver(receiver);
        address.setRegion(region);
        address.setUser(user);
        address.setPhone(phone);
        Address newAddress = userDao.saveAddress(address);
//        addresses.add(newAddress);
        return MessageUtil.createMessage(MessageUtil.SUCCESS_CODE, MessageUtil.DONE_SUCCESS_CODE, newAddress.getAddressId());
    }

    @Override
    public Message<Set<GroupBuying>> getCreatedGroup(int userId) {
        User user = userDao.findById(userId);
        //如果没有这个用户
        if(user == null) {
            return MessageUtil.createMessage(MessageUtil.MISS_USER_CODE, MessageUtil.MISS_USER_MSG, null);
        }
        Set<GroupBuying> groups = user.getCreateGroups();
        //过滤掉inventory=-1的
        for (GroupBuying group:groups){
            Set<Goods> tmp = group.getGoods();
            tmp = FilterByInventory(tmp);
            group.setGoods(tmp);
        }
        return MessageUtil.createMessage(MessageUtil.SUCCESS_CODE, MessageUtil.SUCCESS, groups);
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
}
