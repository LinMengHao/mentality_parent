package com.lmh.eduService.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lmh.eduService.entity.EduCourse;
import com.lmh.eduService.entity.EduPsychologist;
import com.lmh.eduService.service.EduCourseService;
import com.lmh.eduService.service.EduPsychologistService;
import com.lmh.utils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Api(tags = "前台讲师列表展示")
//@CrossOrigin
@RestController
@RequestMapping("eduService/psychologistFront")
public class PsychologistFrontController {
    @Autowired
    private EduPsychologistService psychologistService;
    @Autowired
    private EduCourseService courseService;

    @ApiOperation("分页查询心理讲师的方法")
    @PostMapping("getPsychologistFrontList/{page}/{limit}")
    public R getPsychologistFrontList(@PathVariable long page,@PathVariable long limit){
        Page<EduPsychologist> psychologistPage=new Page<>(page,limit);
        Map<String,Object> map=psychologistService.getPsychologistFrontList(psychologistPage);
        return R.ok().data(map);
    }
    @ApiOperation("查询心理讲师详细信息")
    @GetMapping("getPsychologistInfo/{psychologistId}")
    public R getPsychologistInfo(@PathVariable String psychologistId){
        EduPsychologist psychologist = psychologistService.getById(psychologistId);
        QueryWrapper<EduCourse> wrapper=new QueryWrapper<>();
        wrapper.eq("psychologist_id",psychologistId);
        List<EduCourse> eduCourseList = courseService.list(wrapper);
        return R.ok().data("teacher",psychologist).data("courseList",eduCourseList);
    }
}
