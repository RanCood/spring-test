package com.zg.st.servlet.filter;


import com.zg.st.servlet.framework.ReReadableHttpServletRequest;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author: zg
 * @date: 2020/6/12 13:53
 */
@WebFilter(urlPatterns = "/upload/*")
public class ValidateUploadFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        InputStream inputStream = req.getInputStream();
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int n = 0;
        for (; ; ) {
            if ((n = inputStream.read(buffer)) == -1) {
                break;
            }
            output.write(buffer, 0, n);
        }
        System.out.println("filter拿到数据：" + output.toString("UTF-8"));
        chain.doFilter(new ReReadableHttpServletRequest(req, output.toByteArray()), resp);
    }
}
