package com.zg.st.servlet.web;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author: zg
 * @date: 2020/6/11 15:28
 */
@WebServlet(urlPatterns = {"/favicon.ico", "/static/*"})
public class FileServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletContext ctx = req.getServletContext();
        String urlPath = req.getRequestURI().substring(ctx.getContextPath().length());
        String realPath = ctx.getRealPath(urlPath);
        if (realPath == null) {
            // 无法获取到路径:
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        Path path = Paths.get(realPath);
        if (!path.toFile().exists()) {
            // 无法获取到路径:
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        String mime = Files.probeContentType(path);
        if (mime == null) {
            mime = "application/octet-stream";
        }
        resp.setContentType(mime);
        ServletOutputStream out = resp.getOutputStream();
        BufferedInputStream input = new BufferedInputStream(new FileInputStream(realPath));
        try {
            byte[] bytes = new byte[1024];
            int n = 0;
            while ((n = input.read(bytes)) != -1) {
                out.write(bytes);
            }
        } finally {
            input.close();
        }
        out.flush();
    }
}
