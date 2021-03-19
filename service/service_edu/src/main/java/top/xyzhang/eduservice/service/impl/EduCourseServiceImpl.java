package top.xyzhang.eduservice.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import top.xyzhang.eduservice.entity.EduCourse;
import top.xyzhang.eduservice.entity.EduCourseDescription;
import top.xyzhang.eduservice.entity.vo.CourseInfoVO;
import top.xyzhang.eduservice.entity.vo.CoursePublishVO;
import top.xyzhang.eduservice.mapper.EduCourseMapper;
import top.xyzhang.eduservice.service.EduChapterService;
import top.xyzhang.eduservice.service.EduCourseDescriptionService;
import top.xyzhang.eduservice.service.EduCourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import top.xyzhang.eduservice.service.EduVideoService;
import top.xyzhang.servicebase.exceptionhandler.MyTestException;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-02-26
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {

    @Autowired
    private EduCourseDescriptionService eduCourseDescriptionService;

    @Autowired
    private EduVideoService eduVideoService;

    @Autowired
    private EduChapterService eduChapterService;

    /**
     * 添加课程基本信息
     */
    @Override
    public String saveCourseInfo(CourseInfoVO infoVO) {
        // 保存课程基本信息
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(infoVO, eduCourse);
        eduCourse.setSubjectParentId(null);
        int insert = baseMapper.insert(eduCourse);
        if (insert == 0) {
            throw new MyTestException(20001, "课程信息添加失败");
        }

        // 保存课程描述信息
        EduCourseDescription eduCourseDescription = new EduCourseDescription();
        eduCourseDescription.setDescription(infoVO.getDescription());
        eduCourseDescription.setId(eduCourse.getId());
        boolean save = eduCourseDescriptionService.save(eduCourseDescription);
        if (!save) {
            throw new MyTestException(20001, "课程详情信息添加失败");
        }

        return eduCourse.getId();
    }

    /**
     * 根据课程id查询课程基本信息
     * @param courseId
     * @return
     */
    @Override
    public CourseInfoVO getCourseInfo(String courseId) {
        // 课程信息
        EduCourse eduCourse = baseMapper.selectById(courseId);
        CourseInfoVO courseInfoVO = new CourseInfoVO();
        BeanUtils.copyProperties(eduCourse, courseInfoVO);

        // 课程描述信息
        EduCourseDescription courseDescription = eduCourseDescriptionService.getById(courseId);
        courseInfoVO.setDescription(courseDescription.getDescription());

        return courseInfoVO;
    }

    /**
     * 修改课程信息
     * @param courseInfoVO
     */
    @Override
    public void updateCourseInfo(CourseInfoVO courseInfoVO) {
        // 修改课程表
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVO, eduCourse);
        int updateById = baseMapper.updateById(eduCourse);
        // 修改课程信息表
        EduCourseDescription courseDescription = new EduCourseDescription();
        courseDescription.setId(courseInfoVO.getId());
        courseDescription.setDescription(courseInfoVO.getDescription());
        eduCourseDescriptionService.updateById(courseDescription);
    }

    /**
     * 根据课程id查询课程信息
     * @param id
     * @return
     */
    @Override
    public CoursePublishVO getPublishCourseInfo(String id) {
        // 调用Mapper
        CoursePublishVO publishCourseInfo = baseMapper.getPublishCourseInfo(id);
        return publishCourseInfo;
    }

    /**
     * 根据课程id删除课程基本信息、描述、章节小节等信息
     * @param id
     */
    @Override
    public void removeCourseById(String id) {
        // 1. 删除小节
        eduVideoService.removeVideosByCourseId(id);
        // 2. 删除章节
        eduChapterService.removeChapterByCourseId(id);
        // 3. 删除描述信息
        eduCourseDescriptionService.removeById(id);
        int deleteById = baseMapper.deleteById(id);
        if (deleteById == 0) {
            throw new MyTestException(20001, "课程删除失败");
        }
    }
}
