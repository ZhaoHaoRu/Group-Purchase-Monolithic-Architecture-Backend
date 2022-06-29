package com.example.groupbuy.serviceimpl;

import com.example.groupbuy.dao.*;
import com.example.groupbuy.entity.*;
import com.example.groupbuy.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;

@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserDao userDao;

    @Override
    public User userAuth(String userName, String password) {
        return userDao.checkUser(userName, password);
    }

    // 此处的逻辑是不允许用户名重复, 默认用户最开始钱包中的金额是1000
    @Override
    public User register(String userName, String password, String email) {
        User checkedUser = userDao.findByUserName(userName);
        if(checkedUser != null) {
            return null;
        }
        User user = new User();
        user.setUserName(userName);
        user.setPassword(password);
        user.setEmail(email);
        user.setWallet(BigDecimal.valueOf(1000));
        User afterSave = userDao.save(user);
        return afterSave;
    }
}
