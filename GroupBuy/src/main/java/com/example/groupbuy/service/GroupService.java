package com.example.groupbuy.service;

import com.example.groupbuy.entity.GroupBuying;
import com.example.groupbuy.utils.messageUtils.Message;

public interface GroupService {
    Message<GroupBuying> getGroupById(int id);
}
