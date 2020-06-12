package com.zg.st.servlet.web;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author: zg
 * @date: 2020/6/12 14:22
 */
@WebServlet(urlPatterns = "/upload/*")
public class UploadServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletInputStream input = req.getInputStream();
        byte[] buffer = new byte[1024];
        int n = 0;
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        for (; ; ) {
            if ((n = input.read(buffer)) == -1) {
                break;
            }
            output.write(buffer, 0, n);
        }
        String text = output.toString("UTF-8");
        System.out.println("servlet拿到数据：" + text);
        PrintWriter pw = resp.getWriter();
        pw.write("<h1>Uploaded:</h1>");
        pw.write("<pre><code>");
        pw.write(text);
        pw.write("</code></pre>");
        pw.flush();

    }
}
