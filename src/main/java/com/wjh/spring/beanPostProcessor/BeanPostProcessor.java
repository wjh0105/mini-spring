package com.wjh.spring.beanPostProcessor;

import com.wjh.spring.beanFactory.BeanFactory;

import java.util.Map;

public interface BeanPostProcessor {
    Object postProcessBeforeInitialization(Object bean, String beanName, BeanFactory beanFactory);
    Object postProcessAfterInitialization(Object bean,String beanName,BeanFactory beanFactory);
}
