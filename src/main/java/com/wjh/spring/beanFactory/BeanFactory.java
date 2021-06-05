package com.wjh.spring.beanFactory;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public interface BeanFactory {
        public List getBeanPostProcessors();
        public List getadviceList();
        public ConcurrentHashMap getSingleObjects();
        public ConcurrentHashMap getBeanDefinitionMap();
}
