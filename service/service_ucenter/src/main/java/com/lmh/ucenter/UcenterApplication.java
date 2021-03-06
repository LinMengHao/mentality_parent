package com.lmh.ucenter;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
@EnableFeignClients//服务调用
@EnableDiscoveryClient//nacos注册
@ComponentScan("com.lmh")
@SpringBootApplication
@MapperScan("com.lmh.ucenter.mapper")
public class UcenterApplication {
  public static void main(String[] args) {
    //
      SpringApplication.run(UcenterApplication.class);
  }
}
