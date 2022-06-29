package com.example.groupbuy.service;

import com.example.groupbuy.entity.*;
import com.example.groupbuy.utils.messageUtils.Message;

public interface UserService {
    User userAuth(String userName, String password);

    User register(String userName, String password, String email);

    Message<User> getUserById(int id);
}
