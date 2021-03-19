package top.xyzhang.eduservice.service;

import top.xyzhang.eduservice.entity.EduVideo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author testjava
 * @since 2021-02-26
 */
public interface EduVideoService extends IService<EduVideo> {

    void removeVideosByCourseId(String id);
}
