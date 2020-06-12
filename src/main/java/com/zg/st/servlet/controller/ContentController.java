package com.zg.st.servlet.controller;

import com.zg.st.servlet.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

/**
 * @author: zg
 * @date: 2020/6/10 16:40
 */
@Controller
public class ContentController {
    @GetMapping("/content")
    public User content(HttpSession session){
        User user = (User) session.getAttribute("user");
        return null;
    }

    @GetMapping("/add")
    public ModelAndView create(HttpSession session){
        return null;
    }
}
