package com.example.groupbuy.daoimpl;

import com.example.groupbuy.entity.*;
import com.example.groupbuy.dao.UserDao;
import com.example.groupbuy.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

@Repository
public class UserDaoImpl implements UserDao {
    @Resource
    private UserRepository userRepository;

    @Override
    public User findById(int id) {
        return userRepository.findByUserId(id);
    }

    @Override
    public User checkUser(String userName, String password) {
        return userRepository.checkUser(userName, password);
    }

    @Override
    public User findByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }
}
