package com.zg.st.spring.web;

import com.zg.st.spring.entity.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: zg
 * @date: 2020/6/12 17:58
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @GetMapping("")
    public User load(){
        User user = new User("zg",25,0);
        return user;
    }
}
