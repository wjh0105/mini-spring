package com.wjh.spring.beanPostProcessor;
import com.wjh.service.Test;
import com.wjh.service.handleTest;
import com.wjh.spring.annotation.HPointcut;
import com.wjh.spring.beanFactory.BeanFactory;
import com.wjh.spring.cglibProxy.cglibProxy;
import com.wjh.spring.jdkProxy.JdkProxyInvocationHandler;
import net.sf.cglib.proxy.Enhancer;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;
public class ProxyAnnotationBeanPostProcessor implements BeanPostProcessor{
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName, BeanFactory beanFactory) {
        return bean;
    }
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName,BeanFactory beanFactory) {
        List advices = beanFactory.getadviceList();
        for (Object advice : advices) {
          Method[] methods = advice.getClass().getDeclaredMethods();
            for (Method method : methods) {
                if(method.isAnnotationPresent(HPointcut.class)){
                    HPointcut pointcut = method.getDeclaredAnnotation(HPointcut.class);
                    Class target = pointcut.value();
                    if(bean.getClass().getName().equals(target.getName())){
                   Class[] interfaces = bean.getClass().getInterfaces();

                   if(interfaces.length<1){
                     bean =  cglibProxy(bean,advice);
                     return bean;
                   }
                   else{
                      bean =  jdkProxy(bean,advice,interfaces);
                      return bean;
                   }
                    }
                }
            }
        }
        return bean;
    }
    private Object jdkProxy(Object bean, Object advice, Class[] interfaces) {
        JdkProxyInvocationHandler handler = new JdkProxyInvocationHandler(bean,advice);

        try {
//            System.out.println(1);
//            Test t= new Test();
            bean = Proxy.newProxyInstance(this.getClass().getClassLoader(),interfaces,handler);
//           bean=Proxy.newProxyInstance(this.getClass().getClassLoader(),bean.getClass().getInterfaces(),new handleTest(t));
//            System.out.println(2);
        }catch (Exception e){
            System.out.println(e);
            System.out.println("234");
        }

//        Proxy.newProxyInstance(this.getClass().getClassLoader(),interfaces,handler);
   return bean;
    }
    private Object cglibProxy(Object bean, Object advice) {
        Enhancer enhancer = new Enhancer();

        enhancer.setSuperclass(bean.getClass());
        enhancer.setCallback(new cglibProxy(bean,advice));
        bean = enhancer.create();
        return bean;
    }
}
