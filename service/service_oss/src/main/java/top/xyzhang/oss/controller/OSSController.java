package top.xyzhang.oss.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import top.xyzhang.commonutils.R;
import top.xyzhang.oss.service.OssService;

@RestController
@RequestMapping("/eduoss/fileoss")
@CrossOrigin
public class OSSController {

    @Autowired
    private OssService ossService;

    // 上传头像的方法
    @PostMapping
    public R uploadOssFile(MultipartFile file) {
        String url = ossService.uploadFileAvatar(file);
        return R.ok().data("url", url);
    }
}
