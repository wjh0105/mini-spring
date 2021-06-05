package com.wjh.spring.jdkProxy;
import java.util.ArrayList;
import java.util.List;

//创建代理对象的载体，如目标对象，advice，ponitcut,advisor
public class AdvisedSupport{
    private Object target;
    // 代理对象实现的接口
    private List<Class<?>> interfaces = new ArrayList<>();
    private List<Advisor> advisors = new ArrayList<>();
    private AdvisorAdapter advisorAdapter = new MethodBeforeAdviceAdapter();
    public Object getTarget() {
        return target;
    }
    public void setTarget(Object target) {
        this.target = target;
    }
    // 添加目标对象对象所实现的接口
    public void addInterface(Class<?>... interfaces) {
        this.interfaces.clear();
        for (Class<?> ifc : interfaces) {
            this.interfaces.add(ifc);
        }
    }
    public Class<?>[] getInterfaces() {
        return interfaces.toArray(new Class<?>[0]);
    }

    public void setInterfaces(List<Class<?>> interfaces) {
        this.interfaces = interfaces;
    }
    public List<Advisor> getAdvisors() {
        return advisors;
    }

    public void setAdvisors(List<Advisor> advisors) {
        this.advisors = advisors;
    }
    //根据advisor,获取到advice,并将advice,适配成所有的MethodInterceptor
    public List<Object> getInterceptorsAndDynamicInterceptionAdvice() {
        List<Object> interceptorList = new ArrayList<>();
        for (Advisor advisor : this.advisors) {
            if (advisor instanceof DefaultPointcutAdvisor) {
                interceptorList.add(advisorAdapter.getInterceptor(advisor));
            }
        }
        return interceptorList;
    }

    //如果添加的是一个advice,封装成一个Advisor,并加入链表
    public void addAdvice(Advice advice) {
        addAdvisor(new DefaultPointcutAdvisor(advice, null));
    }

    public void addAdvisor(Advisor advisor) {
        this.advisors.add(advisor);
    }
}
