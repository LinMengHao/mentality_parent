package com.lmh.vod;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients//服务调用
@EnableDiscoveryClient//nacos注册
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class,scanBasePackages = {"com.lmh"})
public class VodApplication {
  public static void main(String[] args) {
    //
      SpringApplication.run(VodApplication.class,args);
  }
}
