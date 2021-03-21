package top.xyzhang.eduservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import top.xyzhang.commonutils.R;

/**
 * 服务远程调用 根据服务名调用其他服务中的方法
 */
@FeignClient("service-vod")
@Component
public interface VodClient {
    /**
     * 定义调用方法的路径
     * 根据id删除阿里云视频
     * @PathVariable 一定要加路径名称
     */
    @DeleteMapping("/eduvod/video/removeVideo/{id}")
    public R removeAliyunVideo(@PathVariable("id") String id);

}
