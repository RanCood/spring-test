package com.zg.st.servlet.framework;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.reflections.Reflections;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * @author: zg
 * @date: 2020/6/10 16:11
 */
@WebServlet(urlPatterns = "/")
public class DispatcherServlet extends HttpServlet {
    private Map<String, GetDispatcher> getDispatcherMap = new HashMap<>();
    private Map<String, PostDispatcher> postDispatcherMap = new HashMap<>();
    private ViewEngine viewEngine;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        System.out.println("doGet...");
        String path = req.getRequestURI().substring(req.getContextPath().length());
        Dispatcher dispatcher = getDispatcherMap.get(path);
        doService(req,resp,dispatcher);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        System.out.println("doPost...");
        String path = req.getRequestURI().substring(req.getContextPath().length());
        Dispatcher dispatcher = postDispatcherMap.get(path);
        doService(req,resp,dispatcher);
    }

    public void doService(HttpServletRequest req, HttpServletResponse resp, Dispatcher dispatcher) throws IOException {
        if (Objects.isNull(dispatcher)) {
            resp.sendError(404);
            return;
        }
        ModelAndView mv = null;
        Object obj = null;
        try {
            obj = dispatcher.invoke(req, resp);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        if (obj == null) {
            return;
        }
        if (obj instanceof ModelAndView) {
            if (mv == null) {
                return;
            }
            if (mv.view.startsWith("redirect:")) {
                resp.sendRedirect(mv.view.substring(9));
                return;
            }
            PrintWriter pw = resp.getWriter();
            this.viewEngine.render(mv, pw);
            pw.flush();
        }
    }

    @Override
    public void init() throws ServletException {
        URL url = Thread.currentThread().getContextClassLoader().getResource("com/zg/st");
        System.out.println("protocal:" + url.getProtocol());
        System.out.println("path:" + url.getPath());
        scanControllers();
    }


    private void scanControllers() {
        Reflections reflections = new Reflections("com.zg.st");
        Set<Class<?>> typesAnnotated = reflections.getTypesAnnotatedWith(Controller.class);
        for (Class<?> clazz : typesAnnotated) {
            Object instance = null;
            try {
                instance = clazz.newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            Method[] methods = clazz.getMethods();
            for (Method method : methods) {
                if (method.isAnnotationPresent(GetMapping.class)) {
                    GetMapping annotation = method.getAnnotation(GetMapping.class);
                    String[] path = annotation.value();
                    Class<?>[] parameterTypes = method.getParameterTypes();
                    String[] parameterNames = new String[parameterTypes.length];
                    for (int i = 0; i < parameterTypes.length; i++) {
                        parameterNames[i] = parameterTypes[i].getName();
                    }
                    GetDispatcher getDispatcher = new GetDispatcher(instance, method, parameterNames, parameterTypes);
                    if (method.isAnnotationPresent(ResponseBody.class)) {
                        getDispatcher.isResponseBody = true;
                    }
                    this.getDispatcherMap.put(path[0], getDispatcher);
                } else if (method.isAnnotationPresent(PostMapping.class)) {
                    PostMapping annotation = method.getAnnotation(PostMapping.class);
                    String[] path = annotation.value();
                    Class<?>[] parameterTypes = method.getParameterTypes();
                    ObjectMapper objectMapper = new ObjectMapper();
                    PostDispatcher postDispatcher = new PostDispatcher(instance, method, parameterTypes, objectMapper);
                    if (method.isAnnotationPresent(ResponseBody.class)) {
                        postDispatcher.isResponseBody = true;
                    }
                    this.postDispatcherMap.put(path[0], postDispatcher);
                }
            }
        }
    }
}
