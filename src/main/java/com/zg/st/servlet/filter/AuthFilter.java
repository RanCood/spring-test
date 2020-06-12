package com.zg.st.servlet.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author: zg
 * @date: 2020/6/11 16:11
 */
@WebFilter(urlPatterns = "/user/*")
public class AuthFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("AuthFilter: check authentication");
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        if(req.getSession().getAttribute("user") != null){
            chain.doFilter(request, response);
        } else {
            System.out.println("AuthFilter: not signin!");
            resp.sendError(401,"未登录");
//            resp.sendRedirect("/signin");
        }
    }
}
