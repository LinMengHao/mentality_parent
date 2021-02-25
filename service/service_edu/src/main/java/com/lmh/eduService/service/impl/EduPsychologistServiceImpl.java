package com.lmh.eduService.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lmh.eduService.entity.EduPsychologist;
import com.lmh.eduService.mapper.EduPsychologistMapper;
import com.lmh.eduService.service.EduPsychologistService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 讲师 服务实现类
 * </p>
 *
 * @author lmh
 * @since 2020-12-30
 */
@Service
public class EduPsychologistServiceImpl extends ServiceImpl<EduPsychologistMapper, EduPsychologist> implements EduPsychologistService {
    @Cacheable(key = "'teacherList'", value = "teacher")
    @Override
    public List<EduPsychologist> list2() {
        QueryWrapper<EduPsychologist> wrapper1 = new QueryWrapper<>();
        wrapper1.orderByDesc("id");
        wrapper1.last("limit 4");
        List<EduPsychologist> EduPsychologist = baseMapper.selectList(wrapper1);
        return EduPsychologist;
    }
}
