package com.lmh.eduService.mapper;

import com.lmh.eduService.entity.EduCourseCollect;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lmh.eduService.entity.vo.CourseCollectVo;

import java.util.List;

/**
 * <p>
 * 课程收藏 Mapper 接口
 * </p>
 *
 * @author lmh
 * @since 2021-03-04
 */
public interface EduCourseCollectMapper extends BaseMapper<EduCourseCollect> {

    List<CourseCollectVo> selectPageByMemberId(String memberId);
}
