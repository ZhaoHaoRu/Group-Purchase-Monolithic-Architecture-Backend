package com.example.groupbuy.serviceimpl;

import com.alibaba.fastjson.JSONArray;
import com.example.groupbuy.dao.GroupDao;
import com.example.groupbuy.dao.OrderDao;
import com.example.groupbuy.dao.UserDao;
import com.example.groupbuy.entity.*;
import com.example.groupbuy.entity.VO.ChangeGoods;
import com.example.groupbuy.service.GroupService;
import com.example.groupbuy.service.RedisService;
import com.example.groupbuy.service.SecKillService;
import com.example.groupbuy.utils.redis.RedisUtil;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.*;

@Service
public class SecKillServiceImpl implements SecKillService {
    @Resource
    GroupDao groupDao;
    @Resource
    UserDao userDao;

    @Resource
    OrderDao orderDao;

    @Autowired
    GroupService groupService;

    @Resource
    RedisService redisService;

    @Autowired
    private RedisUtil redisUtil;

    // 存储团购对应的团长信息
    private HashMap<Integer, Integer>  groupHeaders = new HashMap<>();
    private HashMap<Integer, BigDecimal> groupBenefit = new HashMap<>();



    @Override
    @Transactional
    public Set<Goods> getAllSecKillGoods() {
        Set<GroupBuying> groupBuyings = groupDao.queryAllSecKillGoods();
        // 获取当前还有效的秒杀团购
        Set<Goods> goods = new HashSet<Goods>();
//        for(GroupBuying groupBuying : groupBuyings) {
//            long nowTime = new Date().getTime();
////            int duration = groupBuying.getDuration();
//            long durationTime = Long.valueOf(groupBuying.getDuration() * 60 * 60 * 1000L);
//            Timestamp timestamp = groupBuying.getStartTime();
//            long startTime = timestamp.getTime();
//            if(startTime + durationTime < nowTime) {
//                groupBuying.setState(3);
//                groupDao.save(groupBuying);
//                groupBuyings.remove(groupBuying);
//            }
//            // 获取当前秒杀团购中的商品，并且过滤掉无效的
//            else {
//                Set<Goods> goodsList = groupDao.getGoodsByGroupId(groupBuying.getGroupId());
//                Set<Goods> newGoods = groupService.FilterByInventory(goodsList);
//                goods.addAll(newGoods);
//            }
//        }
        Iterator<GroupBuying> iterator = groupBuyings.iterator();
        while(iterator.hasNext()) {
            long nowTime = new Date().getTime();
            GroupBuying groupBuying = iterator.next();
            long durationTime = Long.valueOf(groupBuying.getDuration() * 60 * 60 * 1000L);
            Timestamp timestamp = groupBuying.getStartTime();
            long startTime = timestamp.getTime();
            if(startTime + durationTime < nowTime) {
                groupBuying.setState(3);
                groupDao.save(groupBuying);
                iterator.remove();
            }
            // 获取当前秒杀团购中的商品，并且过滤掉无效的
            else {
                Set<Goods> goodsList = groupDao.getGoodsByGroupId(groupBuying.getGroupId());
                Set<Goods> newGoods = groupService.FilterByInventory(goodsList);
                goods.addAll(newGoods);
            }
        }
        return goods;
    }

    @Override
    public Goods getSecKillGoodsById(int id) {
        Goods goods = groupDao.getGoodsById(id);
        // 如果没有对应id的有效商品的话，返回空
        if(goods == null || goods.getInventory() < 0) {
            return null;
        } else {
            return goods;
        }
    }



    @Override
//    @Transactional
    public boolean isOrdered(GroupBuying groupBuying, User user) {
        Set<Orders> orders = orderDao.isOrdered(groupBuying, user);
        if(orders.isEmpty()) {
            return true;
        } else {
            return false;
        }
    }


