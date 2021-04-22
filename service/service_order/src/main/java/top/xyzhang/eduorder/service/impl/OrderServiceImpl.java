package top.xyzhang.eduorder.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import top.xyzhang.commonutils.R;
import top.xyzhang.eduorder.client.OrderCourseInfoClient;
import top.xyzhang.eduorder.client.OrderUserInfoClient;
import top.xyzhang.eduorder.entity.Order;
import top.xyzhang.eduorder.mapper.OrderMapper;
import top.xyzhang.eduorder.service.OrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import top.xyzhang.eduorder.utils.OrderNoUtil;

import java.math.BigDecimal;
import java.util.Map;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author xyzhang
 * @since 2021-04-17
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Autowired
    private OrderCourseInfoClient orderCourseInfoClient;

    @Autowired
    private OrderUserInfoClient orderUserInfoClient;

    /**
     * 生成订单
     * @param courseId
     * @param memberIdByJwtToken
     * @return
     */
    @Override
    public String createOrder(String courseId, String memberIdByJwtToken) {
        //1 远程调用获取用户信息
        R userInfo = orderUserInfoClient.getUserInfo(memberIdByJwtToken);
        //2 远程调用获取课程信息
        R courseInfo = orderCourseInfoClient.getCourseInfoOrder(courseId);

        Map<String, Object> userInfoData = userInfo.getData();
        Map<String, Object> courseInfoData = courseInfo.getData();

        Order order = new Order();
        order.setOrderNo(OrderNoUtil.getOrderNo());
        order.setCourseId(courseId);
        order.setCourseTitle((String) courseInfoData.get("title"));
        order.setCourseCover((String) courseInfoData.get("cover"));
        order.setTeacherName((String) courseInfoData.get("teacherName"));
        order.setTotalFee(BigDecimal.valueOf((double) courseInfoData.get("price")));

        order.setMemberId((String) userInfoData.get("id"));
        order.setNickname((String) userInfoData.get("nickname"));
        order.setMobile((String) userInfoData.get("mobile"));
        order.setPayType(1);
        order.setStatus(0);
        baseMapper.insert(order);

        return order.getOrderNo();
    }
}
