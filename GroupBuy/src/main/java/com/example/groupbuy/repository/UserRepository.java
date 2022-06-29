package com.example.groupbuy.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.example.groupbuy.entity.*;


public interface UserRepository extends JpaRepository<User, Integer> {
    @Query(value = "from User where userName = :username and password = :password")
    User checkUser(@Param("userName") String userName, @Param("password") String password);

    User findByUserId(int id);

    User findByUserName(String userName);
}
