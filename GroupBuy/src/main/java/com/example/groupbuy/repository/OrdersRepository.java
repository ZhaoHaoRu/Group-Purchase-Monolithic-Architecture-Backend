package com.example.groupbuy.repository;


import com.example.groupbuy.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.List;

@Repository
public interface OrdersRepository extends JpaRepository<Orders,Integer>, JpaSpecificationExecutor<Orders> {

    Orders findByOrderId(int id);

    @Query(value = "select * from orders where user_id = :userId AND state != 0",nativeQuery = true)
    List<Orders> findOrderByUserId(@Param("userId") Integer userId);

    @Query(value = "select * from orders where group_id = :groupId AND state = 1",nativeQuery = true)
    List<Orders> findOrderByGroupId(@Param("groupId") Integer groupId);

    @Query(value = "SELECT order_id FROM orders WHERE user_id = :userId AND group_id = :groupId AND state = 0",nativeQuery = true)
    Integer getOrderId(@Param("userId") Integer userId, @Param("groupId") Integer groupId);

    @Query(value = "SELECT * FROM orders WHERE user_id = :userId AND group_id = :groupId AND state = 0",nativeQuery = true)
    Orders getCart(@Param("userId") Integer userId, @Param("groupId") Integer groupId);

    @Transactional
    @Modifying
    @Query(value = "update orders set time = :time, address_id = :addressId, state = 1 where order_id = :orderId", nativeQuery = true)
    void addToOrder(@Param("time") Timestamp time, @Param("addressId") Integer addressId, @Param("orderId") Integer orderId);

    @Transactional
    @Modifying
    @Query(value = "UPDATE user SET user.wallet = user.wallet+(SELECT SUM(order_item.goods_number*goods.price) \n" +
            "FROM order_item NATURAL JOIN goods\n" +
            "WHERE order_item.goods_id = goods.goods_id AND order_item.order_id = :orderId)\n" +
            "WHERE user_id = :userId",nativeQuery = true)
    void refundOne(@Param("orderId") Integer orderId, @Param("userId") Integer userId);

    @Transactional
    @Modifying
    @Query(value = "update orders set orders.state = 2 where order_id =:orderId",nativeQuery = true)
    void deleteByOrderId(@Param("orderId") Integer orderId);

    @Transactional
    @Modifying
    @Query(value = "update orders set orders.state = 2 where group_id = :groupId AND state=1",nativeQuery = true)
    public void deleteByGroupId(@Param("groupId")Integer groupId);
}
