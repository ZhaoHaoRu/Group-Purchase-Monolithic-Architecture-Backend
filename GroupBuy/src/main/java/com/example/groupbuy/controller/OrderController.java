package com.example.groupbuy.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.groupbuy.service.OrderService;
import com.example.groupbuy.utils.messageUtils.Message;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/order")
@Api(tags = "订单")
public class OrderController {

    @Resource
    OrderService orderService;

    @GetMapping("/getOrderByUserId")
    @ApiOperation("通过用户id获取其订单信息")
    public Message<List<JSONObject>> getOrderByUserId(@RequestParam int userId){
        return orderService.getOrderByUserId(userId);
    }

    @GetMapping("/getOrderInfo")
    @ApiOperation("通过团长用户id获取其所有售出的订单信息")
    public Message<List<JSONObject>> getOrderInfo(@RequestParam int userId){
        System.out.println("getOrderInfo");
        System.out.println(userId);
        return orderService.getOrderInfo(userId);
    }

    @GetMapping("/getOrderByGroupId")
    @ApiOperation("团长通过团购Id查看团购订单查看团购订单")
    public Message<List<JSONObject>> getOrderByGroupId(@RequestParam int groupId){
        return orderService.getOrderByGroupId(groupId);
    }

    @GetMapping("/addToCart")
    @ApiOperation("在团购页面将物品加入购物车")
    public Message<String> addToCart(@RequestParam("userId")int userId, @RequestParam("groupId")int groupId,
                                     @RequestParam("goodsId")int goodsId, @RequestParam("goodsNumber")int goodsNumber){
        return orderService.addToCart(userId,groupId,goodsId,goodsNumber);
    }

    @GetMapping("/addOrder")
    @ApiOperation("用户下订单")
    public Message<String> addOrder(@RequestParam("groupId")int groupId, @RequestParam("userId")int userId,
                                    @RequestParam("addressId")int addressId, @RequestParam("time")String time){
        return orderService.addOrder(groupId,userId,addressId,time);
    }

    @GetMapping("/deleteOneOrder")
    @ApiOperation("团长通过订单号取消单个订单")
    public Message<String> deleteOneOrder(@RequestParam int orderId){
        return orderService.deleteOneOrder(orderId);
    }

    @GetMapping("/deleteOrderByGroupId")
    @ApiOperation("团长删除团购后所有此团购订单都删除和退款")
    public Message<String> deleteOrderByGroupId(@RequestParam int groupId){
        return orderService.deleteOrderByGroupId(groupId);
    }

    @GetMapping("getGroupCart")
    @ApiOperation("用户查看团购的购物车")
    public Message<JSONObject> getGroupCart(@RequestParam("groupId")int groupId, @RequestParam("userId")int userId){
        return orderService.getGroupCart(groupId, userId);
    }
}
