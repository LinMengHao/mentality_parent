package com.lmh.eduService.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lmh.eduService.controller.EduCourseController;
import com.lmh.eduService.entity.EduCourseCollect;
import com.lmh.eduService.entity.vo.CourseCollectVo;
import com.lmh.eduService.mapper.EduCourseCollectMapper;
import com.lmh.eduService.service.EduCourseCollectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 课程收藏 服务实现类
 * </p>
 *
 * @author lmh
 * @since 2021-03-04
 */
@Service
public class EduCourseCollectServiceImpl extends ServiceImpl<EduCourseCollectMapper, EduCourseCollect> implements EduCourseCollectService {

    @Override
    public void addCourseCollect(String courseId, String memberId) {
        //未收藏则收藏
        if(!this.isCollect(courseId, memberId)) {
            EduCourseCollect courseCollect = new EduCourseCollect();
            courseCollect.setCourseId(courseId);
            courseCollect.setMemberId(memberId);
            this.save(courseCollect);
        }
    }

    @Override
    public Boolean isCollect(String courseId, String memberId) {
        QueryWrapper<EduCourseCollect>wrapper=new QueryWrapper<>();
        wrapper.eq("member_id",memberId)
                .eq("course_id",courseId);
        Integer integer = baseMapper.selectCount(wrapper);
        return integer>0;
    }

    @Override
    public Boolean removeCollect(String courseId, String memberId) {
        if(this.isCollect(courseId, memberId)){
        QueryWrapper<EduCourseCollect> wrapper=new QueryWrapper<>();
        wrapper.eq("course_id",courseId)
                .eq("member_id",memberId);
        return this.remove(wrapper);
        }
        return false;
    }

    @Override
    public List<CourseCollectVo> selectListByMemberId(String memberId) {
        return baseMapper.selectPageByMemberId(memberId);
    }
}
