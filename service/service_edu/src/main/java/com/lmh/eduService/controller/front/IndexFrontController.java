package com.lmh.eduService.controller.front;

import com.lmh.eduService.entity.EduCourse;
import com.lmh.eduService.entity.EduPsychologist;
import com.lmh.eduService.service.EduCourseService;
import com.lmh.eduService.service.EduPsychologistService;
import com.lmh.utils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = "前端页面展示")
@CrossOrigin
@RequestMapping("/eduService/indexFront")
@RestController
public class IndexFrontController {
    @Autowired
    private EduCourseService courseService;
    @Autowired
    private EduPsychologistService psychologistService;
    @ApiOperation("查询前八条热门课程,前四名师")
    @GetMapping("index")
    public R index(){
        List<EduCourse> list = courseService.list1();

        List<EduPsychologist> list1 = psychologistService.list2();

        return R.ok().data("eduList",list).data("teacherList",list1);
    }
}
