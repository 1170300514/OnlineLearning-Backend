package top.xyzhang.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import top.xyzhang.eduservice.entity.EduVideo;
import top.xyzhang.eduservice.mapper.EduVideoMapper;
import top.xyzhang.eduservice.service.EduVideoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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

    /**
     * 根据课程id删除小节
     * @param id
     */
    @Override
    public void removeVideosByCourseId(String id) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("course_id", id);
        baseMapper.delete(wrapper);
    }
}
