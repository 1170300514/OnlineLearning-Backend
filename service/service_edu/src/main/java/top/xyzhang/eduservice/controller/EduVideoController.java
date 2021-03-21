package top.xyzhang.eduservice.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import top.xyzhang.commonutils.R;
import top.xyzhang.eduservice.client.VodClient;
import top.xyzhang.eduservice.entity.EduVideo;
import top.xyzhang.eduservice.service.EduVideoService;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-02-26
 */
@RestController
@RequestMapping("/eduservice/edu-video")
@CrossOrigin
public class EduVideoController {
    @Autowired
    private EduVideoService videoService;

    @Autowired
    private VodClient vodClient;

    /**
     * 添加小节
     */
    @PostMapping("addVideo")
    public R addVideo(@RequestBody EduVideo video) {
        videoService.save(video);
        return R.ok();
    }

    /**
     * 删除小节
     * TODO: 删除阿里云中的视频
     */
    @DeleteMapping("{id}")
    public R deleteVideo(@PathVariable String id) {
        // 根据小节id查询视频id进行删除
        EduVideo video = videoService.getById(id);
        String videoSourceId = video.getVideoSourceId();
        if (StringUtils.hasLength(videoSourceId)) {
            vodClient.removeAliyunVideo(videoSourceId);
        }

        videoService.removeById(id);
        return R.ok();
    }
    /**
     * 修改小节
     */
    @PostMapping("updateVideo")
    public R updateVideo(@RequestBody EduVideo video) {
        videoService.updateById(video);
        return R.ok();
    }
}

