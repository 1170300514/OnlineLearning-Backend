package top.xyzhang.eduservice.mapper;

import top.xyzhang.eduservice.entity.EduCourse;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import top.xyzhang.eduservice.entity.vo.CoursePublishVO;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author testjava
 * @since 2021-02-26
 */
public interface EduCourseMapper extends BaseMapper<EduCourse> {
    public CoursePublishVO getPublishCourseInfo(String courseId);
}
