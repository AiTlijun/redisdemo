package com.niu.top.redisdemo.controller;

import com.niu.top.redisdemo.entity.User;
import com.niu.top.redisdemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author hongwei
 * @date 2018/11/12 15:22
 */
@Controller
@RequestMapping(value = "/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * save use before findName
     *
     * @return
     */
    @GetMapping("/save")
    public User save() {
        User user = new User(2, "Tseng", 21);
        mongoTemplate.save(user);
        return user;
    }

    @RequestMapping("/add")
    public void add(HttpServletRequest req, HttpServletResponse resp, User user) {
        userService.save(user);
        resp.setContentType("text/html");
        resp.setCharacterEncoding("utf-8");
        try {
            resp.getWriter().write("添加数据信息："+user.toString());
            resp.getWriter().flush();
            resp.getWriter().close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return;
    }

    @GetMapping("/find")
    public List<User> find() {
        List<User> userList = mongoTemplate.findAll(User.class);
        return userList;
    }

    /**
     * input String name "Tseng"
     *
     * @param name
     * @return
     */
    @GetMapping("/findByName")
    public User findByName(@RequestParam("name") String name) {
        User user = userService.findByName(name);
        return user;
    }
}
