package com.wjh.spring.jdkProxy;
import java.lang.reflect.Method;
import java.util.List;

public class ReflectiveMethodInvocation implements MethodInvocation{
    // 代理对象
    private Object proxy;
    // 目标对象
    private Object target;
    // 目标方法
    private Method method;
    // 一连串的通知器,都封装成MethodInterceptor
    protected final List<?> interceptorsAndDynamicMethodMatchers;
    private Object[] aguments;
    // 代表执行到第几个通知器链
    private int currentInterceptorIndex = -1;
    public ReflectiveMethodInvocation(Object proxy, Object target, Method method, Object[] aguments,
                                      List<Object> interceptorsAndDynamicMethodMatchers) {
        this.proxy = proxy;
        this.target = target;
        this.method = method;
        this.aguments = aguments;
        this.interceptorsAndDynamicMethodMatchers = interceptorsAndDynamicMethodMatchers;
    }
    public Object proceed() {
        if (this.currentInterceptorIndex == interceptorsAndDynamicMethodMatchers.size() - 1) {
            try {
                //目标方法的最终调用
                return method.invoke(target, aguments);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        //这里就是将advice封装成MethodInterceptor.从链表中一个一个执行
        Object interceptorOrInterceptionAdvice = this.interceptorsAndDynamicMethodMatchers
                .get(++this.currentInterceptorIndex);
        //这里将ReflectiveMethodInvocation自己用传入了,所以是一个递归调用
        return ((MethodInterceptor) interceptorOrInterceptionAdvice).invoke(this);
    }
    public Method getMethod() {
        return method;
    }
    public Object getThis() {
        return target;
    }

    public Object[] getArguments() {
        return aguments;
    }

}
