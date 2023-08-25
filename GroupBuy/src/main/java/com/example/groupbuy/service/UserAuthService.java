package com.example.groupbuy.service;

import com.example.groupbuy.entity.User;
import com.example.groupbuy.entity.UserForDetail;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserAuthService extends UserDetailsService {

    UserForDetail register(User user);
}
