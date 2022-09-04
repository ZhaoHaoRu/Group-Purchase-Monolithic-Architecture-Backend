package com.example.groupbuy.serviceimpl;

import com.example.groupbuy.dao.UserDao;
import com.example.groupbuy.entity.User;
import com.example.groupbuy.entity.UserForDetail;
import com.example.groupbuy.repository.UserForDetailRepository;
import com.example.groupbuy.service.UserAuthService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("userAuthService")
public class UserAuthServiceImpl implements UserAuthService {
    @Resource
    UserDao userDao;

    @Resource
    UserForDetailRepository userForDetailRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserForDetail userForDetail = userForDetailRepository.findByUser(username);
        return userForDetail;
    }

    @Override
    public UserForDetail register(User user) {
        if(user == null)
            return null;
        UserForDetail userForDetail = new UserForDetail();
        userForDetail.setUser(user);
        BCryptPasswordEncoder bcryptPasswordEncoder = new BCryptPasswordEncoder();
        userForDetail.setUsername(user.getUserName());
        /**
         * 储存加密后的密码
         */
        userForDetail.setPassword(bcryptPasswordEncoder.encode(user.getPassword()));
        return userForDetailRepository.save(userForDetail);
    }
}
