package org.rhythm.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;

import java.io.Serializable;

@Data
@Tag(name = "文章查询界面DTO", description = "查询文章界面传递的数据模型")
public class TestPaperPageQueryDTO implements Serializable {
    //文章标题
    @Schema(title = "关键字", type = "String")
    private String keyword;
    //用户ID
    @Schema(name = "用户ID", type = "Long")
    private Long userId;
    //文章分类ID
    @Schema(name = "科目", type = "String")
    private String subject;
    //页码
    @Schema(name = "页码", type = "Integer")
    private Integer page;
    //每页显示记录数
    @Schema(name = "每页记录数", type = "Integer")
    private Integer pageSize;
}
