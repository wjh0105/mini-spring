package com.wjh.service;

import com.wjh.spring.annotation.HComponent;
import com.wjh.spring.aware.Aware;
//@HComponent
public class userAware implements Aware {
    public void say(){
        System.out.println("aware");
    }
}
