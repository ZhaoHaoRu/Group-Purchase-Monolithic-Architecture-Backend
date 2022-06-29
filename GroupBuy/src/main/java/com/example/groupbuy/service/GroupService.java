package com.example.groupbuy.service;

import com.example.groupbuy.entity.GroupBuying;
import com.example.groupbuy.utils.messageUtils.Message;
import org.springframework.util.MultiValueMap;

public interface GroupService {
    Message<GroupBuying> getGroupById(int id);

    GroupBuying createGroup();
}
