package com.wjh.spring.jdkProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;

public class JdkDynamicAopProxy implements AopProxy, InvocationHandler {
    private AdvisedSupport advisedSupport;
    public JdkDynamicAopProxy(AdvisedSupport advisedSupport) {
        this.advisedSupport = advisedSupport;
    }
        @Override
    public Object getProxy() {
 return Proxy.newProxyInstance(this.getClass().getClassLoader(), advisedSupport.getInterfaces(), this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        MethodInvocation invocation;
        List<Object> chain = this.advisedSupport.getInterceptorsAndDynamicInterceptionAdvice();
        invocation = new ReflectiveMethodInvocation(proxy, advisedSupport.getTarget(), method, args, chain);
        return ((ReflectiveMethodInvocation) invocation).proceed();

    }
}
