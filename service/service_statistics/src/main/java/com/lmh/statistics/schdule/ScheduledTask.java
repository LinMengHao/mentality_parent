package com.lmh.statistics.schdule;

import com.lmh.statistics.service.DailyService;
;
import com.lmh.statistics.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class ScheduledTask {
    @Autowired
    private DailyService dailyService;

    //cron表达式  设置时间 spring写6位，不然会报错
    // 在线生成cron表达式  http://cron.qqe2.com/
    //在每天0点，把前一天数据进行数据查询添加
    @Scheduled(cron = "0 0 0 * * ? ")
    public void taskDaily() {
        dailyService.registerCount(DateUtil.formatDate(DateUtil.addDays(new Date(), -1)));
    }
}
