package com.example.groupbuy.repository;

import com.example.groupbuy.entity.GroupPic;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupPicRepository extends MongoRepository<GroupPic, Integer> {
    GroupPic findByGroupId(Integer groupId);
}
