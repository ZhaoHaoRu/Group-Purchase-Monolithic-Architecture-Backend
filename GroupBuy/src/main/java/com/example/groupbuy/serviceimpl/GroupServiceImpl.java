package com.example.groupbuy.serviceimpl;

import com.example.groupbuy.dao.GroupDao;
import com.example.groupbuy.entity.GroupBuying;
import com.example.groupbuy.service.GroupService;
import com.example.groupbuy.utils.messageUtils.Message;
import com.example.groupbuy.utils.messageUtils.MessageUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import javax.annotation.Resource;

@Service
public class GroupServiceImpl implements GroupService {
    @Resource
    GroupDao groupDao;

    @Override
    public Message<GroupBuying> getGroupById(int id) {
        return MessageUtil.createMessage(MessageUtil.LOGIN_SUCCESS_CODE, MessageUtil.SUCCESS, groupDao.getGroupById(id).orElse(null));
    }

    @Override
    public GroupBuying createGroup() {
        GroupBuying group = new GroupBuying();
//        System.out.println();
        return group;
    }
}
