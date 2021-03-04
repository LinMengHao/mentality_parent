package com.lmh.statistics.controller;


import com.lmh.statistics.client.UcenterClient;
import com.lmh.statistics.service.DailyService;
import com.lmh.utils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 网站统计日数据 前端控制器
 * </p>
 *
 * @author lmh
 * @since 2021-03-04
 */
@Api(tags = "网站统计日数据")
@CrossOrigin
@RestController
@RequestMapping("/statistics/daily")
public class DailyController {
@Autowired
    private DailyService dailyService;
@Autowired
    private UcenterClient ucenterClient;
@ApiOperation(value = "统计某一天的注册人数生成统计数据")
    @PostMapping("registerCount/{day}")
    public R registerCount(@PathVariable String day){
    dailyService.registerCount(day);
    return R.ok();
}
}

