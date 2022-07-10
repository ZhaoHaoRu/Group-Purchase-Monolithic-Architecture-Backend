package com.example.groupbuy.daoimpl;

import com.example.groupbuy.dao.GroupDao;
import com.example.groupbuy.entity.Goods;
import com.example.groupbuy.entity.GroupBuying;
import com.example.groupbuy.repository.GoodsRepository;
import com.example.groupbuy.repository.GroupRepository;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.Optional;
import java.util.Set;

@Repository
public class GroupDaoImpl implements GroupDao {
    @Resource
    GroupRepository groupRepository;
    @Resource
    GoodsRepository goodsRepository;

    @Override
    public Optional<GroupBuying> getGroupById(int id) {
        return groupRepository.findById(id);
    }

    @Override
    public GroupBuying save(GroupBuying groupBuying) { return groupRepository.save(groupBuying); }

    @Override
    public Goods saveGoods(Goods goods) { return goodsRepository.save(goods); }

    @Override
    public Set<GroupBuying> selectGroupsByTag(String category) {
        return groupRepository.selectGroupsByTag(category);
    }

    @Override
    public Set<GroupBuying> findAll() { return groupRepository.selectAllByStateIsNot();}

    @Override
    public void endGroup(int groupId) {
        GroupBuying groupBuying = getGroupById(groupId).get();
        groupBuying.setDuration(0);
        groupRepository.save(groupBuying);
    }

    @Override
    public void deleteGroup(int groupId) {
        GroupBuying groupBuying = getGroupById(groupId).get();
        groupBuying.setState(0);
        groupRepository.save(groupBuying);
    }

}
