package com.wjh.spring.cglibProxy;



import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;
import java.util.function.DoubleToIntFunction;

public class cglibProxy implements MethodInterceptor {
        Object target;
    Object advice;
        public cglibProxy(Object target, Object advice ){
            this.advice = advice;
           this.target = target;
        }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
       Method beforeMethod =  advice.getClass().getDeclaredMethod("before");
       Method afterMethod =  advice.getClass().getDeclaredMethod("after");
       if(beforeMethod!=null) beforeMethod.invoke(advice);
       Object result =  methodProxy.invokeSuper(o,objects);
        if(afterMethod!=null) afterMethod.invoke(advice);
        return result;
    }
}
