package com.zg.st.framework;

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
    //方法参数名称
    private String[] parameterNames;

    @Override
    public Object invoke(HttpServletRequest req, HttpServletResponse resp) throws InvocationTargetException, IllegalAccessException, IOException {
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
            PrintWriter pw = resp.getWriter();
            pw.write(writeValueAsString(obj));
            pw.flush();
            return null;
        }
        return obj;
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
