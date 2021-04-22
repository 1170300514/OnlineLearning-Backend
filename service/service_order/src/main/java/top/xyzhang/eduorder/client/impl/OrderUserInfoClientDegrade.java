package top.xyzhang.eduorder.client.impl;

import org.springframework.stereotype.Component;
import top.xyzhang.commonutils.R;
import top.xyzhang.eduorder.client.OrderUserInfoClient;

@Component
public class OrderUserInfoClientDegrade implements OrderUserInfoClient {
    /**
     * 根据用户id获取用户信息
     * 订单模块需要远程调用
     *
     * @param userid
     */
    @Override
    public R getUserInfo(String userid) {
        return R.error().message("远程调用用户模块出错降级");
    }
}
