package com.example.groupbuy.service;

import com.alibaba.fastjson.JSONObject;
import com.example.groupbuy.utils.messageUtils.Message;

import java.util.List;

public interface OrderService {

    Message<List<JSONObject>> getOrderByUserId(int userId);

    Message<List<JSONObject>> getOrderInfo(int userId);

    Message<List<JSONObject>> getOrderByGroupId(int groupId);

    Message<String> addOrder(int groupId,int userId,int addressId,String time);

    Message<String> addToCart(int userId,int groupId,int goodsId,int goodsNumber);

    Message<String> deleteOneOrder(int orderId);

    Message<String> deleteOrderByGroupId(int groupId);

    Message<JSONObject> getGroupCart(int groupId, int userId);
}
