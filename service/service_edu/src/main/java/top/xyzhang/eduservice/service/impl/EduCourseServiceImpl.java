package top.xyzhang.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import top.xyzhang.eduservice.entity.EduCourse;
import top.xyzhang.eduservice.entity.EduCourseDescription;
import top.xyzhang.eduservice.entity.frontvo.CourseFrontVo;
import top.xyzhang.eduservice.entity.frontvo.CourseWebVo;
import top.xyzhang.eduservice.entity.vo.CourseInfoVO;
import top.xyzhang.eduservice.entity.vo.CoursePublishVO;
import top.xyzhang.eduservice.mapper.EduCourseMapper;
import top.xyzhang.eduservice.service.EduChapterService;
import top.xyzhang.eduservice.service.EduCourseDescriptionService;
import top.xyzhang.eduservice.service.EduCourseService;
import top.xyzhang.eduservice.service.EduVideoService;
import top.xyzhang.servicebase.exceptionhandler.MyTestException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    /**
     * 条件查询带分页 查询课程
     * @param coursePage
     * @param courseFrontVo
     * @return
     */
    @Override
    public Map<String, Object> getCourseFrontList(Page<EduCourse> coursePage, CourseFrontVo courseFrontVo) {
        QueryWrapper<EduCourse> courseQueryWrapper = new QueryWrapper<>();
        // 判断条件是否存在
        // 一级分类
        if (StringUtils.hasLength(courseFrontVo.getSubjectParentId())) {
            courseQueryWrapper.eq("subject_parent_id", courseFrontVo.getSubjectParentId());
        }
        // 二级分类
        if (StringUtils.hasLength(courseFrontVo.getSubjectId())) {
            courseQueryWrapper.eq("subject_id", courseFrontVo.getSubjectId());
        }
        // 关注度
        if (StringUtils.hasLength(courseFrontVo.getBuyCountSort())) {
            courseQueryWrapper.orderByDesc("buy_count");
        }
        // 课程创建时间
        if (StringUtils.hasLength(courseFrontVo.getGmtCreateSort())) {
            courseQueryWrapper.orderByDesc("gmt_create");
        }
        // 价格
        if (!StringUtils.isEmpty(courseFrontVo.getPriceSort())) {
            courseQueryWrapper.orderByDesc("price");
        }
        baseMapper.selectPage(coursePage, courseQueryWrapper);

        List<EduCourse> records = coursePage.getRecords();
        long current = coursePage.getCurrent();
        long pages = coursePage.getPages();
        long size = coursePage.getSize();
        long teacherPageTotal = coursePage.getTotal();
        boolean hasNext = coursePage.hasNext();
        boolean hasPrevious = coursePage.hasPrevious();

        Map<String, Object> map = new HashMap<>();
        map.put("items", records);
        map.put("current", current);
        map.put("pages", pages);
        map.put("size", size);
        map.put("total", teacherPageTotal);
        map.put("hasNext", hasNext);
        map.put("hasPrevious", hasPrevious);
        return map;
    }

    /**
     * 根据id查询课程基本信息
     * @param courseId
     * @return
     */
    @Override
    public CourseWebVo getBaseCourseInfo(String courseId) {
        return baseMapper.getBaseCourseInfo(courseId);
    }
}
