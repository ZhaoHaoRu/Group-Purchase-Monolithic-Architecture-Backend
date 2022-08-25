package com.example.groupbuy.controller;


import com.example.groupbuy.entity.Address;
import com.example.groupbuy.entity.GroupBuying;
import com.example.groupbuy.entity.User;
import com.example.groupbuy.service.UserService;
import com.example.groupbuy.utils.messageUtils.Message;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Set;

@RestController
@RequestMapping(path = "/user")
@Api(tags = "用户")
public class UserController {
    @Resource
    private UserService userService;

    @PostMapping(path= "/userAuth")
    @ApiOperation("用户登录验证")
    public @ResponseBody Message<User> userAuth(@RequestParam("userName") String userName, @RequestParam("password") String password) {
        return userService.userAuth(userName, password);
    }

    @PostMapping(path="/register")
    @ApiOperation("新用户注册")
    public @ResponseBody Message<User> register(@RequestParam("userName") String userName, @RequestParam("password") String password, @RequestParam("email") String email) {
        return userService.register(userName, password, email);
    }

    @GetMapping("/getUserById")
    @ApiOperation("通过id获取用户")
    public Message<User> getUserById(@RequestParam int id) {
        return userService.getUserById(id);
    }

    @GetMapping("/getUserCollection")
    @ApiOperation("获取用户收藏的团购")
    public Message<Set<GroupBuying>> getUserCollection(@RequestParam int id) {
        return userService.getUserCollection(id);
    }

    @GetMapping("/getUserAddress")
    @ApiOperation("获取用户地址")
    public Message<Set<Address> > getUserAddress(@RequestParam int id) {
        return userService.getUserAddress(id);
    }

    @GetMapping("/setNewAddress")
    @ApiOperation("新增用户地址")
    public Message<Integer> setNewAddress(@RequestParam("userId") int userId, @RequestParam("receiver") String receiver, @RequestParam("phone") String phone, @RequestParam("region") String region, @RequestParam("location") String location) {
        return userService.setNewAddress(userId, receiver, phone, region, location);
    }

    @GetMapping("/getCreatedGroup")
    @ApiOperation("获取用户创建的团购")
    public Message<Set<GroupBuying>> getCreatedGroup(@RequestParam("userId") int userId) {
        return userService.getCreatedGroup(userId);
    }

    @GetMapping("/recommend")
    @ApiOperation("向用户推荐团购")
    public Message<Set<GroupBuying>> groupRecommend(@RequestParam("userId") int userId) {
        return userService.RecommendGroup(userId);
    }
}
