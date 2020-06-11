package com.zg.st.framework;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author: zg
 * @date: 2020/6/10 16:14
 */
public class GetDispatcher extends Dispatcher {
    private static ObjectMapper objectMapper = new ObjectMapper();
    //方法参数名称
    private String[] parameterNames;

    public Object invoke(HttpServletRequest req, HttpServletResponse resp) throws InvocationTargetException, IllegalAccessException {
        Object[] arguments = new Object[parameterClasses.length];
        for (int i = 0; i < parameterClasses.length; i++) {
            Class<?> parameterClass = parameterClasses[i];
            String parameterName = parameterNames[i];
            if (parameterClass == HttpServletRequest.class) {
                arguments[i] = req;
            } else if (parameterClass == HttpServletResponse.class) {
                arguments[i] = resp;
            } else if (parameterClass == HttpSession.class) {
                arguments[i] = req.getSession();
            } else if (parameterClass == int.class) {
                arguments[i] = Integer.valueOf(getOrDefault(req, parameterName, "0"));
            } else if (parameterClass == boolean.class) {
                arguments[i] = Boolean.valueOf(getOrDefault(req, parameterName, "false"));
            } else if (parameterClass == long.class) {
                arguments[i] = Long.valueOf(getOrDefault(req, parameterName, "0"));
            } else if (parameterClass == String.class) {
                arguments[i] = getOrDefault(req, parameterName, "0");
            } else {
                throw new RuntimeException("Missing handler for type:" + parameterClass);
            }
        }
        Object obj = this.method.invoke(this.instance, arguments);
        if (isResponseBody) {
            try {
                PrintWriter pw = resp.getWriter();
                pw.write(writeValueAsString(obj));
                pw.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            return this.method.invoke(this.instance, arguments);
        }
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

    private String getOrDefault(HttpServletRequest request, String parameterName, String defaultValue) {
        String value = request.getParameter(parameterName);
        return value == null ? defaultValue : value;
    }

    public GetDispatcher() {
    }

    public GetDispatcher(Object instance, Method method, String[] parameterNames, Class<?>[] parameterClasses) {
        super(instance, method, parameterClasses);
        this.parameterNames = parameterNames;
    }

    public String[] getParameterNames() {
        return parameterNames;
    }

    public void setParameterNames(String[] parameterNames) {
        this.parameterNames = parameterNames;
    }

}
