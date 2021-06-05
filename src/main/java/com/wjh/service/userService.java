package com.wjh.service;

import com.wjh.spring.annotation.HComponent;

@HComponent
public class userService implements IuserService{

    @Override
    public void say() {
        System.out.println("hello");
    }
}
