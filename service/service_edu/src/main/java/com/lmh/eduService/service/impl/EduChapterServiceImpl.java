package com.lmh.eduService.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lmh.base.handler.LmhException;
import com.lmh.eduService.entity.EduChapter;
import com.lmh.eduService.entity.EduVideo;
import com.lmh.eduService.entity.chapter.ChapterVo;
import com.lmh.eduService.entity.chapter.VideoVo;
import com.lmh.eduService.mapper.EduChapterMapper;
import com.lmh.eduService.service.EduChapterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lmh.eduService.service.EduVideoService;
import com.lmh.utils.ResultCode;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {
    @Autowired
    private EduVideoService videoService;


    @Override
    public List<ChapterVo> getChapterVideoByCourseId(String courseId) {
        QueryWrapper<EduChapter>wrapper=new QueryWrapper<>();
        wrapper.eq("course_id",courseId);
        wrapper.orderByAsc("sort");
        List<EduChapter> eduChapters = baseMapper.selectList(wrapper);
        QueryWrapper<EduVideo>wrapper1=new QueryWrapper<>();
        wrapper1.eq("course_id",courseId);
        List<EduVideo> list = videoService.list(wrapper1);
        List<ChapterVo>chapterVoList=new ArrayList<>();
    for (EduChapter eduChapter : eduChapters) {
      //
        ChapterVo chapterVo=new ChapterVo();
        BeanUtils.copyProperties(eduChapter,chapterVo);
        List<VideoVo> videoVos=new ArrayList<>();
      for (EduVideo eduVideo : list) {
        //
          VideoVo videoVo=new VideoVo();
          if(eduVideo.getChapterId().equals(chapterVo.getId())){
              BeanUtils.copyProperties(eduVideo,videoVo);
              videoVos.add(videoVo);
          }
      }
      chapterVo.setChildren(videoVos);
      chapterVoList.add(chapterVo);
    }
        return chapterVoList;
    }

    @Override
    public boolean deleteChapter(String id) {
        QueryWrapper<EduVideo> wrapper=new QueryWrapper<>();
        wrapper.eq("chapter_id",id);
        int count = videoService.count(wrapper);
        if(count>0){
            throw new LmhException(ResultCode.CHAPTER_NOT_DELETE.getCode(),ResultCode.CHAPTER_NOT_DELETE.getMessage());
        }else {
            int i = baseMapper.deleteById(id);
            if(i>0){
                return true;
            }else {
                return false;
            }
        }
    }
}
