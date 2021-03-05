package com.lmh.eduCms.controller;

import com.lmh.eduCms.entity.CmsBanner;
import com.lmh.eduCms.service.CmsBannerService;
import com.lmh.utils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@Api(tags = "前端banner展示")
@RestController
@RequestMapping("/cms/bannerFront")
//@CrossOrigin
public class BannerFrontController {
    @Autowired
    private CmsBannerService bannerFrontService;
    //查询所有banner

    @ApiOperation("前端banner展示")
    @GetMapping("getAllBanner")
    public R getAllBanner(){
        List<CmsBanner> list=bannerFrontService.selectAllBanner();
        return R.ok().data("list",list);
    }
}
