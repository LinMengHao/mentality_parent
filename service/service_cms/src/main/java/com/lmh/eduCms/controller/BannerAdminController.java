package com.lmh.eduCms.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lmh.eduCms.entity.CmsBanner;
import com.lmh.eduCms.service.CmsBannerService;
import com.lmh.utils.R;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 首页banner表 前端控制器,后台管理员调整轮播图
 * </p>
 *
 * @author testjava
 * @since 2020-09-07
 */
@RestController
@RequestMapping("/cms/bannerAdmin")
//@CrossOrigin
public class BannerAdminController {
    @Autowired
    private CmsBannerService bannerAdminService;
    //分页查询
    @ApiOperation("banner分页查找")
    @GetMapping("pageBanner/{page}/{limit}")
    public R pageBanner(@PathVariable long page, @PathVariable long limit){
        Page<CmsBanner> pageBanner=new Page<>(page,limit);
        bannerAdminService.page(pageBanner,null);
        return R.ok().data("items",pageBanner.getRecords()).data("total",pageBanner.getTotal());
    }
    //添加banner
    @ApiOperation("添加banner")
    @PostMapping("addBanner")
    public R addBanner(@RequestBody CmsBanner banner){
        bannerAdminService.save(banner);
        return R.ok();
    }
    //修改
    @ApiOperation("修改banner")
    @PostMapping("update")
    public R updateById(@RequestBody CmsBanner banner){
        bannerAdminService.updateById(banner);
        return R.ok();
    }
    //根据id查询
    @ApiOperation("根据id返回banner信息")
    @GetMapping("get/{id}")
    public R get(@PathVariable String id){
        CmsBanner banner = bannerAdminService.getById(id);
        return R.ok().data("item",banner);
    }
    //删除
    @ApiOperation("删除banner")
    @DeleteMapping("delete/{id}")
    public R delete(@PathVariable String id){
        bannerAdminService.removeById(id);
        return R.ok();
    }
}

