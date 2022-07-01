package com.example.groupbuy.service;

import com.example.groupbuy.entity.*;
import com.example.groupbuy.utils.messageUtils.Message;

import java.util.Set;

public interface UserService {
    Message<User> userAuth(String userName, String password);

    Message<User> register(String userName, String password, String email);

    Message<User> getUserById(int id);

    Message<Set<GroupBuying>> getUserCollection(int id);

    Message<Set<Address> > getUserAddress(int id);

}
