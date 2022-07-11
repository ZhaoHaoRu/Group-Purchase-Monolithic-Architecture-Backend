package com.example.groupbuy.daoimpl;

import com.example.groupbuy.dao.GroupDao;
import com.example.groupbuy.entity.Goods;
import com.example.groupbuy.entity.GroupBuying;
import com.example.groupbuy.repository.GoodsRepository;
import com.example.groupbuy.repository.GroupRepository;
import com.example.groupbuy.repository.OrderItemsRepository;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public class GroupDaoImpl implements GroupDao {
    @Resource
    GroupRepository groupRepository;
    @Resource
    GoodsRepository goodsRepository;

    @Resource
    OrderItemsRepository orderItemsRepository;

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
        groupRepository.updateGroupState(groupId);
    }

    @Override
    public void updateGroup(Integer groupId, String groupTitle, String groupInfo, String category,
                                Timestamp startTime, Integer duration, String delivery){
        groupRepository.updateGroup(category, delivery, duration, groupInfo, groupTitle, startTime, groupId);
    }

    @Override
    public List<Goods> getGoodsByGroupId(Integer groupId){
        return goodsRepository.getGoodsByGroupId(groupId);
    }

    @Override
    public void updateGoods(Integer goodsId, String goodsInfo, BigDecimal price, Integer inventory){
        goodsRepository.updateGoods(goodsInfo, inventory, price, goodsId);
    }

    @Override
    public void updateCartItems(Integer orderId, Integer oldGoodsId, Integer newGoodsId){
        orderItemsRepository.updateCartItems(newGoodsId, orderId, oldGoodsId);
    }
}
