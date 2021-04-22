package top.xyzhang.eduorder.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.xyzhang.commonutils.JwtUtils;
import top.xyzhang.commonutils.R;
import top.xyzhang.eduorder.entity.Order;
import top.xyzhang.eduorder.service.OrderService;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 订单 前端控制器
 * </p>
 *
 * @author xyzhang
 * @since 2021-04-17
 */
@RestController
@RequestMapping("/eduorder/order")
@CrossOrigin
public class OrderController {
    @Autowired
    private OrderService orderService;

    /**
     * 订单全流程：
     * 1. 用户点击购买课程创建订单 向数据库订单表中插入一条数据 返回用户订单号
     * 2. 打开新页面 显示订单信息 点击去支付生成支付二维码
     * 3. 支付后向支付日志表中添加支付记录
     *
     *
     * 创建订单
     */
    @PostMapping("createOrder/{courseId}")
    public R createOrder(@PathVariable String courseId, HttpServletRequest request) {
        System.out.println("=====================courseId"+courseId);

        // 创建订单 返回订单号
        String orderId = orderService.createOrder(courseId, JwtUtils.getMemberIdByJwtToken(request));
        System.out.println("=====================orderId"+orderId);
        return R.ok().data("orderId", orderId);
    }

    /**
     * 根据订单id查询订单信息
     */
    @GetMapping("getOrderInfo/{orderNo}")
    public R getOrderInfo(@PathVariable String orderNo) {
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("order_no", orderNo);
        Order order = orderService.getOne(wrapper);
        return R.ok().data("item", order);
    }
}

