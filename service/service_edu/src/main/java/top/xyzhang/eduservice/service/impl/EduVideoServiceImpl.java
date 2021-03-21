package top.xyzhang.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import top.xyzhang.eduservice.client.VodClient;
import top.xyzhang.eduservice.entity.EduVideo;
import top.xyzhang.eduservice.mapper.EduVideoMapper;
import top.xyzhang.eduservice.service.EduVideoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-02-26
 */
@Service
public class EduVideoServiceImpl extends ServiceImpl<EduVideoMapper, EduVideo> implements EduVideoService {

    @Autowired
    private VodClient vodClient;

    /**
     * 根据课程id删除小节
     * @param id
     */
    @Override
    public void removeVideosByCourseId(String id) {
        // 根据课程id查询全部视频id
        QueryWrapper<EduVideo> videoQueryWrapper = new QueryWrapper<>();
        videoQueryWrapper.eq("course_id", id);
        List<EduVideo> eduVideos = baseMapper.selectList(videoQueryWrapper);

        List<String> videoIds = new ArrayList<>();
        for (EduVideo eduVideo : eduVideos) {
            String videoSourceId = eduVideo.getVideoSourceId();
            if (StringUtils.hasLength(videoSourceId)) {
                videoIds.add(videoSourceId);
            }
        }
        vodClient.removeVideosByCourse(videoIds);

        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("course_id", id);
        baseMapper.delete(wrapper);
    }
}
