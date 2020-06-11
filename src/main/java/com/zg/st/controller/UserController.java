package com.zg.st.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: zg
 * @date: 2020/6/10 16:40
 */
@Controller
public class UserController {
    @GetMapping("/signin")
    @ResponseBody
    public Map<String,Object> signin(){
        Map<String,Object> map = new HashMap<>();
        map.put("zg","zg");
        map.put("zg1","zg1");
        map.put("zg3","zg3");
        map.put("zg4","zg4");
        return map;
    }

    @GetMapping("/signout")
    public ModelAndView signout(HttpSession session){
        return null;
    }
}
