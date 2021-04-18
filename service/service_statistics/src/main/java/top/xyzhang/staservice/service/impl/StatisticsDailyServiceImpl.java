package top.xyzhang.staservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import top.xyzhang.commonutils.R;
import top.xyzhang.staservice.client.UcenterClient;
import top.xyzhang.staservice.entity.StatisticsDaily;
import top.xyzhang.staservice.mapper.StatisticsDailyMapper;
import top.xyzhang.staservice.service.StatisticsDailyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务实现类
 * </p>
 *
 * @author xyzhang
 * @since 2021-04-17
 */
@Service
public class StatisticsDailyServiceImpl extends ServiceImpl<StatisticsDailyMapper, StatisticsDaily> implements StatisticsDailyService {

    @Autowired
    private UcenterClient ucenterClient;

    /**
     * 统计某日期注册人数
     * @param day
     */
    @Override
    public void registerCount(String day) {
        // 添加之前查找是否存在相同日期的数据
        QueryWrapper<StatisticsDaily> wrapper = new QueryWrapper<>();
        wrapper.eq("date_calculated",day);
        baseMapper.delete(wrapper);

        // 远程调用获取数据
        R countRegister = ucenterClient.countRegister(day);
        Integer num = (Integer) countRegister.getData().get("num");

        // 将数据存储到数据库中
        StatisticsDaily sta = new StatisticsDaily();
        sta.setRegisterNum(num); // 注册人数
        sta.setDateCalculated(day); // 统计日期

        sta.setVideoViewNum(RandomUtils.nextInt(0,100));
        sta.setLoginNum(RandomUtils.nextInt(0,100));
        sta.setCourseNum(RandomUtils.nextInt(0,100));
        baseMapper.insert(sta);
    }

    /**
     * 图表显示
     * @param type
     * @param begin
     * @param end
     * @return
     */
    @Override
    public Map<String, Object> getShowData(String type, String begin, String end) {
        QueryWrapper<StatisticsDaily> wrapper = new QueryWrapper<>();
        wrapper.between("date_calculated",begin, end);
        wrapper.select("date_calculated", type);
        List<StatisticsDaily> list = baseMapper.selectList(wrapper);

        // 日期list
        List<String> date_calculatedList = new ArrayList<>();
        // 数量list
        List<Integer> numDataList = new ArrayList<>();

        // 遍历全部数据的list集合 分别进行封装
        for (int i = 0; i < list.size(); i++) {
            StatisticsDaily daily = list.get(i);
            // 封装日期集合
            date_calculatedList.add(daily.getDateCalculated());
            // 封装对应数量
            switch (type) {
                case "login_num":
                    numDataList.add(daily.getLoginNum());
                    break;
                case "register_num":
                    numDataList.add(daily.getRegisterNum());
                    break;
                case "video_view_num":
                    numDataList.add(daily.getVideoViewNum());
                    break;
                case "course_num":
                    numDataList.add(daily.getCourseNum());
                    break;
                default:
                    numDataList.add(0);
                    break;
            }
        }
        // 统一返回 前端要求返回日期和数量
        Map<String, Object> map = new HashMap<>();
        map.put("date_calculatedList", date_calculatedList);
        map.put("numDataList", numDataList);
        return map;
    }
}
