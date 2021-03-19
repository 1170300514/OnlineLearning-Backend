package top.xyzhang.eduservice.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.xyzhang.commonutils.R;
import top.xyzhang.eduservice.entity.EduChapter;
import top.xyzhang.eduservice.entity.chapter.ChapterVO;
import top.xyzhang.eduservice.service.EduChapterService;
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
@CrossOrigin
@RequestMapping("/eduservice/edu-chapter")
public class EduChapterController {
    @Autowired
    private EduChapterService eduChapterService;

    @Autowired
    private EduCourseService eduCourseService;

    /**
     * 课程大纲列表
     */
    @GetMapping("getChapterVideo/{courseId}")
    public R getChapterVideo(@PathVariable String courseId) {
        List<ChapterVO> chapterVideoByCourseId = eduChapterService.getChapterVideoByCourseId(courseId);
        return R.ok().data("allChapterVideos", chapterVideoByCourseId);
    }

    /**
     * 添加章节
     */
    @PostMapping("addChapter")
    public R addChapter(@RequestBody EduChapter eduChapter) {
        eduChapterService.save(eduChapter);
        return R.ok();
    }

    /**
     * 根据章节ID查询
     */
    @GetMapping("getChapterInfo/{chapterId}")
    public R getChapterInfo(@PathVariable String chapterId) {
        EduChapter chapter = eduChapterService.getById(chapterId);
        return R.ok().data("chapter", chapter);
    }

    /**
     * 修改章节信息
     */
    @PostMapping("updateChapter")
    public R updateChapterById(@RequestBody EduChapter eduChapter) {
        eduChapterService.updateById(eduChapter);
        return R.ok();
    }

    /**
     * 删除
     */
    @DeleteMapping("{chapterId}")
    public R deleteChapterById(@PathVariable String chapterId) {
        boolean deleteById = eduChapterService.deleteById(chapterId);
        return deleteById ? R.ok() : R.error();
    }
}

