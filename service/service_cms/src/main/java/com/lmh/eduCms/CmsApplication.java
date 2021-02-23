package com.lmh.eduCms;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"com.lmh"})
@MapperScan("com.lmh.eduCms.mapper")
public class CmsApplication {
  public static void main(String[] args) {
    //
      SpringApplication.run(CmsApplication.class,args);
  }
}
