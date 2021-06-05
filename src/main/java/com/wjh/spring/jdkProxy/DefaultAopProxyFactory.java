package com.wjh.spring.jdkProxy;

public class DefaultAopProxyFactory implements AopProxyFactory{
    @Override
    public Object createAopProxy(AdvisedSupport advisedSupport) {
        Object target = advisedSupport.getTarget();
        if (target.getClass().getInterfaces().length > 0) {
            return new JdkDynamicAopProxy(advisedSupport).getProxy();//代理器而已，没有得到代理对象
        }else{
            //cglib
            return null;
        }
    }
}
