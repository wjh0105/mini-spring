package com.wjh.spring.jdkProxy;
//方法拦截,这里可以进行方法拦截
public interface MethodInterceptor extends Advice{
    Object invoke(MethodInvocation invocation);
}
