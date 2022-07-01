package com.example.groupbuy.repository;

import com.example.groupbuy.entity.Goods;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface GoodsRepository extends JpaRepository<Goods,Integer>, JpaSpecificationExecutor<Goods> {

    Goods findByGoodsId(Integer goodsId);

    @Transactional
    @Modifying
    @Query(value = "update goods set inventory = :inventory where goods_id = :goodsId",nativeQuery = true)
    void changeInventory(@Param("inventory") Integer inventory, @Param("goodsId") Integer goodsId);
}
