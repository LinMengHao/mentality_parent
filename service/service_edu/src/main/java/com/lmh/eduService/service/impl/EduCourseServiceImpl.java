package com.lmh.eduService.service.impl;

import com.lmh.base.handler.LmhException;
import com.lmh.eduService.entity.EduCourse;
import com.lmh.eduService.entity.EduCourseDescription;
import com.lmh.eduService.entity.vo.CoursePublishVo;
import com.lmh.eduService.mapper.EduCourseMapper;
import com.lmh.eduService.service.EduCourseDescriptionService;
import com.lmh.eduService.service.EduCourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lmh.eduService.entity.vo.CourseInfoVo;
import com.lmh.utils.ResultCode;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author lmh
 * @since 2021-01-26
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {
    @Autowired
    private EduCourseDescriptionService courseDescriptionService;

    @Override
    public String addCourseInfo(CourseInfoVo courseInfoVo) {
        //添加课程基本信息
        EduCourse eduCourse=new EduCourse();
        BeanUtils.copyProperties(courseInfoVo,eduCourse);
        int insert = baseMapper.insert(eduCourse);
        if(insert<=0){
            throw new LmhException(ResultCode.COURSE_NOT_SAVE.getCode(),ResultCode.COURSE_NOT_SAVE.getMessage());
        }
        String id=eduCourse.getId();
        //添加课程描述
        EduCourseDescription eduCourseDescription=new EduCourseDescription();
        BeanUtils.copyProperties(courseInfoVo,eduCourseDescription);
        eduCourseDescription.setId(id);
        boolean save = courseDescriptionService.save(eduCourseDescription);
        if(!save){
            throw new LmhException(ResultCode.COURSE_NOT_SAVE.getCode(),ResultCode.COURSE_NOT_SAVE.getMessage());
        }
        return id;
    }

    @Override
    public CourseInfoVo getCourseInfo(String id) {
        CourseInfoVo courseInfoVo=new CourseInfoVo();
        EduCourse eduCourse = baseMapper.selectById(id);
        BeanUtils.copyProperties(eduCourse,courseInfoVo);
        EduCourseDescription eduCourseDescription = courseDescriptionService.getById(id);
        courseInfoVo.setDescription(eduCourseDescription.getDescription());
        return courseInfoVo;
    }

    @Override
    public void updateCourseInfo(CourseInfoVo courseInfoVo) {
        EduCourse eduCourse=new EduCourse();
        BeanUtils.copyProperties(courseInfoVo,eduCourse);
        int i = baseMapper.updateById(eduCourse);
        if(i<=0){
            throw new LmhException(ResultCode.COURSE_NOT_UPDATE.getCode(),ResultCode.COURSE_NOT_UPDATE.getMessage());
        }
        EduCourseDescription eduCourseDescription=new EduCourseDescription();
        BeanUtils.copyProperties(courseInfoVo,eduCourseDescription);
        boolean b = courseDescriptionService.updateById(eduCourseDescription);
        if(!b){
            throw new LmhException(ResultCode.COURSE_NOT_UPDATE.getCode(),ResultCode.COURSE_NOT_UPDATE.getMessage());
        }
    }

    @Override
    public CoursePublishVo getCoursePublishInfo(String id) {
        CoursePublishVo publishCourseInfo = baseMapper.getPublishCourseInfo(id);
        return publishCourseInfo;
    }
}
