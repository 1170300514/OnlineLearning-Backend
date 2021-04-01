package top.xyzhang.eduservice.entity.frontvo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CourseFrontVo {

    @ApiModelProperty(value = "课程讲师ID")
    private String teacherId;

    @ApiModelProperty(value = "课程专业ID")
    private String subjectId;

    @ApiModelProperty(value = "课程专业父级ID")
    private String subjectParentId;

    @ApiModelProperty(value = "课程标题")
    private String title;

    @ApiModelProperty(value = "销量排序")
    private String buyCountSort;

    @ApiModelProperty(value = "最新时间排序")
    private String gmtCreateSort;

    @ApiModelProperty(value = "课程销售价格")
    private BigDecimal priceSort;
}
