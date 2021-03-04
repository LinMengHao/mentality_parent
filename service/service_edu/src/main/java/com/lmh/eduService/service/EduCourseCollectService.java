package com.lmh.eduService.service;

import com.lmh.eduService.entity.EduCourseCollect;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lmh.eduService.entity.vo.CourseCollectVo;

import java.util.List;

/**
 * <p>
 * 课程收藏 服务类
 * </p>
 *
 * @author lmh
 * @since 2021-03-04
 */
public interface EduCourseCollectService extends IService<EduCourseCollect> {

    void addCourseCollect(String courseId, String memberId);

    Boolean isCollect(String courseId, String memberId);

    Boolean removeCollect(String courseId, String memberId);

    List<CourseCollectVo> selectListByMemberId(String memberId);
}
