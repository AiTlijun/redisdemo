package com.niu.top.redisdemo.service.impl;

import com.niu.top.redisdemo.entity.User;
import com.niu.top.redisdemo.repository.UserRepository;
import com.niu.top.redisdemo.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author hongwei
 * @date 2018/11/12 15:26
 */
@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserRepository userRepository;

    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public User findByName(String name) {
        return userRepository.findByName(name);
    }
}
