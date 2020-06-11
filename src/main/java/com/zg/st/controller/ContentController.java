package com.zg.st.controller;

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
    public ModelAndView content(){
        return null;
    }

    @GetMapping("/add")
    public ModelAndView create(HttpSession session){
        return null;
    }
}
