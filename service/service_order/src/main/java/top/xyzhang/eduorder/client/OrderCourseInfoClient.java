package top.xyzhang.eduorder.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import top.xyzhang.commonutils.R;
import top.xyzhang.eduorder.client.impl.OrderCourseInfoClientDegrade;

/**
 * 远程调用获取课程信息
 */
@FeignClient(name = "service-edu",fallback = OrderCourseInfoClientDegrade.class)
@Component
public interface OrderCourseInfoClient {

    /**
     * 【订单模块】- 根据课程id查询课程信息
     * 跨模块调用需要用R对象+字符串传递
     * @PathVariable 后必须加参数
     */
    @PostMapping("/eduservice/coursefront/getCourseInfoOrder/{id}")
    public R getCourseInfoOrder(@PathVariable("id") String id);
}
