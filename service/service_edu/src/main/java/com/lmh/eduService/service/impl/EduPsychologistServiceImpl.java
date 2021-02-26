package com.lmh.eduService.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lmh.eduService.entity.EduPsychologist;
import com.lmh.eduService.mapper.EduPsychologistMapper;
import com.lmh.eduService.service.EduPsychologistService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Override
    public Map<String, Object> getPsychologistFrontList(Page<EduPsychologist> psychologistPage) {
        QueryWrapper<EduPsychologist> wrapper=new QueryWrapper<>();
        wrapper.orderByDesc("sort");
        baseMapper.selectPage(psychologistPage,wrapper);
        List<EduPsychologist> records = psychologistPage.getRecords();
        long current = psychologistPage.getCurrent();
        long pages = psychologistPage.getPages();
        long size = psychologistPage.getSize();
        long total = psychologistPage.getTotal();
        boolean hasNext = psychologistPage.hasNext();//下一页
        boolean hasPrevious = psychologistPage.hasPrevious();//上一页

        //把分页数据获取出来，放到map集合
        Map<String, Object> map = new HashMap<>();
        map.put("items", records);
        map.put("current", current);
        map.put("pages", pages);
        map.put("size", size);
        map.put("total", total);
        map.put("hasNext", hasNext);
        map.put("hasPrevious", hasPrevious);

        //map返回
        return map;
    }


}
