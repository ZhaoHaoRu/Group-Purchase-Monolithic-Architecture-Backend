package com.example.groupbuy.serviceimpl;

import com.alibaba.fastjson.JSONObject;
import com.example.groupbuy.dao.*;
import com.example.groupbuy.entity.*;
import com.example.groupbuy.service.UserService;
import com.example.groupbuy.utils.messageUtils.Message;
import com.example.groupbuy.utils.messageUtils.MessageUtil;
import com.google.common.collect.Sets;
import io.swagger.models.auth.In;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.*;

@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserDao userDao;

    @Resource
    private GroupDao groupDao;

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
        /**
         *  为用户新建喜好历史条目
         */
        List<String> categories = Arrays.asList("水果鲜花", "肉禽蛋", "水产海鲜", "乳品烘培", "酒水饮料");
        for(String category : categories) {
            UserHistory userHistory = new UserHistory();
            userHistory.setLiking(0);
            userHistory.setUser(afterSave);
            userHistory.setCategory(category);
            userDao.saveUserHistory(userHistory);
        }
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
    public Message<Set<GroupBuying>> getUserCollection(int userId) {
        User user = userDao.findById(userId);
        if(user == null) {
            return MessageUtil.createMessage(MessageUtil.MISS_USER_CODE, MessageUtil.MISS_USER_MSG, null);
        }

        //过滤掉inventory=-1的
        Set<GroupBuying> groups = user.getGroups();
        Set<GroupBuying> groupBuyings = new HashSet<>();
        for (GroupBuying group:groups){
            Set<Goods> tmp = group.getGoods();
            tmp = FilterByInventory(tmp);
            group.setGoods(tmp);
            /**
             * 找到团购对应的图片
             */
            groupBuyings.add(groupDao.handleGroupPictures(group));
        }
//        return MessageUtil.createMessage(MessageUtil.SUCCESS_CODE, MessageUtil.SUCCESS, groups);
        return MessageUtil.createMessage(MessageUtil.SUCCESS_CODE, MessageUtil.SUCCESS, groupBuyings);
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

        //过滤掉已经删除的团购
        groups.removeIf(group -> group.getState() == 0);

        //过滤掉inventory=-1的
        Set<GroupBuying> groupBuyings = new HashSet<>();
        for (GroupBuying group:groups){
            Set<Goods> tmp = group.getGoods();
            tmp = FilterByInventory(tmp);
            group.setGoods(tmp);
            /**
             * 找到团购对应的图片
             */
            groupBuyings.add(groupDao.handleGroupPictures(group));
        }
//        return MessageUtil.createMessage(MessageUtil.SUCCESS_CODE, MessageUtil.SUCCESS, groups);
        return MessageUtil.createMessage(MessageUtil.SUCCESS_CODE, MessageUtil.SUCCESS, groupBuyings);
    }

    /**
     * 选取出库存量大于0的商品并找到对应的图片
     * @param sourceList
     * @return
     */
    @Override
    public Set<Goods> FilterByInventory(Set<Goods> sourceList){
        List<Goods> goodsList = new ArrayList<>(sourceList);
        Set<Goods> newSet = new HashSet<>();
        for (int i=0; i<goodsList.size(); ++i){
            Goods goods = goodsList.get(i);
            if (goods.getInventory()>=0){
                goods = groupDao.handleGoodsPicture(goods);
                newSet.add(goods);
            }
        }
        return newSet;
    }

    /**
     * 剔除已经无效和用户已经购买、用户创建的、收藏过的团购
     * @param user
     * @param groups
     * @return
     */
    @Override
    public Set<GroupBuying> GroupFilter(User user, Set<GroupBuying> groups) {
        Iterator<GroupBuying> iterator = groups.iterator();
        Set<GroupBuying> collectGroups = user.getGroups();
        Set<GroupBuying> createGroups = user.getCreateGroups();
        Set<GroupBuying> groupFiltered = new HashSet<>();
        for(GroupBuying group : groups) {
            long durationTime = Long.valueOf(group.getDuration() * 60 * 60 * 1000L);
            Timestamp timestamp = group.getStartTime();
            long startTime = timestamp.getTime();
            long nowTime = new Date().getTime();
            if(startTime + durationTime < nowTime) {
                // TODO: 注意此处会对于团购的过期情况进行修改
                group.setState(3);
                groupDao.save(group);
                continue;
            }
            if(collectGroups.contains(group)) {
                continue;
            }
            else if(createGroups.contains(group)) {
                continue;
            }
            groupFiltered.add(group);
        }
        return groupFiltered;

//        while(iterator.hasNext()) {
//            GroupBuying group = iterator.next();
//            if(collectGroups.contains(group)) {
//                iterator.remove();
//            }
//            else if(createGroups.contains(group)) {
//                iterator.remove();
//            }
//            // 判断团购是否已经过期
//            long durationTime = Long.valueOf(group.getDuration() * 60 * 60 * 1000L);
//            Timestamp timestamp = group.getStartTime();
//            long startTime = timestamp.getTime();
//            long nowTime = new Date().getTime();
//            if(startTime + durationTime < nowTime) {
//                // TODO: 注意此处会对于团购的过期情况进行修改
//                group.setState(3);
//                groupDao.save(group);
//                iterator.remove();
//            }
//        }
//        return groups;
    }

    /**
     * 为用户个性化推荐团购
     * @param userId
     * @return
     */
    @Override
    public Message<Set<GroupBuying>> RecommendGroup(int userId) {
        Integer baseCount = 4;
        // 获取用户的喜好历史
        User user = userDao.findById(userId);
        Map<String, Integer> userHistoryMap = new HashMap<String, Integer>();
        Set<UserHistory> userHistories = userDao.findUserHistoryByUser(user);
//        List<String> categories = new ArrayList<String>(Arrays.asList("水果鲜花", "肉禽蛋", "水产海鲜", "乳品烘培", "酒水饮料"));
        for (UserHistory userHistory : userHistories) {
            userHistoryMap.put(userHistory.getCategory(), userHistory.getLiking());
        }
        // 对于用户喜好进行排序
        List<Map.Entry<String, Integer>> entryList2 = new ArrayList<Map.Entry<String, Integer>>(userHistoryMap.entrySet());
        Collections.sort(entryList2, new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> me1, Map.Entry<String, Integer> me2) {
                return me2.getValue().compareTo(me1.getValue()); // 降序排序
            }
        });
        System.out.println("\n根据value排序结果: \n" + entryList2);
        Set<GroupBuying> groupSet = new HashSet<>();
        int i = 0;
        while(groupSet.size() < baseCount) {
            String firstCategory = entryList2.get(i).getKey();
            Set<GroupBuying> groups = groupDao.selectGroupsByTag(firstCategory);
            Set<GroupBuying> groupsQualified = GroupFilter(user, groups);
            groupSet.addAll(groupsQualified);
            i += 1;
            if(i >= 5)
                break;
        }
        return MessageUtil.createMessage(MessageUtil.SUCCESS_CODE, MessageUtil.SUCCESS, groupSet);
    }
}
