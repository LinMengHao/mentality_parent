package com.lmh.statistics;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableFeignClients//服务调用
@EnableDiscoveryClient//nacos注册
@SpringBootApplication
@EnableScheduling//开启定时任务
@MapperScan(basePackages = "com.lmh.statistics.mapper")
@ComponentScan(basePackages = "com.lmh")
public class DailyApplication {
  public static void main(String[] args) {
    //
      SpringApplication.run(DailyApplication.class,args);
  }
}
