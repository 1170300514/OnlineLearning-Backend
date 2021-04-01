package top.xyzhang.eduservice.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.xyzhang.commonutils.R;
import top.xyzhang.eduservice.entity.EduCourse;
import top.xyzhang.eduservice.entity.EduTeacher;
import top.xyzhang.eduservice.service.EduCourseService;
import top.xyzhang.eduservice.service.EduTeacherService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/eduservice/teacherfront")
@CrossOrigin
public class TeacherFrontController {

    @Autowired
    private EduTeacherService teacherService;

    @Autowired
    private EduCourseService courseService;

    /**
     * 分页查询讲师
     */
    @PostMapping("getTeacherFrontList/{page}/{limit}")
    public R getTeacherFrontList(@PathVariable long page, @PathVariable long limit) {
        Page<EduTeacher> teacherPage = new Page<>(page, limit);
        Map<String, Object> map = teacherService.getTeacherFrontList(teacherPage);

        // 返回分页的所有数据
        return R.ok().data(map);
    }

    /**
     * 讲师详情
     */
    @GetMapping("getTeacherFrontInfo/{teacherId}")
    public R getTeacherFrontInfo(@PathVariable String teacherId) {
        //根据讲师id查询讲师基本信息
        EduTeacher teacher = teacherService.getById(teacherId);
        // 查询讲师课程信息
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        wrapper.eq("teacher_id",teacherId);
        List<EduCourse> list = courseService.list(wrapper);
        return R.ok().data("teacher", teacher).data("courseList", list);
    }
}
