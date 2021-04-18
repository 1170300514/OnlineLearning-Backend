package top.xyzhang.staservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import top.xyzhang.commonutils.R;

@Component
@FeignClient("service-ucenter")
public interface UcenterClient {

    /**
     * 查询某一天的注册人数
     */
    @GetMapping("/educenter/member/countRegister/{day}")
    public R countRegister(@PathVariable("day") String day);
}
