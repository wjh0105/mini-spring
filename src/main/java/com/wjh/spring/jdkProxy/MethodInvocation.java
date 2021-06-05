package com.wjh.spring.jdkProxy;

import java.lang.reflect.Method;

//目标切点
public interface MethodInvocation {
    Method getMethod();

    Object[] getArguments();

    Object getThis();

    Object proceed();
}
