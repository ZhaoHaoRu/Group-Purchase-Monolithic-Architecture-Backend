package com.example.groupbuy.dao;

import com.example.groupbuy.entity.GroupBuying;

import java.util.Optional;

public interface GroupDao {
    Optional<GroupBuying> getGroupById(int id);

}
