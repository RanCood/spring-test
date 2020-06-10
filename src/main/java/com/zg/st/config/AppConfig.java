package com.zg.st.config;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author: zg
 * @date: 2020/6/9 15:57
 */
public class AppConfig {
    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(8081);
        System.out.println("server is running...");
        for (;;){
            Socket socket = server.accept();
            Thread handler = new Thread(new Handler(socket));
            handler.start();
        }
    }

}
