package com.lmh.eduService.service;

import com.lmh.eduService.entity.EduPsychologist;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

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
}
