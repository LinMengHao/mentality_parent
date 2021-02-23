package com.lmh.eduCms.service;

import com.lmh.eduCms.entity.CmsBanner;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务类
 * </p>
 *
 * @author lmh
 * @since 2021-02-18
 */
public interface CmsBannerService extends IService<CmsBanner> {

    List<CmsBanner> selectAllBanner();

}
