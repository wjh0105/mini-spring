package com.wjh.spring.jdkProxy;
//这里需要一个能将Advice转换成MethodInterceptor的适配器AdvisorAdapter,先抽象出一个接口
public interface AdvisorAdapter {
    MethodInterceptor getInterceptor(Advisor advisor);
}
