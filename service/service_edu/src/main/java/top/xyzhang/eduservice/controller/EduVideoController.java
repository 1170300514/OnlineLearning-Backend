package top.xyzhang.eduservice.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.xyzhang.commonutils.R;
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
     */
    @DeleteMapping("{id}")
    public R deleteVideo(@PathVariable String id) {
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

