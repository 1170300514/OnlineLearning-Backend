package top.xyzhang.eduservice.service;

import org.springframework.web.multipart.MultipartFile;
import top.xyzhang.eduservice.entity.EduSubject;
import com.baomidou.mybatisplus.extension.service.IService;
import top.xyzhang.eduservice.entity.subject.FirstSubject;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author testjava
 * @since 2021-02-25
 */
public interface EduSubjectService extends IService<EduSubject> {

    // 添加课程分类
    void saveSubject(MultipartFile file, EduSubjectService eduSubjectService);

    List<FirstSubject> getAllSubjects();
}
