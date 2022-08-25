package com.example.groupbuy.dao;

import com.example.groupbuy.entity.*;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.Set;

public interface UserDao {
    User findById(int id);

    User checkUser(String userName, String password);

    User findByUserName(String userName);

    User save(User user);

    Address saveAddress(Address address);

    Set<UserHistory> findUserHistoryByUser(User user);

    UserHistory findByUserAndAndCategory(User user, String category);

    void updateLiking(Integer newLiking, Integer userId, String category);

    UserHistory saveUserHistory(UserHistory userHistory);
}
