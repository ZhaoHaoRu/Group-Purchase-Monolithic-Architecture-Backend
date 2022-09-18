package com.example.groupbuy.repository;

import com.example.groupbuy.entity.User;
import com.example.groupbuy.entity.UserForDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


public interface UserForDetailRepository extends JpaRepository<UserForDetail, Integer> {
    @Query(value = "from UserForDetail where username = :username")
    UserForDetail findByUser(@Param("username") String username);
}
