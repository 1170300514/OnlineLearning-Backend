package top.xyzhang.oss.service;

import org.springframework.web.multipart.MultipartFile;

public interface OssService {
    /**
     * 文件上传到阿里云
     * @param file
     * @return
     */
    String uploadFileAvatar(MultipartFile file);
}
