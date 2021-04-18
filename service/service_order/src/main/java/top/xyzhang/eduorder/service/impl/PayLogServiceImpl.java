package top.xyzhang.eduorder.service.impl;

import top.xyzhang.eduorder.entity.PayLog;
import top.xyzhang.eduorder.mapper.PayLogMapper;
import top.xyzhang.eduorder.service.PayLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 支付日志表 服务实现类
 * </p>
 *
 * @author xyzhang
 * @since 2021-04-17
 */
@Service
public class PayLogServiceImpl extends ServiceImpl<PayLogMapper, PayLog> implements PayLogService {

}
