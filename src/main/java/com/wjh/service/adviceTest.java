package com.wjh.service;

import com.wjh.spring.annotation.HAdvice;
import com.wjh.spring.annotation.HPointcut;

@HAdvice
public class adviceTest {
    @HPointcut(adminService.class)
    public void point(){}
    public void before(){
        System.out.println("before");
    }
    public void after(){
        System.out.println("after");
    }
}
