package com.wjh.spring.jdkProxy;

public interface AopProxyFactory {
    Object createAopProxy(AdvisedSupport advisedSupport);
}
