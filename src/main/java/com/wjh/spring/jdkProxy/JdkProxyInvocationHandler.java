package com.wjh.spring.jdkProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class JdkProxyInvocationHandler implements InvocationHandler{

    private Object target;
    private Object advice;
    public JdkProxyInvocationHandler(Object target, Object advice){
        this.advice =advice;
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println(1);
        Method beforeMethod  = advice.getClass().getDeclaredMethod("before");
        Method afterMethod  = advice.getClass().getDeclaredMethod("after");
        if(beforeMethod!=null) {
                beforeMethod.invoke(advice);
        }
        Object result = method.invoke(target,args);
        if(afterMethod!=null) {
                afterMethod.invoke(advice);
        }
        return null;
    }
}
