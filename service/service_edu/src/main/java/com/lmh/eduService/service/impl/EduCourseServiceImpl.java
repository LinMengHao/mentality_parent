package com.lmh.eduService.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lmh.base.handler.LmhException;
import com.lmh.eduService.client.VodClient;
import com.lmh.eduService.entity.EduChapter;
import com.lmh.eduService.entity.EduCourse;
import com.lmh.eduService.entity.EduCourseDescription;
import com.lmh.eduService.entity.EduVideo;
import com.lmh.eduService.entity.vo.CoursePublishVo;
import com.lmh.eduService.mapper.EduCourseMapper;
import com.lmh.eduService.service.EduChapterService;
import com.lmh.eduService.service.EduCourseDescriptionService;
import com.lmh.eduService.service.EduCourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lmh.eduService.entity.vo.CourseInfoVo;
import com.lmh.eduService.service.EduVideoService;
import com.lmh.utils.ResultCode;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

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
    @Autowired
    private EduVideoService videoService;
    @Autowired
    private EduChapterService chapterService;
    @Autowired
    private VodClient vodClient;
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

    @Override
    public void deleteCourse(String id) {
        //删除小节里上传到阿里云的视频
        //先删除小节
        QueryWrapper<EduVideo> wrapper =new QueryWrapper<>();
        wrapper.eq("course_id",id);
        List<EduVideo> eduVideoList = videoService.list(wrapper);
        List<String> videoIdList=new ArrayList<>();
        for (EduVideo eduVideo : eduVideoList) {
            String videoId=eduVideo.getVideoSourceId();
            if(!StringUtils.isEmpty(videoId)){
                videoIdList.add(videoId);
            }
        }
        if(videoIdList.size()>0) {
            vodClient.deleteAlyVideoList(videoIdList);
        }
        boolean remove = videoService.remove(wrapper);

        //再删除章节
        QueryWrapper<EduChapter> wrapper1 =new QueryWrapper<>();
        wrapper1.eq("course_id",id);
        boolean remove1 = chapterService.remove(wrapper1);

        //删除课程描述
        boolean b = courseDescriptionService.removeById(id);

        //最后删课程
        int i = baseMapper.deleteById(id);
        if(i==0){
            throw new LmhException(ResultCode.COURSE_NOT_DELETE.getCode(),ResultCode.COURSE_NOT_DELETE.getMessage());
        }
    }
    @Cacheable(key = "'courseList'",value = "course")
    @Override
    public List<EduCourse> list1() {
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        wrapper.last("limit 8");
        List<EduCourse> eduCourses = baseMapper.selectList(wrapper);
        return eduCourses;
    }
}
