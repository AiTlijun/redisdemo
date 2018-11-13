package com.niu.top.redisdemo.service;

import com.niu.top.redisdemo.entity.User;
import org.springframework.stereotype.Repository;

/**
 * @author hongwei
 * @date 2018/11/12 15:18
 */
//@Repository
public interface UserService {
    void save(User user);

    User findByName(String name);

}
