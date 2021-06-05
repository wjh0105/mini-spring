package com.wjh.spring.jdkProxy;

public class DefaultPointcutAdvisor implements Advisor {
    private Pointcut pointcut;
    private Advice advice;

    public DefaultPointcutAdvisor(Advice advice, Pointcut pointcut) {
        this.pointcut = pointcut;
        this.advice = advice;
    }

    @Override
    public Pointcut getPointcut() {
        return this.pointcut;
    }

    @Override
    public Advice getAdvice() {
        return this.advice;
    }

}
