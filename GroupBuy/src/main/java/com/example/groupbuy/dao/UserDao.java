package com.example.groupbuy.dao;

import com.example.groupbuy.entity.*;

public interface UserDao {
    User findById(int id);

    User checkUser(String userName, String password);

    User findByUserName(String userName);
}
