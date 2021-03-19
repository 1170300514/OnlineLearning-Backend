package top.xyzhang.eduservice.service;

import top.xyzhang.eduservice.entity.EduChapter;
import com.baomidou.mybatisplus.extension.service.IService;
import top.xyzhang.eduservice.entity.chapter.ChapterVO;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author testjava
 * @since 2021-02-26
 */
public interface EduChapterService extends IService<EduChapter> {

    List<ChapterVO> getChapterVideoByCourseId(String courseId);

    boolean deleteById(String chapterId);

    void removeChapterByCourseId(String id);
}
