package top.xyzhang.eduservice.entity.chapter;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@ApiModel(value = "章节信息")
@Data
public class ChapterVO implements Serializable {
    private String id;

    private String title;

    private List<VideoVO> children = new ArrayList<>();
}
