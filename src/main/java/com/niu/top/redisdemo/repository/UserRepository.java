package com.niu.top.redisdemo.repository;

import com.niu.top.redisdemo.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author hongwei
 * @date 2018/11/12 15:19
 */
public interface UserRepository extends MongoRepository<User, String> {

   User findByName(String name);
}
