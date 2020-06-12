package com.zg.st.framework;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author: zg
 * @date: 2020/6/10 16:14
 */
public class PostDispatcher extends Dispatcher{
    //方法参数类型
    ObjectMapper objectMapper; // JSON映射

    @Override
    public Object invoke(HttpServletRequest request, HttpServletResponse response) throws InvocationTargetException, IllegalAccessException, IOException {
        Object[] arguments = new Object[parameterClasses.length];
        for (int i = 0; i < parameterClasses.length; i++) {
            Class<?> parameterClass = parameterClasses[i];
            if (parameterClass == HttpServletRequest.class) {
                arguments[i] = request;
            } else if (parameterClass == HttpServletResponse.class) {
                arguments[i] = response;
            } else if (parameterClass == HttpSession.class) {
                arguments[i] = request.getSession();
            } else {
                BufferedReader reader = request.getReader();
                arguments[i] = this.objectMapper.readValue(reader,parameterClass);
            }
        }
        Object obj = this.method.invoke(this.instance, arguments);
        if(isResponseBody) {
            PrintWriter pw = response.getWriter();
            pw.write(writeValueAsString(obj));
            pw.flush();
            return null;
        }
        return obj;
    }

    public PostDispatcher() {
    }

    public PostDispatcher(Object instance, Method method, Class<?>[] parameterClasses, ObjectMapper objectMapper) {
        super(instance, method, parameterClasses);
        this.objectMapper = objectMapper;
    }
}
