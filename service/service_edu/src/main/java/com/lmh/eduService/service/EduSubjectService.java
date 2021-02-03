package com.lmh.eduService.service;

import com.lmh.eduService.entity.EduSubject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lmh.eduService.entity.subject.OneSubject;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author lmh
 * @since 2021-01-25
 */
public interface EduSubjectService extends IService<EduSubject> {

    void saveSubject(MultipartFile file, EduSubjectService subjectService);

    List<OneSubject> getAllSubject();
}
