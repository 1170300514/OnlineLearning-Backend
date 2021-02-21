package top.xyzhang.eduservice.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.xyzhang.commonutils.R;
import top.xyzhang.eduservice.entity.EduTeacher;
import top.xyzhang.eduservice.service.EduTeacherService;

import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-02-20
 */
@Api(value="讲师管理")
@RestController
@RequestMapping("/eduservice/edu-teacher")
public class EduTeacherController {
    // 注入Service
    @Autowired
    private EduTeacherService teacherService;

    // 1. 查询讲师表中的全部数据
    @ApiOperation(value = "所有讲师列表")
    @GetMapping("findAll")
    public R findAllTeachers() {
        // 调用Service中的方法实现查询
        List<EduTeacher> list = teacherService.list();
        return R.ok().data("items", list);
    }

    // 2. 根据id删除讲师 @PathVariable得到路径中的值
    @ApiOperation(value = "根据ID删除讲师")
    @DeleteMapping("{id}")
    public R removeTeacher(
            @ApiParam(name = "id", value = "讲师ID", required = true)
            @PathVariable String id) {
        boolean flag = teacherService.removeById(id);
        if (flag) {
            return R.ok();
        } else {
            return R.error();
        }
    }

    // 3. 分页查询讲师---基本分页功能
    @GetMapping("pageTeacher/{current}/{limit}")
    public R pageListTeacher(@PathVariable long current,
                             @PathVariable long limit) {

        // 创建Page对象
        Page<EduTeacher> teacherPage = new Page<>(1,3);
        // 调用方法时 把分页后的所有数据封装到Page中
        teacherService.page(teacherPage,null);

        long total = teacherPage.getTotal();// 总记录数
        List<EduTeacher> records = teacherPage.getRecords(); // 数据list集合

        return R.ok().data("total", total).data("rows", records);
    }
}

