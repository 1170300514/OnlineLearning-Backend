package top.xyzhang.eduservice.client.impl;

import org.springframework.stereotype.Component;
import top.xyzhang.commonutils.R;
import top.xyzhang.eduservice.client.VodClient;

import java.util.List;

/**
 * 服务降级
 */
@Component
public class VodClientDegrade implements VodClient {

    /**
     * 定义调用方法的路径
     * 根据id删除阿里云视频
     *
     * @param id
     * @PathVariable 一定要加路径名称
     */
    @Override
    public R removeAliyunVideo(String id) {
        return R.error().message("删除视频出错");
    }

    /**
     * 删除多个视频（删除课程时）
     *
     * @param videoIdList
     */
    @Override
    public R removeVideosByCourse(List<String> videoIdList) {
        return R.error().message("删除多个视频出错");
    }
}
