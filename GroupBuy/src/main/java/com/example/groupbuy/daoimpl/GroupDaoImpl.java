package com.example.groupbuy.daoimpl;

import com.example.groupbuy.dao.GroupDao;
import com.example.groupbuy.entity.GroupBuying;
import com.example.groupbuy.repository.GroupRepository;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.Optional;

@Repository
public class GroupDaoImpl implements GroupDao {
    @Resource
    GroupRepository groupRepository;
    @Override
    public Optional<GroupBuying> getGroupById(int id) {
        return groupRepository.findById(id);
    }
}
