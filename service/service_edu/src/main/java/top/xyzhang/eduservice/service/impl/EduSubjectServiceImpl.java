package top.xyzhang.eduservice.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import top.xyzhang.eduservice.entity.EduSubject;
import top.xyzhang.eduservice.entity.ExcelSubjectData;
import top.xyzhang.eduservice.entity.subject.FirstSubject;
import top.xyzhang.eduservice.entity.subject.SecondSubject;
import top.xyzhang.eduservice.listener.SubjectExcelListener;
import top.xyzhang.eduservice.mapper.EduSubjectMapper;
import top.xyzhang.eduservice.service.EduSubjectService;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-02-25
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {

    /**
     * 添加课程分类
     * @param file
     * @param subjectService
     */
    @Override
    public void saveSubject(MultipartFile file, EduSubjectService subjectService) {
        try {
            InputStream inputStream = file.getInputStream();
            EasyExcel.read(inputStream, ExcelSubjectData.class,
                    new SubjectExcelListener(subjectService)).sheet().doRead();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 查询课程列表
     * @return
     */
    @Override
    public List<FirstSubject> getAllSubjects() {
        // 查询所有一级分类
        QueryWrapper wrapper1 = new QueryWrapper();
        wrapper1.eq("parent_id", 0);
        List<EduSubject> list1 = baseMapper.selectList(wrapper1);
        // 查询全部二级分类
        QueryWrapper wrapper2 = new QueryWrapper();
        wrapper2.ne("parent_id", 0);
        List<EduSubject> list2 = baseMapper.selectList(wrapper2);

        List<FirstSubject> ansList = new ArrayList<>();
        // 封装一级分类
        for (int i = 0; i < list1.size(); i++) {
            EduSubject eduSubject = list1.get(i);

            FirstSubject firstSubject = new FirstSubject();
            firstSubject.setId(eduSubject.getId());
            firstSubject.setTitle(eduSubject.getTitle());

            // 封装二级分类
            List<SecondSubject> secondSubjectList = new ArrayList<>();
            for (int j = 0; j < list2.size(); j++) {
                // 获取遍历到的二级分类id
                EduSubject tempEduSubject = list2.get(j);
                if (firstSubject.getId().equals(tempEduSubject.getParentId())) {
                    // 将id相同的二级分类放入一级分类子类中
                    SecondSubject secondSubject = new SecondSubject();
                    secondSubject.setId(tempEduSubject.getId());
                    secondSubject.setTitle(tempEduSubject.getTitle());
                    secondSubjectList.add(secondSubject);
                }
            }
            firstSubject.setChildren(secondSubjectList);
            ansList.add(firstSubject);
        }

        return ansList;
    }
}
