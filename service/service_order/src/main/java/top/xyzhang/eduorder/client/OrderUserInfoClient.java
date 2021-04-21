package top.xyzhang.eduorder.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import top.xyzhang.commonutils.R;

/**
 * 远程调用获取用户信息
 */
@FeignClient(name = "service-ucenter")
@Component
public interface OrderUserInfoClient {

    /**
     * 根据用户id获取用户信息
     * 订单模块需要远程调用
     */
    @PostMapping("/educenter/member/getUserInfo/{userid}")
    public R getUserInfo(@PathVariable("userid") String userid);
}
