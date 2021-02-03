package com.lmh.eduService.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lmh.eduService.entity.EduCourse;
import com.lmh.eduService.entity.vo.CoursePublishVo;
import com.lmh.eduService.service.EduCourseService;
import com.lmh.eduService.entity.vo.CourseInfoVo;
import com.lmh.eduService.entity.vo.CourseQuery;
import com.lmh.utils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author lmh
 * @since 2021-01-26
 */
@Api(tags = "课程")
@CrossOrigin
@RestController
@RequestMapping("/eduService/edu-course")
public class EduCourseController {
@Autowired
    private EduCourseService courseService;

    @ApiOperation("添加课程基本信息")
    @PostMapping("addCourseInfo")
    public R addCourseInfo(@RequestBody CourseInfoVo courseInfoVo){
        String id=courseService.addCourseInfo(courseInfoVo);
        return R.ok().data("courseId",id);
    }
    @ApiOperation("根据ID查询课程基本信息")
    @GetMapping("getCourseInfo/{id}")
    public R getCourseInfo(@PathVariable(name = "id") String id){
        CourseInfoVo courseInfoVo=courseService.getCourseInfo(id);
        return R.ok().data("courseInfoVo",courseInfoVo);
    }
    @ApiOperation("修改课程信息")
    @PostMapping("updateCourseInfo")
    public R updateCourseInfo(@RequestBody CourseInfoVo courseInfoVo){
        courseService.updateCourseInfo(courseInfoVo);
        return R.ok();
    }
    @ApiOperation("发布课程")
    @PostMapping("publishCourse/{id}")
    public R publishCourse(@PathVariable String id) {
        EduCourse eduCourse = new EduCourse();
        eduCourse.setId(id);
        eduCourse.setStatus("Normal");
        courseService.updateById(eduCourse);
        return R.ok();
    }
    @ApiOperation("课程列表查询")
    @PostMapping("findAll/{current}/{limit}")
    public R findAll(@PathVariable(name = "current")long current,
                     @PathVariable(name = "limit")long limit,
                     @RequestBody(required = false)CourseQuery courseQuery){
        String status = courseQuery.getStatus();
        String title = courseQuery.getTitle();
        QueryWrapper<EduCourse>wrapper=new QueryWrapper<>();
        if(!StringUtils.isEmpty(status)){
            wrapper.eq("status",status);
        }
        if(!StringUtils.isEmpty(title)){
            wrapper.like("title",title);
        }
        wrapper.orderByDesc("gmt_create");
        Page<EduCourse> page=new Page<>(current,limit);
        courseService.page(page,wrapper);
        List<EduCourse> records = page.getRecords();
        long total = page.getTotal();
        return R.ok().data("total",total).data("records",records);
    }
    @ApiOperation("获取课程整体信息")
    @GetMapping("getCoursePublishInfo/{id}")
    public R getCoursePublishInfo(@PathVariable String id){
        CoursePublishVo coursePublishVo=courseService.getCoursePublishInfo(id);
        return R.ok().data("publishCourse",coursePublishVo);
    }
}

