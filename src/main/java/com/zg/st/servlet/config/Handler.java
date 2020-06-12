package com.zg.st.servlet.config;

import java.io.*;
import java.net.Socket;
import java.nio.charset.Charset;

/**
 * @author: zg
 * @date: 2020/6/9 17:03
 */
public class Handler implements Runnable{
    private Socket socket;
    public Handler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            inputStream = this.socket.getInputStream();
            outputStream = this.socket.getOutputStream();
            handle(inputStream,outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if(inputStream != null) {
                    inputStream.close();
                }
                if(outputStream != null) {
                    outputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void handle(InputStream inputStream, OutputStream outputStream) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, Charset.defaultCharset()));
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, Charset.defaultCharset()));
        boolean RequestOk = false;
        String start = reader.readLine();
        if(start.startsWith("GET / HTTP/1.")) {
            RequestOk = true;
        }
        for(;;){
            String header = reader.readLine();
            if(header.isEmpty()){
                break;
            }
            System.out.println(header);
        }
        System.out.println(RequestOk ? "Response OK":"Response error");

        if(!RequestOk) {
            writer.write("404 Not Found\r\n");
            writer.write("Content-Length: 0\r\n");
            writer.write("\r\n");
            writer.flush();
        } else {
            String data = "<html><body><h1>Hello, world!</h1></body></html>";
            int length = data.getBytes(Charset.defaultCharset()).length;
            writer.write("HTTP/1.0 200 OK\r\n");
            writer.write("connection: close\r\n");
            writer.write("Content-TYpe: text/html\r\n");
            writer.write("Content-Length: "+length+"\r\n");
            writer.write("\r\n");
            writer.write(data);
            writer.flush();
        }
    }
}
