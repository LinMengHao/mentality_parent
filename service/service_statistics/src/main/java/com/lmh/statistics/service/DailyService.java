package com.lmh.statistics.service;

import com.lmh.statistics.entity.Daily;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 网站统计日数据 服务类
 * </p>
 *
 * @author lmh
 * @since 2021-03-04
 */
public interface DailyService extends IService<Daily> {

    void registerCount(String day);
}
