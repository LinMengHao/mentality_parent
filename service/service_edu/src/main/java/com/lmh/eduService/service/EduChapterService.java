package com.lmh.eduService.service;

import com.lmh.eduService.entity.EduChapter;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lmh.eduService.entity.chapter.ChapterVo;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author lmh
 * @since 2021-01-26
 */
public interface EduChapterService extends IService<EduChapter> {

    List<ChapterVo> getChapterVideoByCourseId(String courseId);

    boolean deleteChapter(String id);
}
