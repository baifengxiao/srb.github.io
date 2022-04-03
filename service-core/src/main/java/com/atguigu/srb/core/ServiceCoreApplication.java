package com.atguigu.srb.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author: baifengxiao
 * @date: 2022/3/30 16:37
 */
@SpringBootApplication
@ComponentScan({"com.atguigu.srb","com.atguigu.common"})
public class ServiceCoreApplication {

    public static void main(String[] args) {

        SpringApplication.run(ServiceCoreApplication.class, args);
    }
}
