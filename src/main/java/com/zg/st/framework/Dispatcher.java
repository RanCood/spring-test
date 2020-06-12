package com.zg.st.framework;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author: zg
 * @date: 2020/6/10 16:14
 */
public abstract class Dispatcher {
    private static ObjectMapper objectMapper = new ObjectMapper();

    //Controller实例
    public Object instance;
    //Controller 方法实例
    public Method method;
    //方法参数类型
    public Class<?>[] parameterClasses;
    //是否将数据写入bogy
    Boolean isResponseBody = false;
    public Dispatcher() {
    }

    public Dispatcher(Object instance, Method method, Class<?>[] parameterClasses) {
        this.instance = instance;
        this.method = method;
        this.parameterClasses = parameterClasses;
    }
    public Object invoke(HttpServletRequest req, HttpServletResponse resp) throws InvocationTargetException, IllegalAccessException, IOException {
        return null;
    }

    public String writeValueAsString(Object value) {
        try {
            return objectMapper.writeValueAsString(value);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    public byte[] writeValueAsBytes(Object value) {
        try {
            return objectMapper.writeValueAsBytes(value);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }
}
