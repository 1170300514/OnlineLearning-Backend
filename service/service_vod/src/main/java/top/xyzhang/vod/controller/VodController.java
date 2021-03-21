package top.xyzhang.vod.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import top.xyzhang.commonutils.R;
import top.xyzhang.vod.service.VodService;

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
    public R removeVideosByCourse(@RequestParam("videoList")List videoIdList) {
        vodService.removeVideosByCourse(videoIdList);
        return R.ok();
    }
}
