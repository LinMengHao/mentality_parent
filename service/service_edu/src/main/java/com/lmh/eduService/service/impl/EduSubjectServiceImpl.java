package com.lmh.eduService.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lmh.eduService.entity.EduSubject;
import com.lmh.eduService.entity.excel.SubjectData;
import com.lmh.eduService.entity.subject.OneSubject;
import com.lmh.eduService.entity.subject.TwoSubject;
import com.lmh.eduService.listener.SubjectExcelListener;
import com.lmh.eduService.mapper.EduSubjectMapper;
import com.lmh.eduService.service.EduSubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author lmh
 * @since 2021-01-25
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {

    //添加课程分类
    @Override
    public void saveSubject(MultipartFile file, EduSubjectService subjectService) {
        try{
            InputStream inputStream = file.getInputStream();
            EasyExcel.read(inputStream, SubjectData.class,new SubjectExcelListener(subjectService)).sheet().doRead();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public List<OneSubject> getAllSubject() {
        //一级
        QueryWrapper<EduSubject> wrapper=new QueryWrapper<>();
        wrapper.eq("parent_id","0");
        List<EduSubject> oneSubject = baseMapper.selectList(wrapper);
        List<OneSubject> list=new ArrayList<>();
        //二级
        QueryWrapper<EduSubject> wrapper1=new QueryWrapper<>();
        wrapper1.ne("parent_id","0");
        List<EduSubject> twoSubject = baseMapper.selectList(wrapper1);
    for (EduSubject eduSubject : oneSubject) {
        OneSubject oneSubject1=new OneSubject();
        BeanUtils.copyProperties(eduSubject,oneSubject1);
        List<TwoSubject> list1=new ArrayList<>();
      for (EduSubject subject : twoSubject) {
          if(eduSubject.getId().equals(subject.getParentId())){
              TwoSubject twoSubject1=new TwoSubject();
              BeanUtils.copyProperties(subject,twoSubject1);
              list1.add(twoSubject1);
          }
      }
      oneSubject1.setChildren(list1);
      list.add(oneSubject1);
    }
        return list;
    }
}
