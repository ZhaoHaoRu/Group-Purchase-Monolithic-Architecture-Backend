package com.example.groupbuy.service;

import com.example.groupbuy.entity.*;

public interface UserService {
    User userAuth(String userName, String password);

    User register(String userName, String password, String email);

}
