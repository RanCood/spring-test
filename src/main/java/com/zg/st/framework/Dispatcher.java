package com.zg.st.framework;

import java.lang.reflect.Method;

/**
 * @author: zg
 * @date: 2020/6/10 16:14
 */
public abstract class Dispatcher {
    //Controller实例
    public Object instance;
    //Controller 方法实例
    public Method method;
    //方法参数类型
    public Class<?>[] parameterClasses;
    //是否将数据写入bogy
    Boolean isResponseBody = false;
    public Dispatcher() {
    }

    public Dispatcher(Object instance, Method method, Class<?>[] parameterClasses) {
        this.instance = instance;
        this.method = method;
        this.parameterClasses = parameterClasses;
    }
}
