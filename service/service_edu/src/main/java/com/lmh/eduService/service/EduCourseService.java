package com.lmh.eduService.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lmh.eduService.entity.EduCourse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lmh.eduService.entity.frontvo.CourseFrontVo;
import com.lmh.eduService.entity.frontvo.CourseWebVo;
import com.lmh.eduService.entity.vo.CourseInfoVo;
import com.lmh.eduService.entity.vo.CoursePublishVo;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author lmh
 * @since 2021-01-26
 */
public interface EduCourseService extends IService<EduCourse> {

    String addCourseInfo(CourseInfoVo courseInfoVo);

    CourseInfoVo getCourseInfo(String id);

    void updateCourseInfo(CourseInfoVo courseInfoVo);

    CoursePublishVo getCoursePublishInfo(String id);

    void deleteCourse(String id);

    List<EduCourse> list1();

    Map<String, Object> getCourseFrontList(Page<EduCourse> pageCourse, CourseFrontVo courseFrontVo);

    CourseWebVo getBaseCourseInfo(String courseId);

    void updateBuyCountById(String id);
}
