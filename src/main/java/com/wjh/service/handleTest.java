package com.wjh.service;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class handleTest implements InvocationHandler {
    Object target;
    public handleTest(Object o){
        target = o;
    }
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("test");
       Object r= method.invoke(target,args);
        return r;
    }
}
