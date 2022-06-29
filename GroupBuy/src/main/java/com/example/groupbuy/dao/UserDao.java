package com.example.groupbuy.dao;

import com.example.groupbuy.entity.*;

import java.util.Optional;

public interface UserDao {
    User findById(int id);

    User checkUser(String userName, String password);

    User findByUserName(String userName);

    User save(User user);

//    Optional<User> getUserById(int id);
}
