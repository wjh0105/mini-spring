package com.wjh.spring.config;

import com.wjh.spring.annotation.HComponentScan;
import com.wjh.spring.annotation.HEnableAspectJAutoProxy;
import com.wjh.spring.annotation.HImport;

@HComponentScan(basePackage = "com.wjh.service")
@HEnableAspectJAutoProxy
public class IocConfig {
}
