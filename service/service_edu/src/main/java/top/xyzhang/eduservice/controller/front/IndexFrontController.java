package top.xyzhang.eduservice.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.xyzhang.commonutils.R;
import top.xyzhang.eduservice.entity.EduCourse;
import top.xyzhang.eduservice.entity.EduTeacher;
import top.xyzhang.eduservice.service.EduCourseService;
import top.xyzhang.eduservice.service.EduTeacherService;

import java.util.List;

@RestController
@RequestMapping("/eduservice/indexfront")
@CrossOrigin
public class IndexFrontController {

    @Autowired
    private EduCourseService courseService;
    @Autowired
    private EduTeacherService teacherService;

    /**
     * 查询前8个热门课程 前4名师
     */
    @GetMapping("index")
    public R index() {
        QueryWrapper<EduCourse> courseWrapper = new QueryWrapper<>();
        courseWrapper.orderByDesc("id");
        courseWrapper.last("limit 8");
        List<EduCourse> eduCourses = courseService.list(courseWrapper);

        QueryWrapper<EduTeacher> teacherWrapper = new QueryWrapper<>();
        teacherWrapper.orderByDesc("id");
        teacherWrapper.last("limit 4");
        List<EduTeacher> eduTeachers = teacherService.list(teacherWrapper);

        return R.ok().data("eduCourses", eduCourses).data("eduTeachers",eduTeachers);
    }
}
