package top.xyzhang.eduservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import top.xyzhang.commonutils.R;
import top.xyzhang.eduservice.entity.EduTeacher;
import top.xyzhang.eduservice.entity.vo.TeacherQuery;
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
@CrossOrigin
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
        Page<EduTeacher> teacherPage = new Page<>(current,limit);
        // 调用方法时 把分页后的所有数据封装到Page中
        teacherService.page(teacherPage,null);

        long total = teacherPage.getTotal();// 总记录数
        List<EduTeacher> records = teacherPage.getRecords(); // 数据list集合

        return R.ok().data("total", total).data("rows", records);
    }

    // 4. 多条件组合查询带分页
    @PostMapping("pageTeacherCondition/{current}/{limit}")
    public R pageTeacherCondition(@PathVariable long current, @PathVariable long limit,
                                  @RequestBody(required = false) TeacherQuery teacherQuery) {
        // 创建page对象
        Page<EduTeacher> page = new Page<>(current, limit);
        // 构建queryWrapper
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();
        // 多条件组合查询（每个条件都可有可无）
        String name = teacherQuery.getName();
        Integer level = teacherQuery.getLevel();
        String begin = teacherQuery.getBegin();
        String end = teacherQuery.getEnd();
        // 判断条件是否存在
        if (StringUtils.hasLength(name)) {
            wrapper.like("name", name);
        }
        if (!ObjectUtils.isEmpty(level)) {
            wrapper.eq("level", level);
        }
        if (StringUtils.hasLength(begin)) {
            wrapper.ge("gmt_create", begin);
        }
        if (StringUtils.hasLength(end)) {
            wrapper.le("gmt_create", end);
        }

        // 排序
        wrapper.orderByDesc("gmt_create");
        // 调用方法实现条件查询
        teacherService.page(page, wrapper);

        // 数据获取及其封装返回
        long total = page.getTotal();
        List<EduTeacher> records = page.getRecords();
        return R.ok().data("total", total).data("rows", records);
    }

    // 5. 添加讲师的接口方法
    @PostMapping("addTeacher")
    public R addTeacher(@RequestBody EduTeacher eduTeacher) {
        boolean save = teacherService.save(eduTeacher);
        if (save) {
            return R.ok();
        } else {
            return R.error();
        }
    }

    // 6. 根据讲师id进行查询
    @GetMapping("getTeacher/{id}")
    public R getTeacher(@PathVariable String id) {
        EduTeacher teacher = teacherService.getById(id);
        return R.ok().data("item", teacher);
    }
    // 7. 讲师修改功能
    @PostMapping("updateTeacher")
    public R updateTeacher(@RequestBody EduTeacher eduTeacher) {
        boolean flag = teacherService.updateById(eduTeacher);
        if (flag) {
            return R.ok();
        } else {
            return R.error();
        }
    }
}

