package top.xyzhang.vod.controller;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import top.xyzhang.commonutils.R;
import top.xyzhang.servicebase.exceptionhandler.MyTestException;
import top.xyzhang.vod.service.VodService;
import top.xyzhang.vod.utils.ConstantPropertiesUtils;
import top.xyzhang.vod.utils.InitVodClient;

import java.util.List;

@RestController
@RequestMapping("/eduvod/video")
@CrossOrigin
public class VodController {

    @Autowired
    private VodService vodService;

    /**
     * 上传视频到阿里云
     */
    @PostMapping("uploadVideo")
    public R uploadVideo(MultipartFile file) {
        // 返回上传视频的id
        String id = vodService.uploadVideo(file);
        return R.ok().data("videoId", id);
    }

    /**
     * 根据视频id删除阿里云视频
     */
    @DeleteMapping("removeVideo/{id}")
    public R deleteVideo(@PathVariable String id) {
        vodService.deleteVideo(id);
        return R.ok();
    }

    /**
     * 删除多个视频（删除课程时）
     */
    @DeleteMapping("removeVideosByCourse")
    public R removeVideosByCourse(@RequestParam("videoList")List<String> videoIdList) {
        vodService.removeVideosByCourse(videoIdList);
        return R.ok();
    }

    /**
     * 根据视频id获取视频凭证
     * @param id
     * @return
     */
    @GetMapping("getPlayAuth/{id}")
    public R getPlayAuth(@PathVariable String id) {
        String auth = null;
        try {
            DefaultAcsClient defaultAcsClient =
                    InitVodClient.initVodClient(ConstantPropertiesUtils.Key_ID, ConstantPropertiesUtils.Key_Secret);

            GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
            GetVideoPlayAuthResponse response = new GetVideoPlayAuthResponse();

            // 向request中设置视频id
            request.setVideoId(id);

            // 调用初始化对象方法
            response = defaultAcsClient.getAcsResponse(request);
            auth = response.getPlayAuth();
        } catch (Exception e) {
            throw new MyTestException(20001, "获取视频凭证失败");
        }
        return R.ok().data("auth", auth);
    }
}
