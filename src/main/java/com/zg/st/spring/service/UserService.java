package com.zg.st.spring.service;

import com.zg.st.spring.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.time.ZoneId;

/**
 * @author: zg
 * @date: 2020/6/12 15:28
 */
@Service
public class UserService {
    @Autowired
    private MailService mailSevice;
    @Autowired
    private ZoneId zoneId;

    @Value("classpath:/test.properties")
    private Resource resource;

    public User login(String name, long id){
        User user = new User(name,11,1);
        return user;
    }

    @PostConstruct
    public void init(){
        System.out.println("Init user service with zoneId = " + this.zoneId);
        resource.getFilename();
    }

    @PreDestroy
    public void shutdown(){
        System.out.println("Shutdown user service");
    }
}
