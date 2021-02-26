package top.xyzhang.oss.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import top.xyzhang.oss.service.OssService;
import top.xyzhang.oss.utils.ConstantPropertiesUtils;

import java.io.InputStream;
import java.util.UUID;

@Service
public class OssServiceImpl implements OssService {
    @Override
    public String uploadFileAvatar(MultipartFile file) {
        // Endpoint以杭州为例，其它Region请按实际情况填写。
        String endpoint = ConstantPropertiesUtils.END_POINT;
        String accessKeyId = ConstantPropertiesUtils.ACCESS_KEY_ID;
        String accessKeySecret = ConstantPropertiesUtils.ACCESS_KEY_SECRET;
        String bucketName = ConstantPropertiesUtils.BUCKET_NAME;

        String uploadUrl = null;
        try {
            // 创建OSSClient实例。
            OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

            // 上传文件流。
            InputStream inputStream = file.getInputStream();
            // 获取文件名称
            String filename = file.getOriginalFilename();
            // 1. 在文件中添加随机值
            String randomid = UUID.randomUUID().toString().replaceAll("-","");
            // 2. 将文件按日期进行分类
            String dataPath = new DateTime().toString("YYYY/MM/DD");
            filename = dataPath + "/" + randomid + filename;

            // 调用oss方法实现上传
            // 1. Bucket名称 2. 上传到oss的文件路径和名称 3. 上传文件输入流
            ossClient.putObject(bucketName, filename, inputStream);

            // 关闭OSSClient。
            ossClient.shutdown();

            // 将上传后的文件路径返回 （手动拼接）
            uploadUrl = "https://"+bucketName+"."+endpoint+"/"+filename;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return uploadUrl;
    }
}
