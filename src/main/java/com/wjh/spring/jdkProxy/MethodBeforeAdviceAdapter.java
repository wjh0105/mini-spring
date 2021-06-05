package com.wjh.spring.jdkProxy;
//并提供一个能将MethodBeforeAdvice转换成MethodInterceptor的一个实现
public class MethodBeforeAdviceAdapter implements AdvisorAdapter{
    @Override
    public MethodInterceptor getInterceptor(Advisor advisor) {
        MethodBeforeAdvice advice = (MethodBeforeAdvice) advisor.getAdvice();
        return new MethodBeforeAdviceInterceptor(advice);
    }
}
