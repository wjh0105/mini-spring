package com.wjh.spring.beanPostProcessor;

import com.wjh.spring.beanFactory.BeanFactory;

import java.util.Map;

public class CglibProxyAnnotationBeanPostProcessor implements BeanPostProcessor{
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName, BeanFactory beanFactory) {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName,BeanFactory beanFactory) {

        return bean;
    }
}
