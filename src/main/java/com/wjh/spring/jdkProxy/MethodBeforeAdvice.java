package com.wjh.spring.jdkProxy;

import java.lang.reflect.Method;

//抽象出一个前置通知,MethodBeforeAdvice
public interface MethodBeforeAdvice {
    void before(Method method, Object[] args, Object target);

}
