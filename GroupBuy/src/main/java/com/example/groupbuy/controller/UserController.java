package com.example.groupbuy.controller;


import com.example.groupbuy.entity.User;
import com.example.groupbuy.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping(path = "/user")
@Api(tags = "用户")
public class UserController {
    @Resource
    private UserService userService;

    @PostMapping("/userAuth")
    @ApiOperation("用户登录验证")
    public User userAuth(@RequestParam("userName") String userName, @RequestParam("password") String password) {
        return userService.userAuth(userName, password);
    }

    @PostMapping("/register")
    @ApiOperation("新用户注册")
    public User register(@RequestParam("userName") String userName, @RequestParam("password") String password, @RequestParam("email") String email) {
        return userService.register(userName, password, email);
    }

}
