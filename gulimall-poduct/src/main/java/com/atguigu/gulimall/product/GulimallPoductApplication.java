package com.atguigu.gulimall.product;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 1.整合 mybatis-plus
 */
@MapperScan("com.atguigu.gulimall.product.dao")
@EnableDiscoveryClient
@SpringBootApplication
@EnableFeignClients("com.atguigu.gulimall.product.feign")
public class GulimallPoductApplication {

    public static void main(String[] args) {
        SpringApplication.run(GulimallPoductApplication.class, args);
    }

}
