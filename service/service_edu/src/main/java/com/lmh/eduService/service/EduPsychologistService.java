package com.lmh.eduService.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lmh.eduService.entity.EduPsychologist;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author lmh
 * @since 2020-12-30
 */
public interface EduPsychologistService extends IService<EduPsychologist> {


    List<EduPsychologist> list2();


    Map<String, Object> getPsychologistFrontList(Page<EduPsychologist> psychologistPage);
}
