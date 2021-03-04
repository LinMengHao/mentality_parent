package com.lmh.statistics.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lmh.statistics.client.UcenterClient;
import com.lmh.statistics.entity.Daily;
import com.lmh.statistics.mapper.DailyMapper;
import com.lmh.statistics.service.DailyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lmh.utils.R;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 网站统计日数据 服务实现类
 * </p>
 *
 * @author lmh
 * @since 2021-03-04
 */
@Service
public class DailyServiceImpl extends ServiceImpl<DailyMapper, Daily> implements DailyService {
    @Autowired
    private UcenterClient ucenterClient;
    @Override
    public void registerCount(String day) {
        //相同日期时  先删除旧数据  再添加
        QueryWrapper<Daily> wrapper = new QueryWrapper<>();
        wrapper.eq("date_calculated",day);
        baseMapper.delete(wrapper);
        //远程调用得到某一天的注册人数
        R register = ucenterClient.countRegister(day);
        Integer countRegister = (Integer)register.getData().get("countRegister");

        //把获取到的数据添加到数据库 统计分析表内
        Daily statisticsDaily = new Daily();
        statisticsDaily.setRegisterNum(countRegister); //注册人数
        statisticsDaily.setDateCalculated(day); //统计日期
        statisticsDaily.setVideoViewNum(RandomUtils.nextInt(100,200));
        statisticsDaily.setLoginNum(RandomUtils.nextInt(100,200));
        statisticsDaily.setCourseNum(RandomUtils.nextInt(100,200));
        baseMapper.insert(statisticsDaily);
    }
}
