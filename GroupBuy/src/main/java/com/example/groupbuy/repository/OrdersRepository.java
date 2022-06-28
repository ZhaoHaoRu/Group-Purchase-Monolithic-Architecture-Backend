package com.example.groupbuy.repository;


import com.example.groupbuy.entity.Orders;
import com.example.groupbuy.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrdersRepository extends JpaRepository<Orders,Integer>, JpaSpecificationExecutor<Orders> {

    List<Orders> findByUser(User user);

}
