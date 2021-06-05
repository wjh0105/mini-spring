package com.wjh.service;

import com.wjh.spring.annotation.HAutowired;
import com.wjh.spring.annotation.HComponent;
import com.wjh.spring.annotation.HService;

@HComponent
public class adminService implements IAdmin{

    @Override
    public void say() {
        System.out.println("admin");
    }
}
