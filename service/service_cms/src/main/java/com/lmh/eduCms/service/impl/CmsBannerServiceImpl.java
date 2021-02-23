package com.lmh.eduCms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lmh.eduCms.entity.CmsBanner;
import com.lmh.eduCms.mapper.CmsBannerMapper;
import com.lmh.eduCms.service.CmsBannerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务实现类
 * </p>
 *
 * @author lmh
 * @since 2021-02-18
 */
@Service
public class CmsBannerServiceImpl extends ServiceImpl<CmsBannerMapper, CmsBanner> implements CmsBannerService {

    @Override
    public List<CmsBanner> selectAllBanner() {
        // 根据id进行降序，并显示排列之后的几条
        QueryWrapper<CmsBanner> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        // last拼接sql
        wrapper.last("limit 5");
        List<CmsBanner> list = baseMapper.selectList(wrapper);
        return list;
    }
}
