package com.wjh.spring.beanPostProcessor;

import com.wjh.spring.annotation.HAutowired;
import com.wjh.spring.beanFactory.BeanFactory;
import com.wjh.spring.beanFactory.DefaultBeanFactory;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class AutowiredAnnotationBeanPostProcessor implements BeanPostProcessor{
//    BeanFactory beanFactory = new DefaultBeanFactory();
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName, BeanFactory beanFactory) {
       Map beanMap = beanFactory.getSingleObjects();
        Class clazz  =bean.getClass();
        Field[] fields = clazz.getDeclaredFields();
        if(fields != null)
        for(Field field :fields){
            //如果有该注解，则进行注入对象实例
            if(field.isAnnotationPresent(HAutowired.class)) {
                //1.先获取类型
                Class<?> fieldType = field.getType();
                //2.从IOC容器中查找有对应的bean或者子类或实现类
                for (Object o :beanMap.values()) {

                    if(fieldType.isAssignableFrom(o.getClass())||fieldType.getName().equals(o.getClass().getName())){
                        field.setAccessible(true);
                        try {
                            field.set(bean,o);
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    }
                }
       return bean;
            }
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName,BeanFactory beanFactory) {



        return bean;
    }
}
