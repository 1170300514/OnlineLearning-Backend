package top.xyzhang.eduorder.service.impl;

import top.xyzhang.eduorder.entity.Order;
import top.xyzhang.eduorder.mapper.OrderMapper;
import top.xyzhang.eduorder.service.OrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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

}
