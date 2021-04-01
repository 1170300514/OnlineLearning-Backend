package top.xyzhang.eduservice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import top.xyzhang.eduservice.entity.EduCourse;
import top.xyzhang.eduservice.entity.frontvo.CourseFrontVo;
import top.xyzhang.eduservice.entity.frontvo.CourseWebVo;
import top.xyzhang.eduservice.entity.vo.CourseInfoVO;
import top.xyzhang.eduservice.entity.vo.CoursePublishVO;

import java.util.Map;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author testjava
 * @since 2021-02-26
 */
public interface EduCourseService extends IService<EduCourse> {

    String saveCourseInfo(CourseInfoVO infoVO);

    CourseInfoVO getCourseInfo(String courseId);

    void updateCourseInfo(CourseInfoVO courseInfoVO);

    CoursePublishVO getPublishCourseInfo(String id);

    void removeCourseById(String id);

    Map<String, Object> getCourseFrontList(Page<EduCourse> coursePage, CourseFrontVo courseFrontVo);

    CourseWebVo getBaseCourseInfo(String courseId);
}
