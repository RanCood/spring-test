package com.zg.st.config;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

/**
 * 读取配置文件
 * @author: zg
 * @date: 2020/6/11 14:59
 */
public class ResourceTest {
    public static void main(String[] args) throws IOException {
        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        URL url = cl.getResource("");
        InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream("test.properties");
        Properties properties = new Properties();
        properties.load(stream);
        System.out.println("name:"+properties.get("name"));
        System.out.println("port:"+properties.get("port"));
    }
}
