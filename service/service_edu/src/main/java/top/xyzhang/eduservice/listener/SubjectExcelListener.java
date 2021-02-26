package top.xyzhang.eduservice.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import top.xyzhang.eduservice.entity.EduSubject;
import top.xyzhang.eduservice.entity.ExcelSubjectData;
import top.xyzhang.eduservice.service.EduSubjectService;
import top.xyzhang.servicebase.exceptionhandler.MyTestException;

public class SubjectExcelListener extends AnalysisEventListener<ExcelSubjectData> {

    // 手动注入
    public EduSubjectService subjectService;

    public SubjectExcelListener() {}
    // 创建有参数构造，传递subjectService用于操作数据库
    public SubjectExcelListener(EduSubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @Override
    public void invoke(ExcelSubjectData excelSubjectData, AnalysisContext analysisContext) {
        if (excelSubjectData == null) {
            throw new MyTestException(20001, "添加失败");
        }
        // 添加一级分类
        EduSubject eduSubject1 = this.existOneSubject(subjectService, excelSubjectData.getFirstSubjectName());
        if (eduSubject1 == null) {
            eduSubject1 = new EduSubject();
            eduSubject1.setTitle(excelSubjectData.getFirstSubjectName());
            eduSubject1.setParentId("0");
            subjectService.save(eduSubject1);
        }
        // 获取一级分类id值
        String pid = eduSubject1.getId();

        // 添加二级分类
        EduSubject eduSubject2 = this.existTwoSubject(subjectService, excelSubjectData.getSecondSubjectName(), pid);
        if (eduSubject2 == null) {
            eduSubject2 = new EduSubject();
            eduSubject2.setTitle(excelSubjectData.getSecondSubjectName());
            eduSubject2.setParentId(pid);
            subjectService.save(eduSubject2);
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }

    // 判断一级分类是否重复
    private EduSubject existOneSubject(EduSubjectService subjectService, String name) {
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title", name);
        wrapper.eq("parent_id","0");
        EduSubject eduSubject = subjectService.getOne(wrapper);
        return eduSubject;
    }

    // 判断二级分类是否重复
    private EduSubject existTwoSubject(EduSubjectService subjectService, String name, String pid) {
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title", name);
        wrapper.eq("parent_id", pid);
        EduSubject eduSubject = subjectService.getOne(wrapper);
        return eduSubject;
    }
}
