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
}
