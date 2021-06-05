package com.wjh.spring.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD}) //当前注解运用在方法上
@Retention(RetentionPolicy.RUNTIME) //
public @interface HPointcut {
    Class value();
}
