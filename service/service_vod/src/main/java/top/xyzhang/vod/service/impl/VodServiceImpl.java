package top.xyzhang.vod.service.impl;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.aliyuncs.vod.model.v20170321.DeleteVideoResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import top.xyzhang.servicebase.exceptionhandler.MyTestException;
import top.xyzhang.vod.service.VodService;
import top.xyzhang.vod.utils.ConstantPropertiesUtils;

import java.io.IOException;
import java.io.InputStream;

import static top.xyzhang.vod.utils.InitVodClient.initVodClient;

@Service
public class VodServiceImpl implements VodService {
    /**
     * 本地文件上传
     * @param file
     * @return
     */
    @Override
    public String uploadVideo(MultipartFile file) {

        // 返回的videoId
        String videoId = null;
        try {
            String accessKeyId = ConstantPropertiesUtils.Key_ID;
            String accessKeySecret = ConstantPropertiesUtils.Key_Secret;
            String originalFilename = file.getOriginalFilename();
            // title上传后显示的名称
            String title = originalFilename.substring(0, originalFilename.lastIndexOf("."));
            // filename上传文件路径
            String fileName = originalFilename;
            // inputstream上传文件输入流
            InputStream inputStream = file.getInputStream();

            UploadStreamRequest request = new UploadStreamRequest(accessKeyId, accessKeySecret, title, fileName, inputStream);

            UploadVideoImpl uploader = new UploadVideoImpl();
            UploadStreamResponse response = uploader.uploadStream(request);
            System.out.print("RequestId=" + response.getRequestId() + "\n");  //请求视频点播服务的请求ID
            if (response.isSuccess()) {
                System.out.print("VideoId=" + response.getVideoId() + "\n");
                videoId = response.getVideoId();
            } else { //如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因
                System.out.print("VideoId=" + response.getVideoId() + "\n");
                System.out.print("ErrorCode=" + response.getCode() + "\n");
                System.out.print("ErrorMessage=" + response.getMessage() + "\n");
                videoId = response.getVideoId();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return videoId;
    }

    /**
     * 删除视频
     * @param id
     */
    @Override
    public void deleteVideo(String id) {
        String accessKeyId = ConstantPropertiesUtils.Key_ID;
        String accessKeySecret = ConstantPropertiesUtils.Key_Secret;
        DefaultAcsClient client = null;
        try {
            client = initVodClient(accessKeyId, accessKeySecret);
            DeleteVideoRequest request = new DeleteVideoRequest();
            DeleteVideoResponse response = new DeleteVideoResponse();
            request.setVideoIds(id);
            response = client.getAcsResponse(request);
        } catch (ClientException e) {
            System.out.println("ErrorMessage = " + e.getLocalizedMessage());
            throw new MyTestException(20001, "删除视频失败");
        }
    }
}
