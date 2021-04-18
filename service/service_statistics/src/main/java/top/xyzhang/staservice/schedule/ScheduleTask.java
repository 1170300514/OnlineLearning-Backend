package top.xyzhang.staservice.schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import top.xyzhang.staservice.service.StatisticsDailyService;
import top.xyzhang.staservice.utils.DateUtil;

import java.util.Date;

@Component
public class ScheduleTask {

    @Autowired
    private StatisticsDailyService dailyService;



    /**
     * 每隔5s执行方法 cron七子表达式
     */
//    @Scheduled(cron = "0/5 * * * * ?")
//    public void task1() {
//        System.out.println("***************task1执行了");
//    }

    /**
     * 每天凌晨一点
     * 查询前一天数据进行添加
     */
    @Scheduled(cron = "0 0 1 * * ? ")
    public void task2() {
        dailyService.registerCount(DateUtil.formatDate(DateUtil.addDays(new Date(), -1)));
    }
}
