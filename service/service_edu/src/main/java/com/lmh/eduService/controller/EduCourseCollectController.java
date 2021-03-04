package com.lmh.eduService.controller;


import com.lmh.eduService.entity.vo.CourseCollectVo;
import com.lmh.eduService.service.EduCourseCollectService;
import com.lmh.utils.JwtUtils;
import com.lmh.utils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 * 课程收藏 前端控制器
 * </p>
 *
 * @author lmh
 * @since 2021-03-04
 */
@Api(tags = "课程收藏")
@CrossOrigin
@RestController
@RequestMapping("/eduService/edu-course-collect")
public class EduCourseCollectController {
@Autowired
    private EduCourseCollectService collectService;
    @ApiOperation("添加一条课程收藏")
    @PostMapping("addCourseCollect/{courseId}")
    public R addCourseCollect(@PathVariable String courseId, HttpServletRequest request){
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        collectService.addCourseCollect(courseId,memberId);
        return R.ok();
    }
    @ApiOperation("判断是否收藏了")
    @GetMapping("isCollect/{courseId}")
    public R isCollect(@PathVariable String courseId,HttpServletRequest request){
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        Boolean isCollect=collectService.isCollect(courseId,memberId);
        return R.ok().data("isCollect",isCollect);
    }
    @ApiOperation("取消收藏课程")
    @DeleteMapping("removeCollect/{courseId}")
    public R removeCollect(@PathVariable String courseId,HttpServletRequest request){
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        Boolean remove=collectService.removeCollect(courseId,memberId);
        if(remove){
            return R.ok().message("已取消收藏");
        }else {
            return R.error().message("取消收藏失败");
        }
    }
    @ApiOperation(value = "获取课程收藏列表")
    @GetMapping("courseCollectList")
    public R showCourseCollect(HttpServletRequest request) {
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        List<CourseCollectVo> list = collectService.selectListByMemberId(memberId);
        return R.ok().data("items", list);
    }
}

