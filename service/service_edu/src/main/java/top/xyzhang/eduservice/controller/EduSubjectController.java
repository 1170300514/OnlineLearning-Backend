package top.xyzhang.eduservice.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import top.xyzhang.commonutils.R;
import top.xyzhang.eduservice.entity.subject.FirstSubject;
import top.xyzhang.eduservice.service.EduSubjectService;

import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-02-25
 */
@RestController
@RequestMapping("/eduservice/subject")
@CrossOrigin
public class EduSubjectController {

    @Autowired
    private EduSubjectService subjectService;

    // 添加课程分类
    // 用户上传文件读取文件内容
    @PostMapping("addSubject")
    public R addSubject(MultipartFile file) {
        // 上传过来的excel文件
        subjectService.saveSubject(file, subjectService);
        return R.ok();
    }

    // 课程分类列表（树形列表）
    @GetMapping("/getAllSubject")
    public R getAllSubject() {
        List<FirstSubject> list = subjectService.getAllSubjects();

        return R.ok().data("items", list);
    }
}

