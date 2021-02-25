package com.lmh.ucenter;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan("com.lmh")
@SpringBootApplication
@MapperScan("com.lmh.ucenter.mapper")
public class UcenterApplication {
  public static void main(String[] args) {
    //
      SpringApplication.run(UcenterApplication.class);
  }
}
