package com.atguigu.gulimall.gateway;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import com.baomidou.mybatisplus.autoconfigure.MybatisPlusAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 *    配置 nacos 注册地址
 *    配置 nacos 命令空间  就是项目包名称
 *    nacos 启动命令: startup.cmd -m standalone   单例启动
 *
 *    //因引入common 把数据源引入进去，因此需要把数据库进行排除
 */
@EnableDiscoveryClient
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class,DruidDataSourceAutoConfigure.class, MybatisPlusAutoConfiguration.class})
public class GulimallGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GulimallGatewayApplication.class, args);
    }

}
