package com.example.groupbuy.serviceimpl;

import com.alibaba.fastjson.JSONObject;
import com.example.groupbuy.dao.GroupDao;
import com.example.groupbuy.dao.OrderDao;
import com.example.groupbuy.dao.UserDao;
import com.example.groupbuy.entity.*;
import com.example.groupbuy.service.OrderService;
import com.example.groupbuy.utils.messageUtils.Message;
import com.example.groupbuy.utils.messageUtils.MessageUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Resource
    OrderDao orderDao;

    @Resource
    GroupDao groupDao;

    @Resource
    UserDao userDao;

    @Override
    public Message<List<JSONObject>> getOrderByUserId(int userId){
        return MessageUtil.createMessage(MessageUtil.LOGIN_SUCCESS_CODE, MessageUtil.SUCCESS, orderDao.getOrderByUserId(userId));
    }

    @Override
    public Message<List<JSONObject>> getOrderInfo(int userId){
        System.out.println("OrderService  getOrderInfo");
        return MessageUtil.createMessage(MessageUtil.LOGIN_SUCCESS_CODE, MessageUtil.SUCCESS, orderDao.getOrderInfo(userId));
    }

    @Override
    public Message<List<JSONObject>> getOrderByGroupId(int groupId){
        return MessageUtil.createMessage(MessageUtil.LOGIN_SUCCESS_CODE, MessageUtil.SUCCESS, orderDao.getOrderByGroupId(groupId));
    }

    @Override
    public Message<JSONObject> getGroupCart(int groupId, int userId){
        return orderDao.getGroupCart(groupId,userId);
    }

    /*
    下订单
     */
    @Override
    public Message<String> addOrder(int groupId,int userId,int addressId,String time){
//        Integer groupId = info.getInteger("groupId");
//        Integer userId = info.getInteger("userId");
        Integer orderId = orderDao.getOrderId(userId,groupId);
        List<OrderItems> itemList = orderDao.findItemsByOrderId(orderId);
        BigDecimal price = BigDecimal.valueOf(0);
        String resultInfo;
        //判断库存够不够
        for (int i=0; i<itemList.size(); ++i){
            OrderItems Item = itemList.get(i);
            Integer number = Item.getGoodsNumber();
            Goods goods = Item.getGood();
            BigDecimal unitPrice = goods.getPrice();
            if (number>goods.getInventory()){
                resultInfo = "下单失败，商品库存不够！";
                return MessageUtil.createMessage(MessageUtil.CREATE_ORDER_ERROR_CODE,MessageUtil.NOTENOUGHSTOCK,resultInfo);
            }
            unitPrice = unitPrice.multiply(BigDecimal.valueOf(number));
            price = price.add(unitPrice);
        }
        //判断用户的余额够不够
        User user = orderDao.findByUserId(userId);
        if (price.compareTo(user.getWallet()) >= 0){
            resultInfo = "下单失败，用户余额不足！";
            return MessageUtil.createMessage(MessageUtil.CREATE_ORDER_ERROR_CODE,MessageUtil.MONEY_NOT_ENOUGH,resultInfo);
        }
        else {
            //用户扣款
            BigDecimal newWallet = user.getWallet();
            newWallet = newWallet.subtract(price);
            orderDao.updateWallet(newWallet, userId);
            //团长收钱
            User header = orderDao.findByGroupId(groupId).getUser();
            BigDecimal headerWallet = header.getWallet();
            headerWallet = headerWallet.add(price);
            orderDao.updateWallet(headerWallet,header.getUserId());
            //减库存
            for (int i=0; i<itemList.size(); ++i){
                OrderItems Item = itemList.get(i);
                Integer number = Item.getGoodsNumber();
                Goods goods = Item.getGood();
                orderDao.changeInventory((goods.getInventory()-number),goods.getGoodsId());
            }
        }
        //条件都满足的情况下下单
//        String StrTime = info.getString("time");
//        Integer addressId = info.getInteger("addressId");
        /**
         * 对于用户的喜好历史进行修改
         */
        GroupBuying group = orderDao.findByGroupId(groupId);
        userDao.updateLiking(3, userId, group.getCategory());
        /**
         * 对于团购的受欢迎程度进行修改
         */
        groupDao.updatePopularity(3, groupId);
        Timestamp Time = Timestamp.valueOf(time);
        orderDao.addToOrder(Time,addressId,orderId);
        resultInfo = "下单成功！";
        return MessageUtil.createMessage(MessageUtil.LOGIN_SUCCESS_CODE,MessageUtil.SUCCESS,resultInfo);
    }

    /*
    将商品加入购物车
     */
    @Override
    public Message<String> addToCart(int userId,int groupId,int goodsId,int goodsNumber){
//        Integer groupId = info.getInteger("groupId");
//        Integer userId = info.getInteger("userId");
//        Integer goodsId = info.getInteger("goodsId");
//        Integer goodsNumber = info.getInteger("goodsNumber");
        String resultInfo;
        Integer orderId = orderDao.getOrderId(userId,groupId);
        User user = orderDao.findByUserId(userId);
        GroupBuying group = orderDao.findByGroupId(groupId);
        Goods goods = orderDao.findByGoodsId(goodsId);
        //如果从来没有将这个团购中的任意一件商品加入过购物车，Order中没有表项，需要创建
        if (orderId == null){
            if (goods.getInventory() < goodsNumber){
                resultInfo = "加入购物车失败，库存不够！";
                return MessageUtil.createMessage(MessageUtil.CREATE_CART_ERROR_CODE,MessageUtil.NOTENOUGHSTOCK,resultInfo);
            }
            else{
                Orders newCart = new Orders();
                newCart.setState(0);
                newCart.setUser(user);
                newCart.setGroup(group);
                OrderItems newItem = new OrderItems();
                newItem.setGood(goods);
                newItem.setOrders(newCart);
                newItem.setGoodsNumber(goodsNumber);
                orderDao.saveCart(newCart);
                orderDao.saveItem(newItem);
            }
        }
        //如果将这个团购中的某个商品加入过购物车，即Order中有表项，需要更新或创建Item
        else{
            List<OrderItems> itemList = orderDao.findItemsByOrderId(orderId);
            for (int i=0; i<itemList.size(); ++i){
                OrderItems item = itemList.get(i);
                if (item.getGood()==goods){
                    int formerNum = item.getGoodsNumber();
                    orderDao.changeGoodsNum(goodsNumber + formerNum, goodsId, orderId);
                    int currentNum = item.getGoodsNumber();
                    if (currentNum == 0){
                        orderDao.deleteByItemId(item.getOrderItemId());
                    }
                    resultInfo = "加入购物车成功!";
                    return MessageUtil.createMessage(MessageUtil.LOGIN_SUCCESS_CODE,MessageUtil.SUCCESS,resultInfo);
                }
            }
            //orderItem中没有这个商品对应的表项，创建一个
            Orders Cart = orderDao.findByOrderId(orderId);
            OrderItems newItem = new OrderItems();
            newItem.setGoodsNumber(goodsNumber);
            newItem.setOrders(Cart);
            newItem.setGood(goods);
            orderDao.saveItem(newItem);
        }
        resultInfo="加入购物车成功！";
        /**
         * 对于用户的喜好历史进行修改
         */
        userDao.updateLiking(2, userId, group.getCategory());
        /**
         * 对于团购的受欢迎程度进行修改
         */
        groupDao.updatePopularity(2, groupId);
        return MessageUtil.createMessage(MessageUtil.LOGIN_SUCCESS_CODE,MessageUtil.SUCCESS,resultInfo);
    }

    /*
    团长删除单个订单或者用户取消订单
     */
    @Override
    public Message<String> deleteOneOrder(int orderId){
//        Integer orderId = OrderId.getInteger("orderId");
        Orders order = orderDao.findByOrderId(orderId);
        Integer userId = order.getUser().getUserId();
        //用户被退款
        orderDao.refundOne(orderId,userId);
        //团长的钱也减少
        orderDao.refundOneBack(orderId,order.getGroup().getUser().getUserId());
        //删除订单
        orderDao.deleteByOrderId(orderId);
        //更新商品库存
        List<OrderItems> itemList = orderDao.findItemsByOrderId(orderId);
        for (int i=0; i<itemList.size(); ++i){
            OrderItems Item = itemList.get(i);
            Integer number = Item.getGoodsNumber();
            Goods goods = Item.getGood();
            orderDao.changeInventory((goods.getInventory()+number),goods.getGoodsId());
        }
        String result = "该订单已取消并已退款！";
        return MessageUtil.createMessage(MessageUtil.LOGIN_SUCCESS_CODE,MessageUtil.SUCCESS,result);
    }

    /*
    团长删除团购后根据团购Id删除所有订单和购物车并退款
     */
    @Override
    public Message<String> deleteOrderByGroupId(int groupId){
//        Integer groupId = GroupId.getInteger("groupId");
        List<Orders> ordersList = orderDao.findOrderByGroupId(groupId);
        //退款
        for (int i=0; i<ordersList.size(); ++i){
            Orders order = ordersList.get(i);
            //用户被退款
            orderDao.refundOne(order.getOrderId(),order.getUser().getUserId());
            //团长的钱相应的减少
            orderDao.refundOneBack(order.getOrderId(),order.getGroup().getUser().getUserId());
        }
        //取消订单
        orderDao.deleteByGroupId(groupId);
        String result = "所有订单都已取消并已退款！";
        return MessageUtil.createMessage(MessageUtil.LOGIN_SUCCESS_CODE,MessageUtil.SUCCESS,result);
    }
}
