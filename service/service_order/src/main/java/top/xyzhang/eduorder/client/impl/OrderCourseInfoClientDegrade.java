package top.xyzhang.eduorder.client.impl;

import org.springframework.stereotype.Component;
import top.xyzhang.commonutils.R;
import top.xyzhang.eduorder.client.OrderCourseInfoClient;

@Component
public class OrderCourseInfoClientDegrade implements OrderCourseInfoClient {
    /**
     * 【订单模块】- 根据课程id查询课程信息
     * 跨模块调用需要用R对象+字符串传递
     *
     * @param id
     * @PathVariable 后必须加参数
     */
    @Override
    public R getCourseInfoOrder(String id) {
        return R.error().message("远程调用课程模块出错降级");
    }
}
