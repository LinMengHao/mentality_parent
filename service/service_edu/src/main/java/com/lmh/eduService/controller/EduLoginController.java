package com.lmh.eduService.controller;

import com.lmh.utils.R;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

@Api(tags = "临时登录")
@CrossOrigin
@RestController
@RequestMapping("/adminService/user")
public class EduLoginController {
    //login
    @PostMapping("login")
    public R login(){
        return R.ok().data("token","admin");
    }

    //info
    @GetMapping("info")
    public R info(){
        return R.ok().data("roles","[admin]")
                .data("name","张雨濛")
                .data("avatar","https://edu-manager-lmh.oss-cn-beijing.aliyuncs.com/2020/09/06/c1a80946c8c74932bbf2f54bdc8b3e04800041057.jpg");
    }
}
