package com.wjh.spring.jdkProxy;
//通知器，里面含有Pointcut和Advice
public interface Advisor {
    Pointcut getPointcut();

    Advice getAdvice();
}
