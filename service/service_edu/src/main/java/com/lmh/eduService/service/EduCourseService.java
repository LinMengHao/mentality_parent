package com.lmh.eduService.service;

import com.lmh.eduService.entity.EduCourse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lmh.eduService.entity.vo.CourseInfoVo;
import com.lmh.eduService.entity.vo.CoursePublishVo;

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
}
