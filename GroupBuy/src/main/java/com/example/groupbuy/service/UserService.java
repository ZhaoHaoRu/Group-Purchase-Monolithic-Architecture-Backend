package com.example.groupbuy.service;

import com.example.groupbuy.entity.*;
import com.example.groupbuy.utils.messageUtils.Message;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Set;

public interface UserService {
    Message<User> userAuth(String userName, String password);

    Message<User> register(String userName, String password, String email);

    Message<User> getUserById(int id);

    Message<Set<GroupBuying>> getUserCollection(int id);

    Message<Set<Address> > getUserAddress(int id);

    // 返回值为addressId
    Message<Integer> setNewAddress(int userId, String receiver, String phone, String region, String location);

    Message<Set<GroupBuying>> getCreatedGroup(int userId);

    Set<Goods> FilterByInventory(Set<Goods> goodsList);

    Set<GroupBuying> GroupFilter(User user, Set<GroupBuying> groups);

    Message<Set<GroupBuying>> RecommendGroup(int userId);
}
