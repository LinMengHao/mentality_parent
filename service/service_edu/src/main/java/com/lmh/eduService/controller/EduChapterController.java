package com.lmh.eduService.controller;


import com.lmh.eduService.entity.EduChapter;
import com.lmh.eduService.entity.chapter.ChapterVo;
import com.lmh.eduService.service.EduChapterService;
import com.lmh.utils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author lmh
 * @since 2021-01-26
 */
@Api(tags = "课程章节")
//@CrossOrigin
@RestController
@RequestMapping("/eduService/edu-chapter")
public class EduChapterController {
    @Autowired
    private EduChapterService chapterService;

    @ApiOperation("根据课程id查询章节")
    @GetMapping("getChapterVideo/{courseId}")
    public R getChapterVideo(@PathVariable(name = "courseId")String courseId){
        List<ChapterVo> list=chapterService.getChapterVideoByCourseId(courseId);
        return R.ok().data("allChapterVideo",list);
    }
    @ApiOperation("添加章节")
    @PostMapping("addChapter")
    public R addChapter(@RequestBody EduChapter eduChapter){
        chapterService.save(eduChapter);
        return R.ok();
    }
    @ApiOperation("根据id查询章节")
    @GetMapping("getChapterById/{id}")
    public R getChapterById(@PathVariable String id){
        EduChapter eduChapter = chapterService.getById(id);
        return R.ok().data("chapter",eduChapter);
    }
    @ApiOperation("修改章节")
    @PostMapping("updateChapter")
    public R updateChapter(@RequestBody EduChapter eduChapter){
        chapterService.updateById(eduChapter);
        return R.ok();
    }
    //有小节的章节不可以删除
    @ApiOperation("根据ID删除章节")
    @DeleteMapping("deleteChapter/{id}")
    public R deleteChapter(@PathVariable String id){
        boolean flag=chapterService.deleteChapter(id);
        if(flag){
            return R.ok();
        }else {
            return R.error();
        }
    }


}

