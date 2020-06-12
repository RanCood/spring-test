package com.zg.st.framework;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author: zg
 * @date: 2020/6/12 12:10
 */
public class ReReadableHttpServletRequest extends HttpServletRequestWrapper {
    private Boolean open = false;
    private byte[] body;

    /**
     * Constructs a request object wrapping the given request.
     *
     * @param request the {@link HttpServletRequest} to be wrapped.
     * @throws IllegalArgumentException if the request is null
     */
    public ReReadableHttpServletRequest(HttpServletRequest request,byte[] body) {
        super(request);
        this.body = body;
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        if(open) {
            throw new RuntimeException("Cannot re-open input stream!");
        }
        open = true;
        return new ServletInputStream() {
            private int offset = 0;
            @Override
            public boolean isFinished() {
                return true;
            }

            @Override
            public boolean isReady() {
                return true;
            }

            @Override
            public void setReadListener(ReadListener readListener) {

            }

            @Override
            public int read() throws IOException {
                for(;;){
                    if(offset >= body.length) {
                        return -1;
                    }
                    int n = body[offset];
                    offset++;
                    return n;
                }
            }
        };
    }

    @Override
    public BufferedReader getReader() throws IOException {
        if(open) {
            throw new RuntimeException("Cannot re-open input stream!");
        }
        open = true;
//        InputStreamReader inputStreamReader = new InputStreamReader(getInputStream(),"UTF-8");
        return new BufferedReader(new InputStreamReader(getInputStream(),"UTF-8"));
    }
}
