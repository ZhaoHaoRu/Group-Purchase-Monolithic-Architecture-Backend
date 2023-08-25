package com.example.groupbuy.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.example.groupbuy.entity.*;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Optional;
import java.util.Set;


public interface UserRepository extends JpaRepository<User, Integer> {
    @Query(value = "from User where userName = :userName and password = :password")
    User checkUser(@Param("userName") String userName, @Param("password") String password);

    User findByUserId(int id);

    User findByUserName(String userName);

    User save(User user);

    @Transactional
    @Modifying
    @Query(value = "update user set wallet = :newWallet where user_id = :userId", nativeQuery = true)
    void updateWallet(@Param("newWallet") BigDecimal newWallet, @Param("userId") Integer userId);

    @Transactional
    @Modifying
    @Query(value = "update user set wallet = wallet + :newWallet where user_id = :userId", nativeQuery = true)
    void addToWallet(@Param("newWallet") BigDecimal newWallet, @Param("userId") Integer userId);
}
