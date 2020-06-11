package com.zg.st.web;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Objects;

/**
 * @author: zg
 * @date: 2020/6/9 18:51
 */
@WebServlet(urlPatterns = "/hello")
public class MyServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        super.doGet(req, resp);
        System.out.println("contextPath:"+req.getContextPath());
        System.out.println("URI:"+req.getRequestURI());
        System.out.println("URL:"+req.getRequestURL());
        System.out.println(req.getRequestedSessionId());
        System.out.println(req.getContentType());
        System.out.println(req.getContextPath());
        System.out.println(req.getContentLength());
        System.out.println(req.getQueryString());
        System.out.println(req.getCookies());
        System.out.println(req.getHeader("kkk"));
        System.out.println(req.getRemoteAddr());
        System.out.println(req.getRemoteHost());
        System.out.println(req.getRemotePort());
        System.out.println(req.getRemoteUser());
        System.out.println(req.getScheme());
        System.out.println(req.getSession().getId());
        String name = req.getParameter("name");
        if (Objects.isNull(name)) {
            name = "world";
        }
        resp.setContentType("text/html; charset=utf-8");
//        resp.setStatus(HttpServletResponse.SC_BAD_GATEWAY);
        PrintWriter pw = resp.getWriter();
        pw.write("<h1>Hello " + name + "!</h1>");
        pw.flush();
    }
}