    /**
     * 处理购买不成功，此订单中商品库存的所有变动都要恢复，也包括团长的收益
     * @param goodsData
     * @param i
     * @return
     */
    @Override
    public boolean orderExceptionHandle(JSONArray goodsData, int i) {
        try {
            for(int j = 0; j < i; ++j) {
                Map<String, Integer> obj = (Map<String, Integer>) goodsData.get(j);
                Integer goodsNumber = 0 - obj.get("goodsNumber");
                Integer goodsId = obj.get("goodsId");
                groupDao.updateInventory(goodsNumber, goodsId);
                // 对于缓存中的结果也要有所修改
                redisUtil.incr(goodsId.toString(), goodsNumber);
            }
        } catch (Exception e) {
            // 如果异常处理失败，返回false
            return false;
        }
        return true;
    }

    /**
     * 执行秒杀请求，减库存，下订单
     * @param userId
     * @param addressId
     * @param groupId
     * @param goodsData
     * @return
     */
    @Override
    @Transactional
    // 在进入这里之前，已经判断了用户余额
    public boolean SecKillExecution(int userId, int addressId, int groupId, BigDecimal price, JSONArray goodsData) {
        // 首先判断是否是重复秒杀, 如果是重复秒杀，直接返回错误
        BigDecimal totalPay = BigDecimal.valueOf(0);
        // 判断库存，减少库存
        System.out.println("price:");
        System.out.println(price);
        int length = goodsData.size();
        for(int i = 0; i < length; ++i) {
            Map<String, Integer> obj = (Map<String, Integer>) goodsData.get(i);
            Integer goodsNumber = obj.get("goodsNumber");
            Integer goodsId = obj.get("goodsId");
//            // 如果商品库存为0，此时下单失败
//            if(redisService.get("goodsOver"+goodsId.toString(), String.class) == true){
//                orderExceptionHandle(goodsData, i - 1);
//                return false;
//            }
            // 如果目前商品库存不足，购买不成功，此订单商品中的所有变动都要恢复
            // 此时的判断只针对数据库，不再对于缓存进行判断
            groupDao.updateInventory(goodsNumber, goodsId);
            Goods nowGoods = groupDao.getGoodsById(goodsId);
            Integer nowInventory = nowGoods.getInventory();
            // TODO 如果这种情况，即缓存中商品库存够卖，但是数据库操作后为负数的情况不出现，这里逻辑还可以进一步简化（测试的时候进一步check)
            // 如果现存的库存小于零，说明购买不成功，此订单商品中的所有变动都要恢复，与此同时订单生成不成功
            if(nowInventory < 0) {
                orderExceptionHandle(goodsData, i);
                return false;
            } else if(nowInventory == 0) {
                // 这时商品商品已经买完
                redisService.set("goodsOver"+goodsId.toString(), "true");
            }
        }

        // 对于用户修改余额
        User user = userDao.findById(userId);
        if(user == null)
            return false;
        BigDecimal newWallet = user.getWallet();
        newWallet = newWallet.subtract(price);
        orderDao.updateWallet(newWallet, userId);
        // 修改团长的收益
        Integer headerId = groupHeaders.get(groupId);
        if(headerId == null) {
            User header = orderDao.findByGroupId(groupId).getUser();
            headerId = header.getUserId();
            groupBenefit.put(groupId, header.getWallet());
            groupHeaders.put(groupId, headerId);
        }
        BigDecimal benefit = groupBenefit.get(groupId);
        benefit = benefit.add(price);
        groupBenefit.put(groupId, benefit);
        orderDao.updateWallet(benefit, headerId);
//        orderDao.addToWallet(newWallet, headerId);
        // 生成订单,这里的时间是后端的系统时间
        Date newDate = new Date();
        Timestamp time = new Timestamp(newDate.getTime());
        Integer orderId = orderDao.getOrderId(userId, groupId);
        orderDao.addToOrder(time, addressId, orderId);
        // 将订单加入缓存
        redisService.set("order_" + String.valueOf(userId) + "_" + String.valueOf(groupId), "true");
        return true;
    }

    @Override
    public boolean SecKillResult(Integer userId, Integer groupId) {
        String result = redisService.get("order_" + userId.toString() + "_" + groupId.toString(), String.class);
        // 秒杀成功
        if(result.equals("true")){
            return true;
        } else {
            return false;
        }
    }
}
