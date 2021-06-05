package com.wjh.spring.ioc;
import com.wjh.spring.Initialization.Initialization;
import com.wjh.spring.annotation.*;
import com.wjh.spring.aware.Aware;
import com.wjh.spring.beanFactory.DefaultBeanFactory;
import com.wjh.spring.beanPostProcessor.AutowiredAnnotationBeanPostProcessor;
import com.wjh.spring.beanPostProcessor.BeanPostProcessor;

import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
public class ApplicationContext {
    private Class config;//配置文件
    private ConcurrentHashMap<String, Object> singleObjects = null;//单例池，实例对象
    private ConcurrentHashMap<String, BeanDefinition> beanDefinitionMap = null;//bean定义信息，类的信息
    private List<BeanPostProcessor>beanPostProcessors = null;
    private List<Object>adviceList = null;
    DefaultBeanFactory beanFactory = null;
    public ApplicationContext(Class<?> config) throws Exception {
        this.config = config;
        beanFactory = new DefaultBeanFactory();
        beanFactoryInitial(beanFactory);//初始化beanFactory
        Scan(config);//扫描目录文件，把类信息加入到beanDefinitionMap
        singletonBeanCreate();//实例化单实例bean

    }

    private void beanFactoryInitial(DefaultBeanFactory beanFactory) {
        this.singleObjects = beanFactory.getSingleObjects();
        this.beanDefinitionMap = beanFactory.getBeanDefinitionMap();
        this.beanPostProcessors = beanFactory.getBeanPostProcessors();
        this.adviceList = beanFactory.getAdviceList();
        try {
          BeanPostProcessor beanPostProcessor =  AutowiredAnnotationBeanPostProcessor.class.newInstance();
            beanPostProcessors.add(beanPostProcessor);
            if(config.isAnnotationPresent(HEnableAspectJAutoProxy.class)){
               HImport hImport =  HEnableAspectJAutoProxy.class.getDeclaredAnnotation(HImport.class);
            Class proxyBeanPostProcessor = hImport.value();
            beanPostProcessors.add((BeanPostProcessor)proxyBeanPostProcessor.newInstance());
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private void singletonBeanCreate() throws Exception {
//        Map<String,Object> beforeInitBean =new HashMap<>();
        for (Map.Entry<String, BeanDefinition> entry : beanDefinitionMap.entrySet()) {
            String beanName = entry.getKey();
            BeanDefinition beanDefinition = entry.getValue();
            if (beanDefinition.getScope().equals("singleton")) {
                Object o =beanDefinition.getClazz().newInstance();
//                Object o = creatBean(beanName,beanDefinition);
//                System.out.println(beanName);
//                singleObjects.put(beanName, o);
                singleObjects.put(beanName,o);
            }
        }
        for (Map.Entry<String, Object> entry : singleObjects.entrySet()) {
            String beanName = entry.getKey();
            Object o = entry.getValue();
            Object awareObject = handleAware(beanName,o);//忽略此Aware接口回调方法
            Object beforeObject =  BeanPostProcessorBeforInitialization(awareObject,beanName);//后置处理器的初始化前方法
            Object initObject =  invokeInitMethond(beforeObject,beanName);
            Object afterObject =BeanPostProcessorAfterInitialization(initObject,beanName);//后置处理器的初始化后方法--AOP
            singleObjects.put(beanName,afterObject);
        }


    }

    private Object creatBean(String beanName,BeanDefinition beanDefinition) throws Exception {
        Class clazz = beanDefinition.getClazz();
        Object o = clazz.newInstance();
      Object awareObject = handleAware(beanName,o);//忽略此Aware接口回调方法
      Object beforeObject =  BeanPostProcessorBeforInitialization(awareObject,beanName);//后置处理器的初始化前方法
      Object initObject =  invokeInitMethond(beforeObject,beanName);
        Object afterObject =BeanPostProcessorAfterInitialization(initObject,beanName);//后置处理器的初始化后方法--AOP
        return  o;
//        依赖注入
//        for(Field field : clazz.getDeclaredFields()){
//            if(field.isAnnotationPresent(HAutowired.class)){
//                String name = field.getName();
//                Object o1 = getBean(name);
//                field.setAccessible(true);
//                field.set(o,o1);
//            }
//        }
//        return o;
    }

    private Object BeanPostProcessorAfterInitialization(Object awareObject, String name) {
        for (BeanPostProcessor beanPostProcessor : beanPostProcessors) {
            awareObject=    beanPostProcessor.postProcessAfterInitialization(awareObject,name,beanFactory);
        }
        return awareObject;
    }

    private Object invokeInitMethond(Object o, String name) throws Exception {
        if(o instanceof Initialization){
          o =  ((Initialization) o).afterPropertiesSet();
        }
        return o;
    }

    private Object BeanPostProcessorBeforInitialization(Object awareObject, String beanName) {
        for (BeanPostProcessor beanPostProcessor : beanPostProcessors) {
            beanPostProcessor.postProcessBeforeInitialization(awareObject,beanName,beanFactory);
        }

        return awareObject;
    }

    private Object handleAware(String beanName, Object o) {
        if(o instanceof Aware){

            return o;
        }
        return o;
    }

    public void Scan(Class<?> config) {
        HComponentScan hComponentScan = config.getDeclaredAnnotation(HComponentScan.class);
        String basePackage = hComponentScan.basePackage();
//        System.out.println(basePackage);
//        basePackage = basePackage.replace(".", "/");
//        System.out.println(basePackage);
        ClassLoader classLoader = this.getClass().getClassLoader();
        URL url = classLoader.getResource(basePackage.replace(".", "/"));

        File file = new File(url.getPath());
        recursionFile1(file, basePackage);
    }

    private void recursionFile1(File file, String basePackage) {
        File[] files = file.listFiles();//获取当前目录的所有文件
        if (files != null) {
            for (int i = 0; i < files.length; i++) {
                File absoluteFile = files[i].getAbsoluteFile();
                if (absoluteFile.isDirectory()) {
                    recursionFile1(absoluteFile, basePackage); //是文件夹，则继续迭代
                } else if (absoluteFile.toString().endsWith(".class")) { //是文件则判断是否是class文件
                    //File.separator  与系统有关的默认名称分隔符，它被表示为一个字符串。此字符串只包含一个字符 即/ 或\
                    String temp = absoluteFile.toString().replace(File.separator, ".");
                    //substring  返回一个新的字符串，它是此字符串的一个子字符串。
                    String className = temp.substring(temp.indexOf(basePackage));//com.controller.UserController.class
                    className = className.substring(0, className.lastIndexOf("."));//com.controller.UserController
//                    System.out.println(className);
                    try {
                        Class<?> clazz = Class.forName(className);//获取class对象
                        if(BeanPostProcessor.class.isAssignableFrom(clazz)&&!clazz.equals(BeanPostProcessor.class)){
                            BeanPostProcessor beanPostProcessor =(BeanPostProcessor) clazz.newInstance();
                            beanPostProcessors.add(beanPostProcessor);//初始化bean后置处理器
                        }
                            if(clazz.isAnnotationPresent(HAdvice.class)){
                                adviceList.add(clazz.newInstance());
                            }

                        //判断该对象是否因该加入容器，即是否有相应注解
                        if (clazz.isAnnotationPresent(HComponent.class) || clazz.isAnnotationPresent(HController.class)
                                || clazz.isAnnotationPresent(HService.class) || clazz.isAnnotationPresent(HRepository.class)) {
                            // 创建对象
                            BeanDefinition beanDefinition = new BeanDefinition();
                            String beanName = clazz.getSimpleName();
//                            System.out.println("nane12:"+beanName);
                            beanDefinition.setClazz(clazz);
                            if (clazz.isAnnotationPresent(HScope.class)) {
                                HScope hScope = clazz.getDeclaredAnnotation(HScope.class);
                                beanDefinition.setScope(hScope.value());
                            } else beanDefinition.setScope("singleton");
//                            System.out.println("beanDefinition beanNanem:==="+beanName);
                            beanDefinitionMap.put(beanName, beanDefinition);
                        }

                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InstantiationException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public Object getBean(String beanName) throws Exception {
//        System.out.println("get beanNaem ::"+beanName);
//        System.out.println(beanDefinitionMap.contains(beanName));
        if (beanDefinitionMap.containsKey(beanName)) {
            BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
            if (beanDefinition.getScope().equals("singleton")) {
                return singleObjects.get(beanName);
            } else {
                return creatBean(beanName,beanDefinitionMap.get(beanName));
            }
        }
        else {
//            return  null;
            throw new NullPointerException();
        }
    }


}
