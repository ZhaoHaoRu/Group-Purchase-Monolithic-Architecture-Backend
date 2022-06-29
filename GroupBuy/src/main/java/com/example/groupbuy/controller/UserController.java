package com.example.groupbuy.controller;


import com.example.groupbuy.entity.User;
import com.example.groupbuy.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping(path = "/user")
@Api(tags = "用户")
public class UserController {
    @Resource
    private UserService userService;

    @PostMapping(path= "/userAuth")
    @ApiOperation("用户登录验证")
    public @ResponseBody User userAuth(@RequestParam("userName") String userName, @RequestParam("password") String password) {
        User user =  userService.userAuth(userName, password);
        return user;
    }

    @PostMapping(path="/register")
    @ApiOperation("新用户注册")
    public @ResponseBody User register(@RequestParam("userName") String userName, @RequestParam("password") String password, @RequestParam("email") String email) {
        return userService.register(userName, password, email);
    }

}
