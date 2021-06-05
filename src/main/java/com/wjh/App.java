package com.wjh;
import com.wjh.service.IAdmin;
import com.wjh.service.adminService;
import com.wjh.service.userService;
import com.wjh.spring.config.IocConfig;
import com.wjh.spring.ioc.ApplicationContext;
public class App {
    public static void main(String[] args) throws Exception {
        ApplicationContext applicationContext = new ApplicationContext(IocConfig.class);
        IAdmin userService = (IAdmin) applicationContext.getBean("adminService");
        userService.say();
    }
}
