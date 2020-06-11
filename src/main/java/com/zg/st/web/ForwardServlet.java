package com.zg.st.web;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author: zg
 * @date: 2020/6/9 18:51
 */
@WebServlet(urlPatterns = "/hi")
public class ForwardServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/hello").forward(req, resp);
//        resp.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);
//        resp.addHeader("Location","/hello");
    }
}
