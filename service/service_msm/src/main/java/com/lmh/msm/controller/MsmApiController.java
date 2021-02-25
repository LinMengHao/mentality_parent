package com.lmh.msm.controller;


import com.lmh.msm.service.MsmService;
import com.lmh.msm.utils.RandomUtil;
import com.lmh.utils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
@Api(tags = "阿里云短信服务")
@RestController
@CrossOrigin
@RequestMapping("/eduMsm/msm")
public class MsmApiController {
  @Autowired private MsmService msmService;
@Autowired
private RedisTemplate<String, String> redisTemplate;
  // 发送短信的方法
  @ApiOperation("发送短信的方法")
  @GetMapping("send/{phone}")
  public R sendMsm(@PathVariable String phone) {
    //1.从缓存里取验证码，如果有直接返回
    String code = redisTemplate.opsForValue().get(phone);
    if(!StringUtils.isEmpty(code)){
      return R.ok();
    }
    // 生成随机值，传递阿里云进行发送
    code = RandomUtil.getSixBitRandom();
    Map<String, Object> param = new HashMap<>();
    param.put("code", code);
    // 调用service发送短信的方法
    boolean isSend = msmService.send(param, phone);
    if(isSend) {
      //发送成功后把验证码放入redis
      //设置有效时间
      redisTemplate.opsForValue().set(phone,code,5, TimeUnit.MINUTES);
      return R.ok();
    } else {
      return R.error().message("短信发送失败");
    }
  }
}
