package com.zg.st.servlet.web;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author: zg
 * @date: 2020/6/9 18:51
 */
@WebServlet(urlPatterns = "/api")
public class RedirectServlet extends HttpServlet {
    private static final String LANGUAGES = "en,zh";
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        super.doGet(req, resp);
        String name = req.getParameter("name");
        String lang = req.getParameter("lang");
        if(LANGUAGES.contains(lang)) {
            Cookie cookie = new Cookie("lang",lang);
            cookie.setPath("/");
            cookie.setMaxAge(8640000);
            cookie.setHttpOnly(true);
            resp.addCookie(cookie);
        }
        String url = "/hello" + (name == null ? "" : "?name=" + name);
        resp.sendRedirect(url);
//        resp.setContentType("text/html; charset=utf-8");
//        resp.setStatus(HttpServletResponse.SC_BAD_GATEWAY);
//        PrintWriter pw = resp.getWriter();
//        pw.write("<h1>Hello " + name + "!</h1>");
//        pw.flush();
    }
}
