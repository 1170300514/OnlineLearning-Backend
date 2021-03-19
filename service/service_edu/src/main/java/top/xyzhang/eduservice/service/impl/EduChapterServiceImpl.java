package top.xyzhang.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import top.xyzhang.eduservice.entity.EduChapter;
import top.xyzhang.eduservice.entity.EduVideo;
import top.xyzhang.eduservice.entity.chapter.ChapterVO;
import top.xyzhang.eduservice.entity.chapter.VideoVO;
import top.xyzhang.eduservice.mapper.EduChapterMapper;
import top.xyzhang.eduservice.service.EduChapterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import top.xyzhang.eduservice.service.EduVideoService;
import top.xyzhang.servicebase.exceptionhandler.MyTestException;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-02-26
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {

    @Autowired
    private EduVideoService videoService;

    @Override
    public List<ChapterVO> getChapterVideoByCourseId(String courseId) {
        // 根据课程id查询章节信息
        QueryWrapper<EduChapter> wrapperChapter = new QueryWrapper<>();
        wrapperChapter.eq("course_id",courseId);
        List<EduChapter> eduChapters = baseMapper.selectList(wrapperChapter);

        // 根据课程id查询课程中全部小节
        QueryWrapper<EduVideo> wrapperVideo = new QueryWrapper<>();
        wrapperVideo.eq("course_id", courseId);
        List<EduVideo> eduVideos = videoService.list(wrapperVideo);


        // 创建List集合 用于最终封装数据
        List<ChapterVO> ansList = new ArrayList<>();

        // 将章节数据封装
        for (int i = 0; i < eduChapters.size(); i++) {
            EduChapter eduChapter = eduChapters.get(i);
            ChapterVO chapterVO = new ChapterVO();
            BeanUtils.copyProperties(eduChapter, chapterVO);
            ansList.add(chapterVO);


            // 填充小节封装数据
            List<VideoVO> videoVOList = new ArrayList<>();
            for (int j = 0; j < eduVideos.size(); j++) {
                // 得到每个小节
                EduVideo eduVideo = eduVideos.get(j);
                if (eduChapter.getId().equals(eduVideo.getChapterId())) {
                    VideoVO videoVO = new VideoVO();
                    BeanUtils.copyProperties(eduVideo, videoVO);
                    videoVOList.add(videoVO);
                }
            }

            chapterVO.setChildren(videoVOList);
        }
        return ansList;
    }

    @Override
    public boolean deleteById(String chapterId) {
        QueryWrapper<EduVideo> videoWrapper = new QueryWrapper<>();
        videoWrapper.eq("chapter_id", chapterId);
        int count = videoService.count(videoWrapper);
        if (count > 0) {
            // 查询出小节不能删除
            throw new MyTestException(20001, "章节中存在数据，不能删除");
        } else {
            int i = baseMapper.deleteById(chapterId);
            return i > 0;
        }
    }

    /**
     * 根据课程id删除chapter
     * @param id
     */
    @Override
    public void removeChapterByCourseId(String id) {
        QueryWrapper<EduChapter> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id", id);
        baseMapper.delete(wrapper);
    }
}
