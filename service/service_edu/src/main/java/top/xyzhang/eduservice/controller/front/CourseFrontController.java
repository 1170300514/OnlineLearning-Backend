package top.xyzhang.eduservice.controller.front;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.xyzhang.commonutils.R;
import top.xyzhang.eduservice.entity.EduCourse;
import top.xyzhang.eduservice.entity.chapter.ChapterVO;
import top.xyzhang.eduservice.entity.frontvo.CourseFrontVo;
import top.xyzhang.eduservice.entity.frontvo.CourseWebVo;
import top.xyzhang.eduservice.service.EduChapterService;
import top.xyzhang.eduservice.service.EduCourseService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("eduservice/coursefront")
@CrossOrigin
public class CourseFrontController {

    @Autowired
    private EduCourseService courseService;

    @Autowired
    private EduChapterService chapterService;

    /**
     * 条件查询带分页查询
     * @param page
     * @param limit
     * @param courseFrontVo
     * @return
     */
    @PostMapping("getFrontCourseList/{page}/{limit}")
    public R getFrontCourseList(@PathVariable long page, @PathVariable long limit,
                                @RequestBody(required = false)CourseFrontVo courseFrontVo) {
        Page<EduCourse> coursePage = new Page<>(page, limit);
        Map<String, Object> map = courseService.getCourseFrontList(coursePage, courseFrontVo);

        return R.ok().data(map);
    }

    /**
     * 获取课程详情数据
     * @return
     */
    @GetMapping("getFrontCourseInfo/{courseId}")
    public R getFrontCourseInfo(@PathVariable String courseId) {
        //查询课程信息
        CourseWebVo courseWebVo = courseService.getBaseCourseInfo(courseId);

        List<ChapterVO> video = chapterService.getChapterVideoByCourseId(courseId);

        return R.ok().data("courseWebVo", courseWebVo).data("chapterVideoList", video);
    }

    /**
     * 【订单模块】- 根据课程id查询课程信息
     * 跨模块调用需要用R对象+字符串传递
     */
    @PostMapping("getCourseInfoOrder/{id}")
    public R getCourseInfoOrder(@PathVariable String id) {
        CourseWebVo courseInfo = courseService.getBaseCourseInfo(id);
        return R.ok()
                .data("id", courseInfo.getId())
                .data("title", courseInfo.getTitle())
                .data("price", courseInfo.getPrice())
                .data("lessonNum", courseInfo.getLessonNum())
                .data("cover", courseInfo.getCover())
                .data("buyCount", courseInfo.getBuyCount())
                .data("viewCount", courseInfo.getViewCount())
                .data("description", courseInfo.getDescription())
                .data("teacherId", courseInfo.getTeacherId())
                .data("teacherName", courseInfo.getTeacherName())
                .data("intro", courseInfo.getIntro())
                .data("avatar", courseInfo.getAvatar())
                .data("subjectLevelOne", courseInfo.getSubjectLevelOne())
                .data("subjectLevelTwo", courseInfo.getSubjectLevelTwo());
    }
}
