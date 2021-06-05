package com.wjh.spring.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE}) //当前注解运用在此类上
@Retention(RetentionPolicy.RUNTIME) //生命周期是运行时
public @interface HService {
}
