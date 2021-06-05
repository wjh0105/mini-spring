package com.wjh.spring.jdkProxy;

import java.lang.reflect.Method;

//切点定义
public interface JoinPoint {
    // 切点的执行点
    Object proceed();
    // 获取切点所在的方法
    Method getMethod();
    //目标类
    Object getThis();
    //目标类执行的参数
    Object[] getArguments();
}
