package com.example.groupbuy.repository;

import com.example.groupbuy.entity.Goods;
import com.example.groupbuy.entity.VO.ChangeGoods;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;

@Repository
public interface GoodsRepository extends JpaRepository<Goods,Integer>, JpaSpecificationExecutor<Goods> {

    Goods findByGoodsId(Integer goodsId);

    @Transactional
    @Modifying
    @Query(value = "update goods set inventory = :inventory where goods_id = :goodsId",nativeQuery = true)
    void changeInventory(@Param("inventory") Integer inventory, @Param("goodsId") Integer goodsId);

    @Query(value = "select * from goods where group_id = :groupId",nativeQuery = true)
    List<Goods> getGoodsByGroupId(@Param("groupId")Integer groupId);

    @Transactional
    @Modifying
    @Query(value = "update goods set goods_info = :goodsInfo, inventory = :inventory, price = :price " +
            "where goods_id = :goodsId",nativeQuery = true)
    void updateGoods(@Param("goodsInfo")String goodsInfo, @Param("inventory")Integer inventory,
                     @Param("price")BigDecimal price, @Param("goodsId")Integer goodsId);
}
