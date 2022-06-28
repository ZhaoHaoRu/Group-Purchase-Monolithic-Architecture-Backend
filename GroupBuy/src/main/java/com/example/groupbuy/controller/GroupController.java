package com.example.groupbuy.controller;

import com.example.groupbuy.entity.GroupBuying;
import com.example.groupbuy.service.GroupService;
import com.example.groupbuy.utils.messageUtils.Message;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
@RestController
@RequestMapping("/group")
@Api(tags = "团购")
public class GroupController {
    @Resource
    GroupService groupService;

    @GetMapping("/getGroupById")
    @ApiOperation("通过id获取团购")
    public Message<GroupBuying> getGroupById(@RequestParam int id) {
        return groupService.getGroupById(id);
    }

/*    @PostMapping("/creatGroup")
    @ApiOperation("添加书籍")
    public Message<String> creatGroup(@RequestBody GroupBuying groupBuying) {
        return GroupService.creatGroup(groupBuying);
    }*/
}
