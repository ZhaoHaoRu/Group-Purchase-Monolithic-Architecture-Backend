package com.example.groupbuy.dao;

import com.alibaba.fastjson.JSONObject;
import com.example.groupbuy.entity.*;
import com.example.groupbuy.utils.messageUtils.Message;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

public interface OrderDao {
    List<JSONObject> getOrderByUserId(int userId);

    List<JSONObject> getOrderInfo(int userId);

    List<JSONObject> getOrderByGroupId(int groupId);

    Message<JSONObject> getGroupCart(int groupId,int userId);

    //封装
    List<Orders> findOrderByGroupId(Integer groupId);

    void refundOne(Integer orderId, Integer userId);

    void deleteByGroupId(Integer groupId);

    void deleteByOrderId(Integer orderId);

    Orders findByOrderId(Integer orderId);

    Integer getOrderId(Integer userId,Integer groupId);

    List<OrderItems> findItemsByOrderId(Integer orderId);

    User findByUserId(int id);

    void updateWallet(BigDecimal newWallet,Integer userId);

    void addToWallet(BigDecimal newWallet,Integer userId);

    void changeInventory(Integer inventory, Integer goodsId);

    void addToOrder(Timestamp time, Integer addressId, Integer orderId);

    GroupBuying findByGroupId(int id);

    Goods findByGoodsId(Integer goodsId);

    void saveCart(Orders newCart);

    void saveItem(OrderItems newItem);

    void deleteByItemId(Integer itemId);

    void changeGoodsNum(Integer goods_number, Integer goods_id, Integer order_id);

    List<Orders> getGroupAllCarts(Integer groupId);

    void refundOneBack(Integer orderId, Integer userId);

    Set<Orders> isOrdered(GroupBuying groupBuying, User user);


}
