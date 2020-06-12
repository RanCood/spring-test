package com.zg.st.servlet.framework;

import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author: zg
 * @date: 2020/6/12 11:10
 */
public class CachedHttpServletResponse extends HttpServletResponseWrapper {
    private Boolean open = false;
    private ByteArrayOutputStream output = new ByteArrayOutputStream();
    /**
     * Constructs a response adaptor wrapping the given response.
     *
     * @param response the {@link HttpServletResponse} to be wrapped.
     * @throws IllegalArgumentException if the response is null
     */
    public CachedHttpServletResponse(HttpServletResponse response) {
        super(response);
    }

    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        if(open) {
            throw new IllegalStateException("Cannot re-open stream!");
        }
        open = true;
        return new ServletOutputStream() {
            @Override
            public boolean isReady() {
                return true;
            }

            @Override
            public void setWriteListener(WriteListener writeListener) {
            }

            @Override
            public void write(int b) throws IOException {
                output.write(b);
            }
        };
    }

    @Override
    public PrintWriter getWriter() throws IOException {
        if(open) {
            throw new IllegalStateException("Cannot re-open writer!");
        }
        open = true;
        return new PrintWriter(output,false);
    }
    public byte[] getContent(){
        return output.toByteArray();
    }
}
