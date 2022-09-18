package com.example.groupbuy.repository;

import com.example.groupbuy.entity.OrderItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;


@Repository
public interface OrderItemsRepository extends JpaRepository<OrderItems,Integer>, JpaSpecificationExecutor<OrderItems> {

    @Transactional
    @Modifying
    @Query(value = "UPDATE order_item SET goods_number = :goods_number WHERE goods_id = :goods_id AND order_id = :order_id",nativeQuery = true)
    void changeGoodsNum(@Param("goods_number") Integer goods_number, @Param("goods_id") Integer goods_id, @Param("order_id") Integer order_id);


    @Query(value = "select * from order_item where order_id = :orderId", nativeQuery = true)
    List<OrderItems> findItemsByOrderId(@Param("orderId") Integer orderId);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM order_item WHERE order_item_id = :itemId",nativeQuery = true)
    void deleteById(@Param("itemId") Integer itemId);

    @Transactional
    @Modifying
    @Query(value = "update order_item set goods_id = :newGoodsId where order_id = :orderId and goods_id = :oldGoodsId",nativeQuery = true)
    void updateCartItems(@Param("newGoodsId")Integer newGoodsId, @Param("orderId")Integer orderId, @Param("oldGoodsId")Integer oldGoodsId);
}
