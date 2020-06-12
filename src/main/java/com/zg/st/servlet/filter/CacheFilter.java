package com.zg.st.servlet.filter;

import com.zg.st.servlet.framework.CachedHttpServletResponse;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author: zg
 * @date: 2020/6/12 11:20
 */
@WebFilter(urlPatterns = "/slow/*")
public class CacheFilter implements Filter {
    private Map<String,byte[]> cache = new ConcurrentHashMap<>();
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        String url = req.getRequestURI();
        byte[] data = this.cache.get(url);
        resp.setHeader("X-Cache-Hit", data == null ? "No" : "Yes");
        if(data == null){
            CachedHttpServletResponse wrapper = new CachedHttpServletResponse(resp);
            chain.doFilter(req,wrapper);
            data = wrapper.getContent();
            this.cache.put(url,data);
        }
        ServletOutputStream output = resp.getOutputStream();
        output.write(data);
        output.flush();
    }

}
