package com.lmh.eduService;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@MapperScan(basePackages = "com.lmh.eduService.mapper")
@ComponentScan(basePackages = "com.lmh")

public class EduApplication {
  public static void main(String[] args) {
    //
      SpringApplication.run(EduApplication.class,args);
  }
}
