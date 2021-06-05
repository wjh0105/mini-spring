package com.wjh.spring.beanFactory;

import com.wjh.spring.beanPostProcessor.BeanPostProcessor;
import com.wjh.spring.ioc.BeanDefinition;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultBeanFactory implements BeanFactory{
    public static ConcurrentHashMap<String, Object> singleObjects = new ConcurrentHashMap<>();//单例池，实例对象
    public static ConcurrentHashMap<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>();//bean定义信息，类的信息
    public static List<BeanPostProcessor>beanPostProcessors = new ArrayList<>();
    public static List<Object>adviceList = new ArrayList<>();
    @Override
    public List getBeanPostProcessors() {
        return beanPostProcessors;
    }

    @Override
    public List getadviceList() {
        return adviceList;
    }

    public List getAdviceList() {
        return adviceList;
    }

    @Override
    public ConcurrentHashMap getSingleObjects() {
        return singleObjects;
    }

    @Override
    public ConcurrentHashMap getBeanDefinitionMap() {
        return beanDefinitionMap;
    }
}
