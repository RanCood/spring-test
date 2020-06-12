package com.zg.st.servlet.controller;

import com.zg.st.servlet.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: zg
 * @date: 2020/6/10 16:40
 */
@Controller
public class UserController {
    @PostMapping("/signin")
    @ResponseBody
    public Map<String, Object> signin(HttpSession session, User user) {
        Map<String, Object> map = new HashMap<>();
        if (user != null) {
            session.setAttribute("user", user);
            map.put("code", "200");
            map.put("data", "");
            map.put("msg", "登陆成功");
        }
        return map;
    }

    @GetMapping("/signin")
    @ResponseBody
    public User getUserInfo(HttpSession session) {
        User user = (User) session.getAttribute("user");
        return user;
    }


    @GetMapping("/slow/test")
    @ResponseBody
    public Map<String, Object> getCacheData(HttpSession session) {
        Map<String, Object> map = new HashMap<>();
        map.put("code", "500");
        map.put("data", "缓存数据试试");
        map.put("msg", "缓存数据试试");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return map;
    }

    @GetMapping("/upload/test")
    @ResponseBody
    public Map<String, Object> upload(String name, String id) {
        System.out.println("controller接收到参数：" + name + ",id:" + id);
        Map<String, Object> map = new HashMap<>();
        map.put("code", "500");
        map.put("data", "缓存数据试试");
        map.put("msg", "缓存数据试试");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return map;
    }

    @GetMapping("/signout")
    @ResponseBody
    public Map<String, Object> signout(HttpSession session) {
        session.removeAttribute("user");
        Map<String, Object> map = new HashMap<>();
        map.put("code", "200");
        map.put("data", "");
        map.put("msg", "退出成功");
        return map;
    }
}
