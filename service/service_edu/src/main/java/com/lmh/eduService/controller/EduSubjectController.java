package com.lmh.eduService.controller;


import com.lmh.eduService.entity.subject.OneSubject;
import com.lmh.eduService.service.EduSubjectService;
import com.lmh.utils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author lmh
 * @since 2021-01-25
 */
@Api(tags = "课程分类")
@CrossOrigin
@RestController
@RequestMapping("/eduService/edu-subject")
public class EduSubjectController {
@Autowired
    private EduSubjectService subjectService;

@ApiOperation("添加课程分类")
@PostMapping("addSubject")
    public R addSubject(MultipartFile file){
    subjectService.saveSubject(file,subjectService);
    return R.ok();
}
//返回数据是树形
@ApiOperation("课程分类列表")
    @GetMapping("getAllSubject")
    public R getAllSubject(){
    List<OneSubject> list=subjectService.getAllSubject();
    return R.ok().data("list",list);
}
}

