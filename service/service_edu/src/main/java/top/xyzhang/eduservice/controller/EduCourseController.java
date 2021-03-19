package top.xyzhang.eduservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import top.xyzhang.commonutils.R;
import top.xyzhang.eduservice.entity.EduCourse;
import top.xyzhang.eduservice.entity.vo.CourseInfoVO;
import top.xyzhang.eduservice.entity.vo.CoursePublishVO;
import top.xyzhang.eduservice.entity.vo.CourseQuery;
import top.xyzhang.eduservice.service.EduCourseService;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-02-26
 */
@RestController
@RequestMapping("/eduservice/educourse")
@CrossOrigin
public class EduCourseController {

    @Autowired
    private EduCourseService eduCourseService;

    /**
     * 课程列表
     */
    @GetMapping("getAllCourseList")
    public R getCourseList() {
        List<EduCourse> list = eduCourseService.list();
        return R.ok().data("list", list);
    }

    /**
     * 课程信息条件查询带分页
     */
    @PostMapping("/pageCourseCondition/{current}/{limit}")
    public R pageCourseCondition(@PathVariable long current, @PathVariable long limit,
                        @RequestBody(required = false) CourseQuery courseQuery) {
        // 创建分页对象
        Page<EduCourse> page = new Page<>(current, limit);
        // 创建wrapper
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        String title = courseQuery.getTitle();
        String status = courseQuery.getStatus();
        String createTime = courseQuery.getBegin();
        String endTime = courseQuery.getEnd();

        // 判断筛选信息
        if (StringUtils.hasLength(title)) {
            wrapper.like("title", title);
        }
        if (StringUtils.hasLength(status)) {
            wrapper.eq("status", status);
        }
        if (StringUtils.hasLength(createTime)) {
            wrapper.ge("gmt_create", createTime);
        }
        if (StringUtils.hasLength(endTime)) {
            wrapper.le("gmt_create", endTime);
        }

        // 排序
        wrapper.orderByDesc("gmt_create");
        eduCourseService.page(page, wrapper);
        // 获取分页对象总数和全部课程信息
        long total = page.getTotal();
        List<EduCourse> eduCourses = page.getRecords();
        return R.ok().data("total", total).data("list", eduCourses);
    }

    /**
     * 删除课程
     */
    @DeleteMapping("/deleteCourse/{id}")
    public R deleteCourse(@PathVariable String id) {
        eduCourseService.removeCourseById(id);
        return R.ok();
    }

    /**
     * 添加课程基本信息
     * @param infoVO
     * @return
     */
    @PostMapping("/addCourse")
    public R addCourse(@RequestBody CourseInfoVO infoVO) {
        String courseId = eduCourseService.saveCourseInfo(infoVO);
        if (StringUtils.hasLength(courseId)) {
            return R.ok().data("courseId", courseId);
        } else {
            return R.error().message("保存失败");
        }

    }

    /**
     * 根据课程id查询课程基本信息
     */
    @GetMapping("getCourseInfo/{courseId}")
    public R getCourseInfo(@PathVariable String courseId) {
        CourseInfoVO item = eduCourseService.getCourseInfo(courseId);
        return R.ok().data("courseInfo", item);
    }

    /**
     * 修改课程信息
     * @return
     */
    @PostMapping("updateCourseInfo")
    public R updateCourseInfo(@RequestBody CourseInfoVO courseInfoVO) {
        eduCourseService.updateCourseInfo(courseInfoVO);
        return R.ok();
    }

    /**
     * 根据课程id查询课程确认信息
     */
    @GetMapping("getPublishCourseId/{id}")
    public R getPublishByCourseId(@PathVariable String id) {
        CoursePublishVO coursePublishVO = eduCourseService.getPublishCourseInfo(id);
        return R.ok().data("publishInfo", coursePublishVO);
    }

    /**
     * 课程最终发布
     */
    @PostMapping("publishCourse/{id}")
    public R publishCourse(@PathVariable String id) {
        EduCourse eduCourse = new EduCourse();
        eduCourse.setId(id);
        eduCourse.setStatus("Normal");
        eduCourseService.updateById(eduCourse);
        return R.ok();
    }
}

