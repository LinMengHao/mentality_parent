package com.lmh.ucenter.controller;


import com.lmh.orderVo.UcenterMemberOrder;
import com.lmh.ucenter.entity.Member;
import com.lmh.ucenter.entity.vo.RegisterVo;
import com.lmh.ucenter.service.MemberService;
import com.lmh.utils.JwtUtils;
import com.lmh.utils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author lmh
 * @since 2021-02-23
 */
@Api(tags = "用户登录注册")
//@CrossOrigin
@RestController
@RequestMapping("/ucenter/member")
public class MemberController {
    @Autowired
    private MemberService memberService;
    //登录
    @ApiOperation("用户登录")
    @PostMapping("login")
    public R login(@RequestBody Member member){
        //返回token值
        String token=memberService.login(member);
        return R.ok().data("token",token);
    }
    //注册
    @ApiOperation("用户注册")
    @PostMapping("register")
    public R register(@RequestBody RegisterVo registerVo){
        memberService.register(registerVo);
        return R.ok();
    }
    //根据token获取用户信息
    @ApiOperation("根据token获取用户信息")
    @GetMapping("getMemberInfo")
    public R getMemberInfo(HttpServletRequest request){
        //用jwt工具类的方法获取用户id
        String memberId= JwtUtils.getMemberIdByJwtToken(request);
    // 查询数据库，根据id获取用户信息
        Member member = memberService.getById(memberId);
        return R.ok().data("memberInfo",member);
    }
    //根据用户id获取用户信息
    @ApiOperation(value = "根据用户id获取用户信息 课程评论用")
    @PostMapping("getUserInfoOrder/{id}")
    public UcenterMemberOrder getUserInfoOrder(@PathVariable String id) {
        Member member = memberService.getById(id);
        UcenterMemberOrder ucenterMemberOrder=new UcenterMemberOrder();
        BeanUtils.copyProperties(member,ucenterMemberOrder);
        return ucenterMemberOrder;
    }
    @ApiOperation(value = "查询某一天注册人数")
    @GetMapping("countRegister/{day}")
    public R countRegister(@PathVariable String day){
        Integer count=memberService.countRegisterDay(day);
        return R.ok().data("countRegister",count);
    }
}

