package com.example.groupbuy.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.groupbuy.entity.GroupBuying;
import com.example.groupbuy.entity.User;
import com.example.groupbuy.entity.VO.ChangeGroup;
import com.example.groupbuy.service.GroupService;
import com.example.groupbuy.utils.messageUtils.Message;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Set;
@RestController
@RequestMapping("/group")
@Api(tags = "团购")
public class GroupController {
    @Resource
    GroupService groupService;

    @GetMapping("/getGroupById")
    @ApiOperation("通过团购id获取团购")
    public Message<GroupBuying> getGroupById(@RequestParam int id) {
        return groupService.getGroupById(id);
    }



    @PostMapping("/createGroup")
    @ApiOperation("添加团购")
    public @ResponseBody Message<GroupBuying> createGroup(@RequestBody JSONObject groupBuying) {
        return groupService.createGroup(groupBuying);
    }

    @PostMapping("/collectGroup")
    @ApiOperation("收藏团购")
    public @ResponseBody Message<User> collectGroup(@RequestParam("userId") Integer userId, @RequestParam("groupId") Integer groupId) {
        return groupService.collectGroup(userId, groupId);
    }

    @PostMapping("/getGroupByTag")
    @ApiOperation("根据标签选取团购")
    public @ResponseBody Message<Set<GroupBuying>> getGroupByTag(@RequestParam("tag") String tag) {
        return groupService.getGroupByTag(tag);
    }

    @PostMapping("/getAllGroups")
    @ApiOperation("获取所有的团购")
    public @ResponseBody Message<Set<GroupBuying>> getAllGroup() {
        return groupService.getAllGroup();
    }

    @PostMapping("/getCollectedGroups")
    @ApiOperation("获取用户已经订阅的团购")
    public @ResponseBody Message<Set<GroupBuying>> getCollectedGroup(@RequestParam("userId") Integer userId) {
        return groupService.getCollectedGroup(userId);
    }


    @GetMapping("/endGroup")
    @ApiOperation("结束团购")
    public Message<String> endGroup(@RequestParam int groupId){
        return groupService.endGroup(groupId);
    }

    @GetMapping("/deleteGroup")
    @ApiOperation("删除团购")
    public Message<String> deleteGroup(@RequestParam int groupId){

        return groupService.deleteGroup(groupId);
    }

    @PostMapping("/changeGroup")
    @ApiOperation("修改团购（必须传回ID)")
    public Message<String> changeGroup(@RequestBody ChangeGroup groupBuying){
        return groupService.changeGroup(groupBuying);
    }

    @GetMapping("/judgeCollected")
    @ApiOperation("判断是否是已收藏团购")
    public Message<Boolean> judgeCollected(@RequestParam("userId") Integer userId, @RequestParam("groupId") Integer groupId) {
        return groupService.judgeCollected(userId, groupId);
    }


}
