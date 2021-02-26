package com.lmh.eduService.controller.front;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.lmh.eduService.entity.EduCourse;
import com.lmh.eduService.entity.chapter.ChapterVo;
import com.lmh.eduService.entity.frontvo.CourseFrontVo;
import com.lmh.eduService.entity.frontvo.CourseWebVo;
import com.lmh.eduService.service.EduChapterService;
import com.lmh.eduService.service.EduCourseService;
import com.lmh.utils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Api(tags="前台课程页功能")
@RestController
@RequestMapping("/eduService/courseFront")
@CrossOrigin
public class CourseFrontController {

    @Autowired
    private EduCourseService courseService;

    @Autowired
    private EduChapterService chapterService;



    //1 条件查询带分页查询课程
    @ApiOperation(value = "条件查询带分页查询课程")
    @PostMapping("getFrontCourseList/{page}/{limit}")
    public R getFrontCourseList(@PathVariable long page, @PathVariable long limit,
                                @RequestBody(required = false) CourseFrontVo courseFrontVo) {
        Page<EduCourse> pageCourse = new Page<>(page,limit);
        Map<String,Object> map = courseService.getCourseFrontList(pageCourse,courseFrontVo);
        //返回分页所有数据
        return R.ok().data(map);
    }

    //2 课程详情的方法
    @ApiOperation(value = " 课程详情的方法")
    @GetMapping("getFrontCourseInfo/{courseId}")
    public R getFrontCourseInfo(@PathVariable String courseId, HttpServletRequest request) {
        //根据课程id，编写sql语句查询课程信息
        CourseWebVo courseWebVo = courseService.getBaseCourseInfo(courseId);
        //根据课程id查询章节和小节
        List<ChapterVo> chapterVideoList = chapterService.getChapterVideoByCourseId(courseId);
        return R.ok().data("courseWebVo",courseWebVo).data("chapterVideoList",chapterVideoList);
    }
//
//    //根据课程id查询课程信息
//    @ApiOperation(value = " 根据课程id查询课程信息")
//    @PostMapping("getCourseInfoOrder/{id}")
//    public CourseWebVoOrder getCourseInfoOrder(@PathVariable String id) {
//        CourseWebVo courseInfo = courseService.getBaseCourseInfo(id);
//        CourseWebVoOrder courseWebVoOrder = new CourseWebVoOrder();
//        BeanUtils.copyProperties(courseInfo,courseWebVoOrder);
//        return courseWebVoOrder;
//    }
}











